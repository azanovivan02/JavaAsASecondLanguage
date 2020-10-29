package io.github.javaasasecondlanguage.homework01.ops;

import io.github.javaasasecondlanguage.homework01.Row;
import io.github.javaasasecondlanguage.homework01.nodes.OutputCollector;

public interface Operator {

    interface Mapper extends Operator {
        void apply(Row inputRow, OutputCollector collector);
    }

    interface Reducer extends Operator {
        void apply(Row inputRow, OutputCollector collector);
    }

    interface Joiner extends Operator {
        void applyLeft(Row inputRow, OutputCollector collector);
        void applyRight(Row inputRow, OutputCollector collector);
    }

    enum OpType {
        MAPPER,
        REDUCER,
        JOINER
    }
}
