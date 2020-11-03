package io.github.javaasasecondlanguage.homework04;

import io.github.javaasasecondlanguage.homework04.graphs.TfIdf;
import io.github.javaasasecondlanguage.homework04.utils.ListDumper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.github.javaasasecondlanguage.homework04.Utils.recordsToString;
import static io.github.javaasasecondlanguage.homework04.utils.AssertionUtils.assertRecordsEqual;
import static io.github.javaasasecondlanguage.homework04.utils.TestUtils.convertToRecords;
import static io.github.javaasasecondlanguage.homework04.utils.TestUtils.pushAllRecordsThenTerminal;

public class TfIdfTest {

    @Test
    void general() {
        var graph = TfIdf.createGraph();
        var inputNode = graph.getInputNodes().get(0);
        var outputNode = graph.getOutputNodes().get(0);

        ListDumper listDumper = new ListDumper();
        GraphPartBuilder
                .startFrom(outputNode)
                .map(listDumper);

        pushAllRecordsThenTerminal(inputNode, inputRecords);

        List<Record> actualRecords = listDumper.getRecords();
        System.out.println(recordsToString(actualRecords));

        assertRecordsEqual(expectedRecords, actualRecords);
    }

    private static final List<Record> inputRecords = convertToRecords(
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

    // TODO replace test cases
    private static final List<Record> expectedRecords = convertToRecords(
            new String[]{"Id", "Word", "TfIdf"},
            new Object[][]{
                    {1, "hello", 0.333},
                    {1, "little", 0.333},
                    {1, "world", 0.333},

                    {2, "little", 1.000},

                    {3, "little", 1.000},

                    {4, "hello", 0.250},
                    {4, "little", 0.500},
                    {4, "world", 0.250},

                    {5, "hello", 0.667},
                    {5, "world", 0.333},

                    {6, "hello", 0.200},
                    {6, "world", 0.800}
            }
    );

}
