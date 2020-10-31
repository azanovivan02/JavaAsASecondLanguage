package io.github.javaasasecondlanguage.homework01.nodes;

import io.github.javaasasecondlanguage.homework01.Record;
import io.github.javaasasecondlanguage.homework01.ops.OpUtils;
import io.github.javaasasecondlanguage.homework01.ops.Operator;
import io.github.javaasasecondlanguage.homework01.ops.Operator.Reducer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ReducerNode extends ProcNode {

    private final Reducer reducer;
    private final Collection<String> groupByKeys;

    private Record currentRecord = null;
    private Map<String, Object> currentGroupByValues = null;

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
            if (currentRecord != null) {
                reducer.signalGroupWasFinished(this::collect, currentGroupByValues);
            }

            collect(Record.terminalRecord());
            return;
        }

        if (!OpUtils.equalByColumns(inputRecord, currentRecord, groupByKeys)) {
            if (currentRecord != null) {
                reducer.signalGroupWasFinished(this::collect, currentGroupByValues);
            }
            currentRecord = inputRecord;
            currentGroupByValues = getGroupByValues(inputRecord, groupByKeys);
        }

        reducer.apply(inputRecord, this::collect, currentGroupByValues);
    }

    private static Map<String, Object> getGroupByValues(Record record, Collection<String> groupByKeys) {
        var currentGroupByValues = new HashMap<String, Object>();
        for (var key : groupByKeys) {
            var value = record.get(key);
            currentGroupByValues.put(key, value);
        }
        return currentGroupByValues;
    }
}
