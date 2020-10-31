package io.github.javaasasecondlanguage.homework01.nodes;

import io.github.javaasasecondlanguage.homework01.Record;
import io.github.javaasasecondlanguage.homework01.ops.Operator;
import io.github.javaasasecondlanguage.homework01.ops.Operator.Joiner;

public class JoinNode extends ProcNode {

    private Joiner joiner;

    public JoinNode(Joiner joiner) {
        super();
        this.joiner = joiner;
    }

    @Override
    public Operator getOperator() {
        return joiner;
    }

    @Override
    public void push(Record inputRecord, int gateNumber) {
        switch (gateNumber) {
            case 0: {
                joiner.applyLeft(inputRecord, this::collect);
                break;
            }
            case 1: {
                joiner.applyRight(inputRecord, this::collect);
                break;
            }
            default: {
                throw new IllegalStateException("Unknown gate: "+gateNumber);
            }
        }
    }
}
