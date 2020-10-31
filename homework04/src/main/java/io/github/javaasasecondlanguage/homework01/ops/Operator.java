package io.github.javaasasecondlanguage.homework01.ops;

import io.github.javaasasecondlanguage.homework01.OutputCollector;
import io.github.javaasasecondlanguage.homework01.Record;

import java.util.List;
import java.util.Map;

public interface Operator {

    interface Mapper extends Operator {
        void apply(Record inputRecord, OutputCollector collector);
    }

    interface Reducer extends Operator {
        void apply(Record inputRecord, OutputCollector collector, Map<String, Object> groupByEntries);
        void signalGroupWasFinished(OutputCollector collector, Map<String, Object> groupByEntries);
    }

    interface Joiner extends Operator {
        List<String> getKeyColumns();
        void setKeyColumns(List<String> keyColumns);

        void applyLeft(Record inputRecord, OutputCollector collector);
        void applyRight(Record inputRecord, OutputCollector collector);
    }

}
