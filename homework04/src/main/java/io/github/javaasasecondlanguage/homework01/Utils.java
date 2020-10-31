package io.github.javaasasecondlanguage.homework01;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {

    public static boolean recordsEqual(Record leftRecord, Record rightRecord, double precisionForDouble) {
        Map<String, Object> leftValues = leftRecord.getValues();
        Map<String, Object> rightValues = rightRecord.getValues();

        if (!leftValues.keySet().equals(rightValues.keySet())) {
            return false;
        }

        for (String key : leftValues.keySet()) {
            Object leftValue = leftValues.get(key);
            Object rightValue = rightValues.get(key);

            if (leftValue instanceof Number && rightValue instanceof Number) {
                double leftDouble = ((Number) leftValue).doubleValue();
                double rightDouble = ((Number) rightValue).doubleValue();
                if (Math.abs(leftDouble - rightDouble) > precisionForDouble) {
                    return false;
                }
            } else if (!leftValue.equals(rightValue)) {
                return false;
            }
        }

        return true;
    }

    public static String recordsToString(Collection<Record> records) {
        return records
                .stream()
                .map(Record::toString)
                .collect(Collectors.joining("\n"));
    }
}
