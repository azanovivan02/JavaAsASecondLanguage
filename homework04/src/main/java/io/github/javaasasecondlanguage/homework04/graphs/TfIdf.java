package io.github.javaasasecondlanguage.homework04.graphs;

import io.github.javaasasecondlanguage.homework04.GraphPartBuilder;
import io.github.javaasasecondlanguage.homework04.ProcGraph;
import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.ops.mappers.AddColumnMapper;
import io.github.javaasasecondlanguage.homework04.ops.mappers.IdentityMapper;
import io.github.javaasasecondlanguage.homework04.ops.mappers.LowerCaseMapper;
import io.github.javaasasecondlanguage.homework04.ops.mappers.RetainColumnsMapper;
import io.github.javaasasecondlanguage.homework04.ops.mappers.TokenizerMapper;
import io.github.javaasasecondlanguage.homework04.ops.reducers.CountReducer;
import io.github.javaasasecondlanguage.homework04.ops.reducers.FirstNReducer;
import io.github.javaasasecondlanguage.homework04.ops.reducers.SumReducer;
import io.github.javaasasecondlanguage.homework04.ops.reducers.TermFrequencyReducer;

import java.util.List;

import static java.util.List.of;

public class TfIdf {

    public static ProcGraph createGraph() {
        var inputGraph = GraphPartBuilder
                .startWith(new IdentityMapper());

        var docCountGraph = inputGraph
                .branch()
                .sortThenReduceBy(of(), new CountReducer("DocsCount"));

        var wordGraph = inputGraph
                .branch()
                .map(new LowerCaseMapper("Text"))
                .map(new TokenizerMapper("Text", "Word"));

        var uniqueDocWordGraph = wordGraph
                .branch()
                .sortThenReduceBy(of("Id", "Word"), new FirstNReducer(1))
                .sortBy(of("Word"));

        var countIdfGraph = uniqueDocWordGraph
                .branch()
                .reduceBy(of("Word"), new CountReducer("DocsWithWordCount"))
                .join(uniqueDocWordGraph, of("Word"))
                .sortBy(of("Id", "Word"));

        var rawTfIdfGraph = wordGraph
                .branch()
                .sortThenReduceBy(of("Id"), new TermFrequencyReducer("Word", "Tf"))
                .join(countIdfGraph, of("Id", "Word"))
                .join(docCountGraph, of())
                .map(new AddColumnMapper("RawTfIdf", TfIdf::calculateTfIdf));

        var tfIdsSumGraph = rawTfIdfGraph
                .branch()
                .reduceBy(of("Id"), new SumReducer("RawTfIdf", "TfIdfSum"));

        var normalizedTfIdfGraph = rawTfIdfGraph
                .join(tfIdsSumGraph, of("Id"))
                .map(new AddColumnMapper("TfIdf", record -> record.getDouble("RawTfIdf") / record.getDouble("TfIdfSum")))
                .map(new RetainColumnsMapper(of("Id", "Word", "TfIdf")));

        return new ProcGraph(
                List.of(inputGraph.getStartNode()),
                List.of(normalizedTfIdfGraph.getEndNode())
        );
    }

    public static double calculateTfIdf(Record record) {
        double tf = record.getDouble("Tf");
        double docsCount = record.getDouble("DocsCount");
        double docsWithWordCount = record.getDouble("DocsWithWordCount");

        return tf * Math.log(docsCount / docsWithWordCount);
    }
}
