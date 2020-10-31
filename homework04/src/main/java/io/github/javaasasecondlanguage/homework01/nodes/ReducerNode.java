package io.github.javaasasecondlanguage.homework01.nodes;

import io.github.javaasasecondlanguage.homework01.Record;
import io.github.javaasasecondlanguage.homework01.ops.Operator;
import io.github.javaasasecondlanguage.homework01.ops.Operator.Reducer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static io.github.javaasasecondlanguage.homework01.Utils.smartEquals;

public class ReducerNode extends ProcNode {

    private final Reducer reducer;
    private final Collection<String> groupByKeys;

    private Map<String, Object> currentGroupByEntries = null;

    public ReducerNode(Reducer reducer, Collection<String> groupByKeys) {
        this.reducer = reducer;
        this.groupByKeys = groupByKeys;
    }

    @Override
    public Operator getOperator() {
        return reducer;
    }

    @Override
    public void push(Record inputRecord, int gateNumber) {
        if (inputRecord.isTerminal()) {
            if (currentGroupByEntries != null) {
                reducer.signalGroupWasFinished(this::collect, currentGroupByEntries);
            }

            collect(Record.terminalRecord());
            return;
        }

        var inputGroupByEntries = inputRecord.getAll(groupByKeys);
        if (!smartEquals(inputGroupByEntries, currentGroupByEntries)) {
            if (currentGroupByEntries != null) {
                reducer.signalGroupWasFinished(this::collect, currentGroupByEntries);
            }
            currentGroupByEntries = inputGroupByEntries;
        }

        reducer.apply(inputRecord, this::collect, currentGroupByEntries);
    }

    private static Map<String, Object> getGroupByValues(Record record, Collection<String> groupByKeys) {
        var currentGroupByValues = new HashMap<String, Object>();
        for (var key : groupByKeys) {
            var value = record.get(key);
            currentGroupByValues.put(key, value);
        }
        return currentGroupByValues;
    }

    public static boolean equalByColumns(Record left, Record right, Collection<String> keyColumns) {
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
}
