package io.github.javaasasecondlanguage.homework01.ops;

import io.github.javaasasecondlanguage.homework01.Row;

import java.util.List;

public class OpUtils {
    public static boolean equalByColumns(Row left, Row right, List<String> keyColumns) {
        if (left == null || right == null) {
            return false;
        }

        for (String column : keyColumns) {
            Object leftValue = left.get(column);
            Object rightValue = right.get(column);
            if (!leftValue.equals(rightValue)) {
                return false;
            }
        }
        return true;
    }

    public static int compareRows(Row o1, Row o2, List<String> keyColumns) {
        for (String column : keyColumns) {
            Comparable leftValue = o1.getComparable(column);
            Comparable rightValue = o2.getComparable(column);
            int comparisonResult = leftValue.compareTo(rightValue);
            if (comparisonResult != 0) {
                return comparisonResult;
            }
        }

        return 0;
    }
}
