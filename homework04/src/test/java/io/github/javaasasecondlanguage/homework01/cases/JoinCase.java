package io.github.javaasasecondlanguage.homework01.cases;

import io.github.javaasasecondlanguage.homework01.CompGraph;
import io.github.javaasasecondlanguage.homework01.GraphPartBuilder;
import io.github.javaasasecondlanguage.homework01.Row;
import io.github.javaasasecondlanguage.homework01.CompNode;
import io.github.javaasasecondlanguage.homework01.ops.InnerJoin;
import io.github.javaasasecondlanguage.homework01.ops.mappers.Printer;

import java.util.List;

import static io.github.javaasasecondlanguage.homework01.utils.TestUtils.convertToRows;
import static io.github.javaasasecondlanguage.homework01.utils.TestUtils.pushAllRowsThenTerminal;
import static java.util.List.of;

public class JoinCase implements TestCase {

    @Override
    public void launch() {
        List<CompNode> startNodes = createGraph().getInputNodes();

        List<List<Row>> inputs = createInputs();
        List<Row> leftRows = inputs.get(0);
        List<Row> rightRows = inputs.get(1);

        CompNode leftStartNode = startNodes.get(0);
        CompNode rightStartNode = startNodes.get(1);

        pushAllRowsThenTerminal(leftStartNode, leftRows);
        pushAllRowsThenTerminal(rightStartNode, rightRows);
    }

    @Override
    public CompGraph createGraph() {
        var rightPart = GraphPartBuilder
                .startWith(new Printer("--- right: "));

        var leftPart = GraphPartBuilder
                .startWith(new Printer("+++ left: "))
                .join(rightPart, of("AuthorId"), new InnerJoin())
                .then(new Printer("*** output: "));

        return new CompGraph(
                List.of(leftPart.getStartNode(), rightPart.getStartNode()),
                List.of(leftPart.getEndNode())
        );
    }

    @Override
    public List<List<Row>> createInputs() {
        List<Row> leftRows = convertToRows(
                new String[]{"DocId", "Text", "AuthorId"},
                new Object[][]{
                        {1, "The Grey Knights have come on behalf of the Holy Inquisition.", 100},
                        {4, "The Heretics will suffer the ultimate punishment!", 100},
                        {2, "The enemies of the Emperor shall be destroyed!", 200},
                        {3, "The warriors of the Inquisition are yours to command", 100},
                        {5, "The fallen shall be forever remembered as the Emperor's finest.", 200}
                }
        );
        List<Row> rightRows = convertToRows(
                new String[]{"AuthorId", "AuthorName"},
                new Object[][]{
                        {100, "Grey Knights"},
                        {100, "Tactical"},
                        {200, "Apothecary"}
                }
        );

        return of(leftRows, rightRows);
    }

}
