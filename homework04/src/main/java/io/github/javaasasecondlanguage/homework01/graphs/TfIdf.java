package io.github.javaasasecondlanguage.homework01.graphs;

import io.github.javaasasecondlanguage.homework01.CompGraph;
import io.github.javaasasecondlanguage.homework01.GraphPartBuilder;
import io.github.javaasasecondlanguage.homework01.Row;
import io.github.javaasasecondlanguage.homework01.ops.InnerJoin;
import io.github.javaasasecondlanguage.homework01.ops.mappers.AddColumnMapper;
import io.github.javaasasecondlanguage.homework01.ops.mappers.IdentityMapper;
import io.github.javaasasecondlanguage.homework01.ops.mappers.RetainColumnsMapper;
import io.github.javaasasecondlanguage.homework01.ops.mappers.TokenizerMapper;
import io.github.javaasasecondlanguage.homework01.ops.reducers.CountReducer;
import io.github.javaasasecondlanguage.homework01.ops.reducers.FirstNReducer;
import io.github.javaasasecondlanguage.homework01.ops.reducers.SumReducer;
import io.github.javaasasecondlanguage.homework01.ops.reducers.TermFrequencyReducer;

import java.util.List;

import static java.util.List.of;

public class TfIdf {

    public static CompGraph createGraph() {
        GraphPartBuilder inputGraph = GraphPartBuilder
                .startWith(new IdentityMapper());

        GraphPartBuilder docCountGraph = inputGraph
                .branch()
                .sortThenReduceBy(of(), new CountReducer("DocsCount"));

        GraphPartBuilder wordGraph = inputGraph
                .branch()
                .then(new AddColumnMapper("Text", row -> row.getString("Text").toLowerCase()))
                .then(new TokenizerMapper("Text", "Word"));

        GraphPartBuilder uniqueDocWordGraph = wordGraph
                .branch()
                .sortThenReduceBy(of("Id", "Word"), new FirstNReducer(1))
                .sortBy(of("Word"));

        GraphPartBuilder countIdfGraph = uniqueDocWordGraph
                .branch()
                .reduceBy(of("Word"), new CountReducer("DocsWithWordCount"))
                .join(uniqueDocWordGraph, of("Word"), new InnerJoin())
                .sortBy(of("Id", "Word"));

        GraphPartBuilder rawTfIdfGraph = wordGraph
                .branch()
                .sortThenReduceBy(of("Id"), new TermFrequencyReducer("Word", "Tf"))
                .join(countIdfGraph, of("Id", "Word"), new InnerJoin())
                .join(docCountGraph, of(), new InnerJoin())
                .then(new AddColumnMapper("RawTfIdf", TfIdf::calculateTfIdf));

        GraphPartBuilder tfIdsSumGraph = rawTfIdfGraph
                .branch()
                .reduceBy(of("Id"), new SumReducer("RawTfIdf", "TfIdfSum"));

        GraphPartBuilder normalizedTfIdfGraph = rawTfIdfGraph
                .join(tfIdsSumGraph, of("Id"), new InnerJoin())
                .then(new AddColumnMapper("TfIdf", row -> row.getDouble("RawTfIdf") / row.getDouble("TfIdfSum")))
                .then(new RetainColumnsMapper(of("Id", "Word", "TfIdf")));

        return new CompGraph(
                List.of(inputGraph.getStartNode()),
                List.of(normalizedTfIdfGraph.getEndNode())
        );
    }

    public static double calculateTfIdf(Row row) {
        double tf = row.getDouble("Tf");
        double docsCount = row.getDouble("DocsCount");
        double docsWithWordCount = row.getDouble("DocsWithWordCount");

        return tf * Math.log(docsCount / docsWithWordCount);
    }
}
