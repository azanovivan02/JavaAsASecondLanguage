package io.github.javaasasecondlanguage.homework01.nodes;

import io.github.javaasasecondlanguage.homework01.Record;
import io.github.javaasasecondlanguage.homework01.ops.Operator;
import io.github.javaasasecondlanguage.homework01.ops.Operator.Reducer;

import java.util.Collection;
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
        if (gateNumber != 0) {
            throw new IllegalArgumentException("Gate does not exist: "+gateNumber);
        }

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

}
