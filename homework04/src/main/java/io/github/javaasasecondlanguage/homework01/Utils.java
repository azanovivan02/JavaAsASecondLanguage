package io.github.javaasasecondlanguage.homework01;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {

    public static boolean smartEquals(Map<String, Object> left, Map<String, Object> right) {
        if (left == null || right == null) {
            return false;
        }

        if (!left.keySet().equals(right.keySet())) {
            return false;
        }

        for (String key : left.keySet()) {
            Object leftValue = left.get(key);
            Object rightValue = right.get(key);

            if (!smartEquals(leftValue, rightValue)) {
                return false;
            }
        }

        return true;
    }

    public static boolean smartEquals(Object left, Object right) {
        if (left == null || right == null) {
            return false;
        }

        if (!(left instanceof Number) || !(right instanceof Number)) {
            return left.equals(right);
        }

        double leftDouble = ((Number) left).doubleValue();
        double rightDouble = ((Number) right).doubleValue();
        double precisionForDouble = 0.001;
        return Math.abs(leftDouble - rightDouble) < precisionForDouble;
    }

    public static int compareRecords(Record leftRecord, Record rightRecord, List<String> keyColumns) {
        for (var column : keyColumns) {
            var leftValue = getComparable(leftRecord, column);
            var rightValue = getComparable(rightRecord, column);
            int comparisonResult = leftValue.compareTo(rightValue);
            if (comparisonResult != 0) {
                return comparisonResult;
            }
        }

        return 0;
    }

    public static Comparable getComparable(Record record, String column) {
        Double doubleValue = record.getDouble(column);
        if (doubleValue != null) {
            return doubleValue;
        } else {
            var stringValue = record.getString(column);
            return stringValue;
        }
    }

    public static String recordsToString(Collection<Record> records) {
        return records
                .stream()
                .map(Record::toString)
                .collect(Collectors.joining("\n"));
    }
}
