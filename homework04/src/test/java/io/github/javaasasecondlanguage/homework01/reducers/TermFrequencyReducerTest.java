package io.github.javaasasecondlanguage.homework01.reducers;

import io.github.javaasasecondlanguage.homework01.Record;
import io.github.javaasasecondlanguage.homework01.ops.reducers.TermFrequencyReducer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.github.javaasasecondlanguage.homework01.utils.AssertionUtils.assertRecordsEqual;
import static io.github.javaasasecondlanguage.homework01.utils.TestUtils.applyToAllRecordsThenTerminal;
import static io.github.javaasasecondlanguage.homework01.utils.TestUtils.convertToRecords;
import static java.util.List.of;

class TermFrequencyReducerTest {

    private static final List<Record> INPUT_RECORDS = convertToRecords(
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

    private static final List<Record> EXPECTED_RECORDS = convertToRecords(
            new String[]{"DocId", "Word", "Tf"},
            new Object[][]{
                    {1, "hello", 0.3333},
                    {1, "little", 0.3333},
                    {1, "world", 0.3333},

                    {2, "little", 1.0},

                    {3, "little", 1.0},

                    {4, "hello", 0.25},
                    {4, "little", 0.5},
                    {4, "world", 0.25},

                    {5, "hello", 0.666},
                    {5, "world", 0.333},

                    {6, "hello", 0.2},
                    {6, "world", 0.8}
            }
    );

    @Test
    void general() {
        var reducer = new TermFrequencyReducer("Word", "Tf");
        reducer.setKeyColumns(of("DocId"));

        var actualRecords = applyToAllRecordsThenTerminal(reducer, INPUT_RECORDS);
        assertRecordsEqual(EXPECTED_RECORDS, actualRecords);
    }
}