package io.github.javaasasecondlanguage.homework01.ops.reducers;

import io.github.javaasasecondlanguage.homework01.OutputCollector;
import io.github.javaasasecondlanguage.homework01.Record;
import io.github.javaasasecondlanguage.homework01.ops.Operator;

import java.util.List;

import static io.github.javaasasecondlanguage.homework01.ops.OpUtils.equalByColumns;

public class FirstNReducer implements Operator.Reducer {

    private final int maxAmount;
    private List<String> keyColumns;

    Record currentRecord = null;
    private int currentCount = 0;

    public FirstNReducer(int maxAmount) {
        this.maxAmount = maxAmount;
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
            collector.collect(inputRecord);
            return;
        }

        if (currentRecord == null || !equalByColumns(inputRecord, currentRecord, keyColumns)) {
            currentCount = 1;
            currentRecord = inputRecord;
        } else {
            currentCount++;
        }

        if (currentCount <= maxAmount) {
            collector.collect(inputRecord);
        }
    }
}
