package io.github.javaasasecondlanguage.homework01;

import java.util.Map;

public class Utils {

    public static boolean rowsEqual(Row leftRow, Row rightRow, double precisionForDouble) {
        Map<String, Object> leftValues = leftRow.getValues();
        Map<String, Object> rightValues = rightRow.getValues();

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
}
