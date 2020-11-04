package io.github.javaasasecondlanguage.homework04.reducers;

import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.ops.reducers.CountReducer;
import io.github.javaasasecondlanguage.homework04.utils.RecordGroup;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.github.javaasasecondlanguage.homework04.utils.AssertionUtils.assertRecordsEqual;
import static io.github.javaasasecondlanguage.homework04.utils.RecordGroup.group;
import static io.github.javaasasecondlanguage.homework04.utils.TestUtils.applyReducerToAllGroups;
import static io.github.javaasasecondlanguage.homework04.utils.TestUtils.convertToRecords;

class CountReducerTest {

    @Test
    void general() {
        var reducer = new CountReducer("Count");

        List<Record> actualRecords = applyReducerToAllGroups(reducer, inputGroups);
        assertRecordsEqual(expectedRecords, actualRecords);
    }

    private static List<RecordGroup> inputGroups = List.of(
            group(
                    Map.of("ItemId", 1),
                    convertToRecords(
                            new String[]{"ItemId", "UserId", "Clicks"},
                            new Object[][]{
                                    {1, 100, 5},
                                    {1, 200, 10},
                                    {1, 300, 5},
                            }
                    )
            ),
            group(
                    Map.of("ItemId", 2),
                    convertToRecords(
                            new String[]{"ItemId", "UserId", "Clicks"},
                            new Object[][]{
                                    {2, 100, 5},
                                    {2, 200, 7},
                            }
                    )
            ),
            group(
                    Map.of("ItemId", 3),
                    convertToRecords(
                            new String[]{"ItemId", "UserId", "Clicks"},
                            new Object[][]{
                                    {3, 200, 3},
                                    {3, 300, 10}
                            }
                    )
            )
    );

    private static List<Record> expectedRecords = convertToRecords(
            new String[]{"ItemId", "Count"},
            new Object[][]{
                    {1, 3},
                    {2, 2},
                    {3, 2},
            }
    );
}