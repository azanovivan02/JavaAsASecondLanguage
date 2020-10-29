package io.github.javaasasecondlanguage.homework01.cases;

import io.github.javaasasecondlanguage.homework01.GraphBuilder;
import io.github.javaasasecondlanguage.homework01.Row;
import io.github.javaasasecondlanguage.homework01.nodes.CompNode;
import io.github.javaasasecondlanguage.homework01.ops.mappers.Printer;
import io.github.javaasasecondlanguage.homework01.ops.reducers.TermFrequencyReducer;

import java.util.Arrays;
import java.util.List;

import static io.github.javaasasecondlanguage.homework01.Utils.convertToRows;
import static io.github.javaasasecondlanguage.homework01.Utils.pushAllThenTerminal;
import static io.github.javaasasecondlanguage.homework01.ops.reducers.Sorter.Order.ASCENDING;

public class TermFrequencyCase implements TestCase {

    @Override
    public void launch() {
        CompNode startNode = createGraph().get(0);

        pushAllThenTerminal(startNode, inputRows);
    }

    @Override
    public List<CompNode> createGraph() {
        GraphBuilder inputGraph = GraphBuilder
                .startWith(new Printer("+++ input: "))
                .sortBy(ASCENDING, "DocId")
                .then(new TermFrequencyReducer("Word", "Tf", "DocId"))
                .then(new Printer("--- output: "));

        return Arrays.asList(
                inputGraph.getStartNode()
        );
    }

    private static final List<Row> inputRows = convertToRows(
            new String[]{"DocId", "Word"},
            new Object[][]{
                    {1, "hello"},
                    {1, "little"},
                    {1, "world"},

                    {2, "little"},

                    {3, "little"},
                    {3, "little"},
                    {3, "little"},

                    {4, "little"},
                    {4, "hello"},
                    {4, "little"},
                    {4, "world"},

                    {5, "hello"},
                    {5, "hello"},
                    {5, "world"},

                    {6, "world"},
                    {6, "world"},
                    {6, "world"},
                    {6, "world"},
                    {6, "hello"}
            }
    );


//    etalon = [
//    {'doc_id': 1, 'text': 'hello', 'tf': pytest.approx(0.3333, abs=0.001)},
//    {'doc_id': 1, 'text': 'little', 'tf': pytest.approx(0.3333, abs=0.001)},
//    {'doc_id': 1, 'text': 'world', 'tf': pytest.approx(0.3333, abs=0.001)},
//
//    {'doc_id': 2, 'text': 'little', 'tf': pytest.approx(1.0)},
//
//    {'doc_id': 3, 'text': 'little', 'tf': pytest.approx(1.0)},
//
//    {'doc_id': 4, 'text': 'hello', 'tf': pytest.approx(0.25)},
//    {'doc_id': 4, 'text': 'little', 'tf': pytest.approx(0.5)},
//    {'doc_id': 4, 'text': 'world', 'tf': pytest.approx(0.25)},
//
//    {'doc_id': 5, 'text': 'hello', 'tf': pytest.approx(0.666, abs=0.001)},
//    {'doc_id': 5, 'text': 'world', 'tf': pytest.approx(0.333, abs=0.001)},
//
//    {'doc_id': 6, 'text': 'hello', 'tf': pytest.approx(0.2)},
//    {'doc_id': 6, 'text': 'world', 'tf': pytest.approx(0.8)}
}
