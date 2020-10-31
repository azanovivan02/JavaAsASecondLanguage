package io.github.javaasasecondlanguage.homework01.ops.reducers;

import io.github.javaasasecondlanguage.homework01.OutputCollector;
import io.github.javaasasecondlanguage.homework01.Record;
import io.github.javaasasecondlanguage.homework01.ops.Operator;

import java.util.Map;

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
        currentSum += inputRecord.getDoubleOrNull(inputColumn);
    }

    @Override
    public void signalGroupWasFinished(OutputCollector collector, Map<String, Object> groupByEntries) {
        Record outputRecord = new Record(groupByEntries);
        outputRecord.set(outputColumn, currentSum);
        collector.collect(outputRecord);

        currentSum = 0;
    }

}
