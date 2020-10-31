package io.github.javaasasecondlanguage.homework01;

import io.github.javaasasecondlanguage.homework01.ops.Operator;
import io.github.javaasasecondlanguage.homework01.ops.Operator.Joiner;
import io.github.javaasasecondlanguage.homework01.ops.Operator.Mapper;
import io.github.javaasasecondlanguage.homework01.ops.Operator.Reducer;

import java.util.ArrayList;
import java.util.List;

import static io.github.javaasasecondlanguage.homework01.ops.Operator.OpType.JOINER;
import static io.github.javaasasecondlanguage.homework01.ops.Operator.OpType.MAPPER;
import static io.github.javaasasecondlanguage.homework01.ops.Operator.OpType.REDUCER;

public class ProcNode {

    private final Operator operator;
    private final Operator.OpType opType;
    private final List<Connection> connections;

    public ProcNode(Operator operator) {
        this.operator = operator;
        this.connections = new ArrayList<>();
        this.opType = calculateOpType(operator);
    }

    public Operator getOperator() {
        return operator;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public Operator.OpType getOpType() {
        return opType;
    }

    public void addConnection(ProcNode node, int gate) {
        Connection connection = new Connection(node, gate);
        connections.add(connection);
    }

    public void push(Record inputRecord, int gateNumber) {
        switch (opType) {
            case MAPPER: {
                Mapper mapper = (Mapper) operator;
                if (!inputRecord.isTerminal()) {
                    mapper.apply(inputRecord, this::collect);
                } else {
                    collect(inputRecord);
                }
                break;
            }
            case REDUCER: {
                Reducer reducer = (Reducer) operator;
                reducer.apply(inputRecord, this::collect);
                break;
            }
            case JOINER: {
                Joiner joiner = (Joiner) this.operator;
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
                        throw new IllegalStateException("Unknown gate");
                    }
                }
            }
        }
    }

    public final void pushIntoZero(Record inputRecord) {
        push(inputRecord, 0);
    }

    private void collect(Record record) {
        for (Connection info : connections) {
            var node = info.getNode();
            var gateNumber = info.getGate();
            node.push(record, gateNumber);
        }
    }

    private static Operator.OpType calculateOpType(Operator operator) {
        if (operator instanceof Mapper) {
            return MAPPER;
        } else if (operator instanceof Reducer) {
            return REDUCER;
        } else {
            return JOINER;
        }
    }
}