package io.github.javaasasecondlanguage.homework01.reducers;

import io.github.javaasasecondlanguage.homework01.Record;
import io.github.javaasasecondlanguage.homework01.ops.reducers.FirstNReducer;
import io.github.javaasasecondlanguage.homework01.ops.reducers.SumReducer;
import io.github.javaasasecondlanguage.homework01.utils.RecordGroup;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.github.javaasasecondlanguage.homework01.utils.AssertionUtils.assertRecordsEqual;
import static io.github.javaasasecondlanguage.homework01.utils.RecordGroup.group;
import static io.github.javaasasecondlanguage.homework01.utils.TestUtils.applyReducerToAllGroups;
import static io.github.javaasasecondlanguage.homework01.utils.TestUtils.convertToRecords;

class FirstNReducerTest {

    @Test
    void general() {
        var reducer = new FirstNReducer(2);

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
                                    {1, 400, 50},
                            }
                    )
            ),
            group(
                    Map.of("ItemId", 2),
                    convertToRecords(
                            new String[]{"ItemId", "UserId", "Clicks"},
                            new Object[][]{
                                    {2, 100, 5},
                            }
                    )
            ),
            group(
                    Map.of("ItemId", 3),
                    convertToRecords(
                            new String[]{"ItemId", "UserId", "Clicks"},
                            new Object[][]{
                                    {3, 200, 3},
                                    {3, 300, 10},
                                    {3, 600, 10}
                            }
                    )
            )
    );

    private static List<Record> expectedRecords = convertToRecords(
            new String[]{"ItemId", "UserId", "Clicks"},
            new Object[][]{
                    {1, 100, 5},
                    {1, 200, 10},
                    {2, 100, 5},
                    {3, 200, 3},
                    {3, 300, 10}
            }
    );
}