package io.github.javaasasecondlanguage.homework01.cases;

import io.github.javaasasecondlanguage.homework01.GraphBuilder;
import io.github.javaasasecondlanguage.homework01.Row;
import io.github.javaasasecondlanguage.homework01.nodes.CompNode;
import io.github.javaasasecondlanguage.homework01.ops.InnerJoin;
import io.github.javaasasecondlanguage.homework01.ops.mappers.AddColumnMapper;
import io.github.javaasasecondlanguage.homework01.ops.mappers.IdentityMapper;
import io.github.javaasasecondlanguage.homework01.ops.mappers.Printer;
import io.github.javaasasecondlanguage.homework01.ops.mappers.RetainColumnsMapper;
import io.github.javaasasecondlanguage.homework01.ops.mappers.TokenizerMapper;
import io.github.javaasasecondlanguage.homework01.ops.reducers.CountReducer;
import io.github.javaasasecondlanguage.homework01.ops.reducers.FirstNReducer;
import io.github.javaasasecondlanguage.homework01.ops.reducers.SumReducer;
import io.github.javaasasecondlanguage.homework01.ops.reducers.TermFrequencyReducer;

import java.util.List;

import static io.github.javaasasecondlanguage.homework01.utils.TestUtils.convertToRows;
import static io.github.javaasasecondlanguage.homework01.utils.TestUtils.pushAllRowsThenTerminal;
import static java.util.Arrays.asList;
import static java.util.List.of;

public class TfIdfCase implements TestCase {

    @Override
    public void launch() {
        List<Row> inputRows = createInputs().get(0);
        CompNode startNode = createGraph().get(0);
        pushAllRowsThenTerminal(startNode, inputRows);
    }

    @Override
    public List<CompNode> createGraph() {
        GraphBuilder inputGraph = GraphBuilder
                .startWith(new IdentityMapper());

        GraphBuilder docCountGraph = inputGraph
                .branch()
                .sortThenReduceBy(of(), new CountReducer("DocsCount"));

        GraphBuilder wordGraph = inputGraph
                .branch()
                .then(new AddColumnMapper("Text", row -> row.getString("Text").toLowerCase()))
                .then(new TokenizerMapper("Text", "Word"));

        GraphBuilder uniqueDocWordGraph = wordGraph
                .branch()
                .sortThenReduceBy(of("Id", "Word"), new FirstNReducer(1))
                .sortBy(of("Word"));

        GraphBuilder countIdfGraph = uniqueDocWordGraph
                .branch()
                .reduceBy(of("Word"), new CountReducer("DocsWithWordCount"))
                .join(uniqueDocWordGraph, of("Word"), new InnerJoin())
                .sortBy(of("Id", "Word"));

        GraphBuilder rawTfIdfGraph = wordGraph
                .branch()
                .sortThenReduceBy(of("Id"), new TermFrequencyReducer("Word", "Tf"))
                .join(countIdfGraph, of("Id", "Word"), new InnerJoin())
                .join(docCountGraph, of(), new InnerJoin())
                .then(new AddColumnMapper("RawTfIdf", TfIdfCase::calculateTfIdf));

        GraphBuilder tfIdsSumGraph = rawTfIdfGraph
                .branch()
                .reduceBy(of("Id"), new SumReducer("RawTfIdf", "TfIdfSum"));

        GraphBuilder normalizedTfIdfGraph = rawTfIdfGraph
                .join(tfIdsSumGraph, of("Id"), new InnerJoin())
                .then(new AddColumnMapper("TfIdf", row -> row.getDouble("RawTfIdf") / row.getDouble("TfIdfSum")))
                .then(new RetainColumnsMapper(of("Id", "Word", "TfIdf")))
                .then(new Printer("^^^ final: "));

        return asList(
                inputGraph.getStartNode()
        );
    }

    public static double calculateTfIdf(Row row) {
        double tf = row.getDouble("Tf");
        double docsCount = row.getDouble("DocsCount");
        double docsWithWordCount = row.getDouble("DocsWithWordCount");

        return tf * Math.log(docsCount / docsWithWordCount);
    }

    @Override
    public List<List<Row>> createInputs() {
        List<Row> inputRows = convertToRows(
                new String[]{"Id", "Text"},
                new Object[][]{
                        {1, "hello, little world"},
                        {2, "little"},
                        {3, "little little little"},
                        {4, "little? hello little world"},
                        {5, "HELLO HELLO! WORLD..."},
                        {6, "world? world... world!!! WORLD!!! HELLO!!!"}
                }
        );
        return of(inputRows);
    }
}
