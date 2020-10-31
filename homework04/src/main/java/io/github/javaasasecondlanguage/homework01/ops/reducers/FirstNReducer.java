package io.github.javaasasecondlanguage.homework01.ops.reducers;

import io.github.javaasasecondlanguage.homework01.OutputCollector;
import io.github.javaasasecondlanguage.homework01.Record;
import io.github.javaasasecondlanguage.homework01.ops.Operator;

import java.util.Map;

public class FirstNReducer implements Operator.Reducer {

    private final int maxAmount;
    private int currentCount = 0;

    public FirstNReducer(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    @Override
    public void apply(Record inputRecord, OutputCollector collector, Map<String, Object> groupByEntries) {
        if (currentCount < maxAmount) {
            currentCount++;
            collector.collect(inputRecord);
        }
    }

    @Override
    public void signalGroupWasFinished(OutputCollector collector, Map<String, Object> groupByEntries) {
        currentCount = 0;
    }
}
