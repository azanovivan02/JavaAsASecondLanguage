package io.github.javaasasecondlanguage.homework01.cases;

import io.github.javaasasecondlanguage.homework01.CompGraph;
import io.github.javaasasecondlanguage.homework01.GraphPartBuilder;
import io.github.javaasasecondlanguage.homework01.Record;
import io.github.javaasasecondlanguage.homework01.ops.mappers.Printer;
import io.github.javaasasecondlanguage.homework01.ops.mappers.TokenizerMapper;
import io.github.javaasasecondlanguage.homework01.ops.reducers.CountReducer;

import java.util.List;

import static io.github.javaasasecondlanguage.homework01.utils.TestUtils.convertToRecords;
import static io.github.javaasasecondlanguage.homework01.utils.TestUtils.pushAllRecordsThenTerminal;
import static java.util.List.of;

public class TableCase implements TestCase {

    @Override
    public void launch() {
        var inputRecords = createInputs().get(0);
        var graph = createGraph().getInputNodes().get(0);
        pushAllRecordsThenTerminal(graph, inputRecords);
    }

    @Override
    public CompGraph createGraph() {
        var mainPart = GraphPartBuilder
                .startWith(new TokenizerMapper("Text", "Word"))
                .sortThenReduceBy(of("Author", "Word"), new CountReducer("Count"))
                .then(new Printer("+++ "));

        return new CompGraph(
                List.of(mainPart.getStartNode()),
                List.of(mainPart.getEndNode())
        );
    }

    @Override
    public List<List<Record>> createInputs() {
        List<Record> inputRecords = convertToRecords(
                new String[]{"Id", "Text", "Author"},
                new Object[][]{
                        {1, "The Grey Knights have come on behalf of the Holy Inquisition.", "Grey Knights"},
                        {2, "The enemies of the Emperor shall be destroyed!", "Apothecary"},
                        {3, "The warriors of the Inquisition are yours to command", "Grey Knights"},
                        {4, "The Heretics will suffer the ultimate punishment!", "Grey Knights"},
                        {5, "The fallen shall be forever remembered as the Emperor's finest.", "Apothecary"}
                }
        );
        return of(inputRecords);
    }
}
