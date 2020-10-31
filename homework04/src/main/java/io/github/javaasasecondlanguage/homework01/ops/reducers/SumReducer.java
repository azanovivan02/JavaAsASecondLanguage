package io.github.javaasasecondlanguage.homework01.ops.reducers;

import io.github.javaasasecondlanguage.homework01.OutputCollector;
import io.github.javaasasecondlanguage.homework01.Record;
import io.github.javaasasecondlanguage.homework01.ops.OpUtils;
import io.github.javaasasecondlanguage.homework01.ops.Operator;

import java.util.List;

public class SumReducer implements Operator.Reducer {

    private final String inputColumn;
    private final String outputSumColumn;
    private List<String> keyColumns;

    Record currentRecord = null;
    double currentSum = 0;

    public SumReducer(String inputColumn, String outputSumColumn) {
        this.inputColumn = inputColumn;
        this.outputSumColumn = outputSumColumn;
    }

    @Override
    public List<String> getKeyColumns() {
        return keyColumns;
    }

    @Override
    public void setKeyColumns(List<String> keyColumns) {
        this.keyColumns = keyColumns;
    }

    @Override
    public void apply(Record inputRecord, OutputCollector collector) {
        if (inputRecord.isTerminal()) {
            if (currentRecord != null) {
                outputCountrecord(collector);
            }

            currentRecord = null;
            currentSum = 0;
            collector.collect(inputRecord);
            return;
        }

        if (currentRecord == null || !OpUtils.equalByColumns(inputRecord, currentRecord, keyColumns)) {
            if (currentRecord != null) {
                outputCountrecord(collector);
            }
            currentRecord = inputRecord;
            currentSum = 0;
        }

        currentSum += inputRecord.getDouble(inputColumn);
    }

    private void outputCountrecord(OutputCollector collector) {
        Record newRecord = currentRecord.copyColumns(keyColumns);
        newRecord.set(outputSumColumn, currentSum);
        collector.collect(newRecord);
    }

}
