package io.github.javaasasecondlanguage.homework01.ops.reducers;

import io.github.javaasasecondlanguage.homework01.OutputCollector;
import io.github.javaasasecondlanguage.homework01.Record;
import io.github.javaasasecondlanguage.homework01.ops.Operator;

import java.util.Map;

public class CountReducer implements Operator.Reducer {

    private final String outputColumn;
    int currentCount = 0;

    public CountReducer(String outputColumn) {
        this.outputColumn = outputColumn;
    }

    @Override
    public void apply(Record inputRecord, OutputCollector collector, Map<String, Object> groupByValues) {
        currentCount++;
    }

    @Override
    public void signalGroupWasFinished(OutputCollector collector, Map<String, Object> groupByValues) {
        Record outputRecord = new Record(groupByValues);
        outputRecord.set(outputColumn, currentCount);
        collector.collect(outputRecord);

        currentCount = 0;
    }
}
