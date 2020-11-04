package io.github.javaasasecondlanguage.homework04.reducers;

import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.ops.reducers.WordFrequencyReducer;
import io.github.javaasasecondlanguage.homework04.utils.RecordGroup;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.github.javaasasecondlanguage.homework04.utils.AssertionUtils.assertRecordsEqual;
import static io.github.javaasasecondlanguage.homework04.utils.RecordGroup.group;
import static io.github.javaasasecondlanguage.homework04.utils.TestUtils.applyReducerToAllGroups;
import static io.github.javaasasecondlanguage.homework04.utils.TestUtils.convertToRecords;

class WordFrequencyReducerTest {

    @Test
    void general() {
        var reducer = new WordFrequencyReducer("Word", "Tf");

        List<Record> actualRecords = applyReducerToAllGroups(reducer, inputGroups);
        assertRecordsEqual(expectedRecords, actualRecords);
    }

    private static final List<RecordGroup> inputGroups = List.of(
            group(
                    Map.of("Id", 1),
                    convertToRecords(
                            new String[]{"Id", "Word"},
                            new Object[][]{
                                    {1, "i"},
                                    {1, "love"},
                                    {1, "you"},
                            }
                    )
            ),
            group(
                    Map.of("Id", 2),
                    convertToRecords(
                            new String[]{"Id", "Word"},
                            new Object[][]{
                                    {2, "love"}
                            }
                    )
            ),
            group(
                    Map.of("Id", 3),
                    convertToRecords(
                            new String[]{"Id", "Word"},
                            new Object[][]{
                                    {3, "love"},
                                    {3, "love"},
                                    {3, "love"}
                            }
                    )
            ),
            group(
                    Map.of("Id", 4),
                    convertToRecords(
                            new String[]{"Id", "Word"},
                            new Object[][]{
                                    {4, "love"},
                                    {4, "i"},
                                    {4, "love"},
                                    {4, "you"},
                            }
                    )
            ),
            group(
                    Map.of("Id", 5),
                    convertToRecords(
                            new String[]{"Id", "Word"},
                            new Object[][]{
                                    {5, "i"},
                                    {5, "i"},
                                    {5, "you"},
                            }
                    )
            ),
            group(
                    Map.of("Id", 6),
                    convertToRecords(
                            new String[]{"Id", "Word"},
                            new Object[][]{
                                    {6, "you"},
                                    {6, "you"},
                                    {6, "you"},
                                    {6, "you"},
                                    {6, "i"}
                            }
                    )
            )
    );

    private static final List<Record> expectedRecords = convertToRecords(
            new String[]{"Id", "Word", "Tf"},
            new Object[][]{
                    {1, "i", 0.3333},
                    {1, "love", 0.3333},
                    {1, "you", 0.3333},

                    {2, "love", 1.0},

                    {3, "love", 1.0},

                    {4, "i", 0.25},
                    {4, "love", 0.5},
                    {4, "you", 0.25},

                    {5, "i", 0.666},
                    {5, "you", 0.333},

                    {6, "i", 0.2},
                    {6, "you", 0.8}
            }
    );
}