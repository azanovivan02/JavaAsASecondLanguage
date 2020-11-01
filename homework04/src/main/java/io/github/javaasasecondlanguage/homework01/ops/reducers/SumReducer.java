package io.github.javaasasecondlanguage.homework01.ops.reducers;

import io.github.javaasasecondlanguage.homework01.OutputCollector;
import io.github.javaasasecondlanguage.homework01.Record;
import io.github.javaasasecondlanguage.homework01.ops.Operator;

import java.util.Map;

/**
 * Counts sum of values in a specified column for each group and returns a single record with a sum.
 */
public class SumReducer implements Operator.Reducer {

    private final String inputColumn;
    private final String outputColumn;
    double currentSum = 0;

    public SumReducer(String inputColumn, String outputColumn) {
        this.inputColumn = inputColumn;
        this.outputColumn = outputColumn;
    }

    @Override
    public void apply(Record inputRecord, OutputCollector collector, Map<String, Object> groupByEntries) {
        currentSum += inputRecord.getDouble(inputColumn);
    }

    @Override
    public void signalGroupWasFinished(OutputCollector collector, Map<String, Object> groupByEntries) {
        Record outputRecord = new Record(groupByEntries);
        outputRecord.set(outputColumn, currentSum);
        collector.collect(outputRecord);

        currentSum = 0;
    }

}
