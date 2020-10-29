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

import static io.github.javaasasecondlanguage.homework01.ops.reducers.Sorter.Order.ASCENDING;
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
                .sortBy(ASCENDING, of())
                .reduce(new CountReducer("DocsCount"), of());

        GraphBuilder wordGraph = inputGraph
                .branch()
                .then(new AddColumnMapper("Text", row -> row.getString("Text").toLowerCase()))
                .then(new TokenizerMapper("Text", "Word"));

        GraphBuilder uniqueDocWordGraph = wordGraph
                .branch()
                .sortBy(ASCENDING, of("Id", "Word"))
                .reduce(new FirstNReducer(1), of("Id", "Word"))
                .sortBy(ASCENDING, of("Word"));

        GraphBuilder countIdfGraph = uniqueDocWordGraph
                .branch()
                .reduce(new CountReducer("DocsWithWordCount"), of("Word"))
                .join(uniqueDocWordGraph, new InnerJoin(), of("Word"))
                .sortBy(ASCENDING, of("Id", "Word"));

        GraphBuilder rawTfIdfGraph = wordGraph
                .branch()
                .sortBy(ASCENDING, of("Id"))
                .reduce(new TermFrequencyReducer("Word", "Tf"),of("Id"))
                .join(countIdfGraph, new InnerJoin(), of("Id", "Word"))
                .join(docCountGraph, new InnerJoin(), of())
                .then(new AddColumnMapper("RawTfIdf", TfIdfCase::calculateTfIdf));

        GraphBuilder tfIdsSumGraph = rawTfIdfGraph
                .branch()
                .reduce(new SumReducer("RawTfIdf", "TfIdfSum"), of("Id"));

        GraphBuilder normalizedTfIdfGraph = rawTfIdfGraph
                .join(tfIdsSumGraph, new InnerJoin(), of("Id"))
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
