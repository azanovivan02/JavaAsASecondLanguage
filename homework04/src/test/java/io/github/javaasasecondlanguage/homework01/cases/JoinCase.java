package io.github.javaasasecondlanguage.homework01.cases;

import io.github.javaasasecondlanguage.homework01.ProcGraph;
import io.github.javaasasecondlanguage.homework01.GraphPartBuilder;
import io.github.javaasasecondlanguage.homework01.Record;
import io.github.javaasasecondlanguage.homework01.nodes.ProcNode;
import io.github.javaasasecondlanguage.homework01.ops.mappers.Printer;

import java.util.List;

import static io.github.javaasasecondlanguage.homework01.utils.TestUtils.convertToRecords;
import static io.github.javaasasecondlanguage.homework01.utils.TestUtils.pushAllRecordsThenTerminal;
import static java.util.List.of;

public class JoinCase implements TestCase {

    @Override
    public void launch() {
        List<ProcNode> startNodes = createGraph().getInputNodes();

        List<List<Record>> inputs = createInputs();
        List<Record> leftRecords = inputs.get(0);
        List<Record> rightRecords = inputs.get(1);

        ProcNode leftStartNode = startNodes.get(0);
        ProcNode rightStartNode = startNodes.get(1);

        pushAllRecordsThenTerminal(leftStartNode, leftRecords);
        pushAllRecordsThenTerminal(rightStartNode, rightRecords);
    }

    @Override
    public ProcGraph createGraph() {
        var rightPart = GraphPartBuilder
                .startWith(new Printer("--- right: "));

        var leftPart = GraphPartBuilder
                .startWith(new Printer("+++ left: "))
                .join(rightPart, of("AuthorId"))
                .map(new Printer("*** output: "));

        return new ProcGraph(
                List.of(leftPart.getStartNode(), rightPart.getStartNode()),
                List.of(leftPart.getEndNode())
        );
    }

    @Override
    public List<List<Record>> createInputs() {
        List<Record> leftRecords = convertToRecords(
                new String[]{"DocId", "Text", "AuthorId"},
                new Object[][]{
                        {1, "The Grey Knights have come on behalf of the Holy Inquisition.", 100},
                        {4, "The Heretics will suffer the ultimate punishment!", 100},
                        {2, "The enemies of the Emperor shall be destroyed!", 200},
                        {3, "The warriors of the Inquisition are yours to command", 100},
                        {5, "The fallen shall be forever remembered as the Emperor's finest.", 200}
                }
        );
        List<Record> rightRecords = convertToRecords(
                new String[]{"AuthorId", "AuthorName"},
                new Object[][]{
                        {100, "Grey Knights"},
                        {100, "Tactical"},
                        {200, "Apothecary"}
                }
        );

        return of(leftRecords, rightRecords);
    }

}
