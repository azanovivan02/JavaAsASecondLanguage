package io.github.javaasasecondlanguage.homework01.cases;

import io.github.javaasasecondlanguage.homework01.GraphBuilder;
import io.github.javaasasecondlanguage.homework01.Row;
import io.github.javaasasecondlanguage.homework01.nodes.CompNode;
import io.github.javaasasecondlanguage.homework01.ops.mappers.Printer;
import io.github.javaasasecondlanguage.homework01.ops.mappers.TokenizerMapper;
import io.github.javaasasecondlanguage.homework01.ops.reducers.CountReducer;

import java.util.List;

import static io.github.javaasasecondlanguage.homework01.ops.reducers.Sorter.Order.ASCENDING;
import static io.github.javaasasecondlanguage.homework01.utils.TestUtils.convertToRows;
import static io.github.javaasasecondlanguage.homework01.utils.TestUtils.pushAllRowsThenTerminal;
import static java.util.Collections.singletonList;
import static java.util.List.of;

public class TableCase implements TestCase {

    @Override
    public void launch() {
        List<Row> inputRows = createInputs().get(0);
        CompNode graph = createGraph().get(0);
        pushAllRowsThenTerminal(graph, inputRows);
    }

    @Override
    public List<CompNode> createGraph() {
        CompNode startNode = GraphBuilder
                .startWith(new TokenizerMapper("Text", "Word"))
                .sortBy(ASCENDING, of("Author", "Word"))
                .reduce(new CountReducer("Count"), of("Author", "Word"))
                .then(new Printer("+++ "))
                .getStartNode();

        return singletonList(startNode);
    }

    @Override
    public List<List<Row>> createInputs() {
        List<Row> inputRows = convertToRows(
                new String[]{"Id", "Text", "Author"},
                new Object[][]{
                        {1, "The Grey Knights have come on behalf of the Holy Inquisition.", "Grey Knights"},
                        {2, "The enemies of the Emperor shall be destroyed!", "Apothecary"},
                        {3, "The warriors of the Inquisition are yours to command", "Grey Knights"},
                        {4, "The Heretics will suffer the ultimate punishment!", "Grey Knights"},
                        {5, "The fallen shall be forever remembered as the Emperor's finest.", "Apothecary"}
                }
        );
        return of(inputRows);
    }
}
