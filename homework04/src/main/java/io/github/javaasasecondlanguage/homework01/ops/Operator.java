package io.github.javaasasecondlanguage.homework01.ops;

import io.github.javaasasecondlanguage.homework01.OutputCollector;
import io.github.javaasasecondlanguage.homework01.Record;

import java.util.Map;

public interface Operator {

    interface Mapper extends Operator {
        void apply(Record inputRecord, OutputCollector collector);
    }

    interface Reducer extends Operator {
        void apply(Record inputRecord, OutputCollector collector, Map<String, Object> groupByEntries);
        void signalGroupWasFinished(OutputCollector collector, Map<String, Object> groupByEntries);
    }
}
