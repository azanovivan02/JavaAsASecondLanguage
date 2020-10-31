package io.github.javaasasecondlanguage.homework01.ops;

import io.github.javaasasecondlanguage.homework01.OutputCollector;
import io.github.javaasasecondlanguage.homework01.Record;

import java.util.List;

public interface Operator {

    interface Mapper extends Operator {
        void apply(Record inputRecord, OutputCollector collector);
    }

    interface Reducer extends Operator {
        List<String> getKeyColumns();
        void setKeyColumns(List<String> keyColumns);

        // TODO: must accept groupBy keys AND values
        void apply(Record inputRecord, OutputCollector collector);

        // TODO: must accept groupBy keys AND values
        default void signalGroupFinished() {
        }
    }

    interface Joiner extends Operator {
        List<String> getKeyColumns();
        void setKeyColumns(List<String> keyColumns);

        void applyLeft(Record inputRecord, OutputCollector collector);
        void applyRight(Record inputRecord, OutputCollector collector);
    }

}
