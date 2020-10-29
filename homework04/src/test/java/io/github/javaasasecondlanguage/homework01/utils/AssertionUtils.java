package io.github.javaasasecondlanguage.homework01.utils;

import io.github.javaasasecondlanguage.homework01.Row;

import java.util.List;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssertionUtils {


    public static void assertRowsEqual(List<Row> expectedRows, List<Row> actualRows) {
        for (var rowIndex = 0; rowIndex < expectedRows.size(); rowIndex++) {
            var actualRow = actualRows.get(rowIndex);
            var expectedRow = expectedRows.get(rowIndex);
            assertEquals(expectedRow, actualRow, format("Row index: %d", rowIndex));
        }
    }
}
