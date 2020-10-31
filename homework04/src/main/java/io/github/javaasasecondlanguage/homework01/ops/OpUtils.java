package io.github.javaasasecondlanguage.homework01.ops;

import io.github.javaasasecondlanguage.homework01.Record;

import java.util.List;

public class OpUtils {
    public static boolean equalByColumns(Record left, Record right, List<String> keyColumns) {
        if (left == null || right == null) {
            return false;
        }

        if (keyColumns == null) {
            throw new IllegalArgumentException("Key columns are null");
        }

        for (var column : keyColumns) {
            var leftValue = left.get(column);
            var rightValue = right.get(column);
            if (!leftValue.equals(rightValue)) {
                return false;
            }
        }
        return true;
    }

    public static int compareRecords(Record o1, Record o2, List<String> keyColumns) {
        for (var column : keyColumns) {
            var leftValue = getComparable(o1, column);
            var rightValue = getComparable(o2, column);
            int comparisonResult = leftValue.compareTo(rightValue);
            if (comparisonResult != 0) {
                return comparisonResult;
            }
        }

        return 0;
    }

    public static Comparable getComparable(Record record, String column) {
        Double doubleValue = record.getDoubleOrNull(column);
        if (doubleValue != null) {
            return doubleValue;
        } else {
            String stringValue = record.get(column);
            return stringValue;
        }
    }
}
