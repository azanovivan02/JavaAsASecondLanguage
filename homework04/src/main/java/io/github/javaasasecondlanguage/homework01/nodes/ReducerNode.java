package io.github.javaasasecondlanguage.homework01.nodes;

import io.github.javaasasecondlanguage.homework01.Record;
import io.github.javaasasecondlanguage.homework01.ops.Operator;
import io.github.javaasasecondlanguage.homework01.ops.Operator.Reducer;

public class ReducerNode extends ProcNode {

    private final Reducer reducer;

    public ReducerNode(Reducer reducer) {
        super();
        this.reducer = reducer;
    }

    @Override
    public Operator getOperator() {
        return reducer;
    }

    @Override
    public void push(Record inputRecord, int gateNumber) {
        reducer.apply(inputRecord, this::collect);
    }
}
