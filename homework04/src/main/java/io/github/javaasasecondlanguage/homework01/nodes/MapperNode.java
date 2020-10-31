package io.github.javaasasecondlanguage.homework01.nodes;

import io.github.javaasasecondlanguage.homework01.Record;
import io.github.javaasasecondlanguage.homework01.ops.Operator;
import io.github.javaasasecondlanguage.homework01.ops.Operator.Mapper;

public class MapperNode extends ProcNode {

    private final Mapper mapper;

    public MapperNode(Mapper mapper) {
        super();
        this.mapper = mapper;
    }

    @Override
    public Operator getOperator() {
        return mapper;
    }

    @Override
    public void push(Record inputRecord, int gateNumber) {
        if (!inputRecord.isTerminal()) {
            mapper.apply(inputRecord, this::collect);
        } else {
            collect(inputRecord);
        }
    }
}
