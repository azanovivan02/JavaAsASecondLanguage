package io.github.javaasasecondlanguage.homework01.cases;

import io.github.javaasasecondlanguage.homework01.GraphBuilder;
import io.github.javaasasecondlanguage.homework01.Row;
import io.github.javaasasecondlanguage.homework01.nodes.CompNode;
import io.github.javaasasecondlanguage.homework01.ops.InnerJoin;
import io.github.javaasasecondlanguage.homework01.ops.mappers.AddColumnMapper;
import io.github.javaasasecondlanguage.homework01.ops.mappers.IdentityMapper;
import io.github.javaasasecondlanguage.homework01.ops.mappers.LambdaMapper;
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
import static io.github.javaasasecondlanguage.homework01.ops.reducers.Sorter.Order.ASCENDING;
import static java.util.Arrays.asList;

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
                .sortBy(ASCENDING)
                .then(new CountReducer("DocsCount"));

        GraphBuilder wordGraph = inputGraph
                .branch()
                .then(new LambdaMapper<String>("Text", String::toLowerCase))
                .then(new TokenizerMapper("Text", "Word"));

        GraphBuilder uniqueDocWordGraph = wordGraph
                .branch()
                .sortBy(ASCENDING, "Id", "Word")
                .then(new FirstNReducer(1, "Id", "Word"))
                .sortBy(ASCENDING, "Word");

        GraphBuilder countIdfGraph = uniqueDocWordGraph
                .branch()
                .then(new CountReducer("DocsWithWordCount", "Word"))
                .join(uniqueDocWordGraph, new InnerJoin("Word"))
                .sortBy(ASCENDING, "Id", "Word");

        GraphBuilder rawTfIdfGraph = wordGraph
                .branch()
                .sortBy(ASCENDING, "Id")
                .then(new TermFrequencyReducer("Word", "Tf", "Id"))
                .join(countIdfGraph, new InnerJoin("Id", "Word"))
                .join(docCountGraph, new InnerJoin())
                .then(new AddColumnMapper("RawTfIdf", TfIdfCase::calculateTfIdf));

        GraphBuilder tfIdsSumGraph = rawTfIdfGraph
                .branch()
                .then(new SumReducer("RawTfIdf", "TfIdfSum", "Id"));

        GraphBuilder normalizedTfIdfGraph = rawTfIdfGraph
                .join(tfIdsSumGraph, new InnerJoin("Id"))
                .then(new AddColumnMapper("TfIdf", row -> row.getDouble("RawTfIdf") / row.getDouble("TfIdfSum")))
                .then(new RetainColumnsMapper("Id", "Word", "TfIdf"))
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
        return List.of(inputRows);
    }
}
