package io.github.javaasasecondlanguage.homework01.ops.reducers;

import io.github.javaasasecondlanguage.homework01.OutputCollector;
import io.github.javaasasecondlanguage.homework01.Record;
import io.github.javaasasecondlanguage.homework01.ops.OpUtils;
import io.github.javaasasecondlanguage.homework01.ops.Operator;

import java.util.List;

public class CountReducer implements Operator.Reducer {

    private String outputCountColumn;
    private List<String> keyColumns;

    Record currentRecord = null;
    int currentCount = 0;

    public CountReducer(String outputCountColumn) {
        this.outputCountColumn = outputCountColumn;
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
                outputCountRecord(collector);
            }

            currentRecord = null;
            collector.collect(inputRecord);
            return;
        }

        if (currentRecord == null || !OpUtils.equalByColumns(inputRecord, currentRecord, keyColumns)) {
            if (currentRecord != null) {
                outputCountRecord(collector);
            }
            currentCount = 1;
            currentRecord = inputRecord;
        } else {
            currentCount++;
        }
    }

    private void outputCountRecord(OutputCollector collector) {
        Record newRecord = currentRecord.copyColumns(keyColumns);
        newRecord.set(outputCountColumn, currentCount);
        collector.collect(newRecord);
    }

}
