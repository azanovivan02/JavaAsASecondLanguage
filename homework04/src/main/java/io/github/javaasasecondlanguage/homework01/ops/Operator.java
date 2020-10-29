package io.github.javaasasecondlanguage.homework01.ops;

import io.github.javaasasecondlanguage.homework01.Row;
import io.github.javaasasecondlanguage.homework01.nodes.OutputCollector;

import java.util.List;

public interface Operator {

    interface Mapper extends Operator {
        void apply(Row inputRow, OutputCollector collector);
    }

    interface Reducer extends Operator {
        List<String> getKeyColumns();
        void setKeyColumns(List<String> keyColumns);
        void apply(Row inputRow, OutputCollector collector);
    }

    interface Joiner extends Operator {
        List<String> getKeyColumns();

        void setKeyColumns(List<String> keyColumns);

        void applyLeft(Row inputRow, OutputCollector collector);
        void applyRight(Row inputRow, OutputCollector collector);
    }

    enum OpType {
        MAPPER,
        REDUCER,
        JOINER
    }
}
