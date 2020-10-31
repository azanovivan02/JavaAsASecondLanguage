package io.github.javaasasecondlanguage.homework01.reducers;

import io.github.javaasasecondlanguage.homework01.Record;
import io.github.javaasasecondlanguage.homework01.ops.reducers.TermFrequencyReducer;
import io.github.javaasasecondlanguage.homework01.utils.RecordGroup;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.github.javaasasecondlanguage.homework01.utils.AssertionUtils.assertRecordsEqual;
import static io.github.javaasasecondlanguage.homework01.utils.RecordGroup.group;
import static io.github.javaasasecondlanguage.homework01.utils.TestUtils.applyReducerToAllGroups;
import static io.github.javaasasecondlanguage.homework01.utils.TestUtils.convertToRecords;

class TermFrequencyReducerTest {

    @Test
    void general() {
        var reducer = new TermFrequencyReducer("Word", "Tf");

        List<Record> actualRecords = applyReducerToAllGroups(reducer, inputGroups);
        assertRecordsEqual(expectedRecords, actualRecords);
    }

    private static final List<RecordGroup> inputGroups = List.of(
            group(
                    Map.of("DocId", 1),
                    convertToRecords(
                            new String[]{"DocId", "Word"},
                            new Object[][]{
                                    {1, "hello"},
                                    {1, "little"},
                                    {1, "world"},
                            }
                    )
            ),
            group(
                    Map.of("DocId", 2),
                    convertToRecords(
                            new String[]{"DocId", "Word"},
                            new Object[][]{
                    {2, "little"}
                            }
                    )
            ),
            group(
                    Map.of("DocId", 3),
                    convertToRecords(
                            new String[]{"DocId", "Word"},
                            new Object[][]{
                                    {3, "little"},
                                    {3, "little"},
                                    {3, "little"}
                            }
                    )
            ),
            group(
                    Map.of("DocId", 4),
                    convertToRecords(
                            new String[]{"DocId", "Word"},
                            new Object[][]{
                                    {4, "little"},
                                    {4, "hello"},
                                    {4, "little"},
                                    {4, "world"},
                            }
                    )
            ),
            group(
                    Map.of("DocId", 5),
                    convertToRecords(
                            new String[]{"DocId", "Word"},
                            new Object[][]{
                                    {5, "hello"},
                                    {5, "hello"},
                                    {5, "world"},
                            }
                    )
            ),
            group(
                    Map.of("DocId", 6),
                    convertToRecords(
                            new String[]{"DocId", "Word"},
                            new Object[][]{
                                    {6, "world"},
                                    {6, "world"},
                                    {6, "world"},
                                    {6, "world"},
                                    {6, "hello"}
                            }
                    )
            )
    );

    private static final List<Record> expectedRecords = convertToRecords(
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
}