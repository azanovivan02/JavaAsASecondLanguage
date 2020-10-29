package io.github.javaasasecondlanguage.homework01.ops.reducers;

import io.github.javaasasecondlanguage.homework01.Row;
import io.github.javaasasecondlanguage.homework01.nodes.OutputCollector;
import io.github.javaasasecondlanguage.homework01.ops.Operator;

import java.util.List;

import static io.github.javaasasecondlanguage.homework01.ops.OpUtils.equalByColumns;

public class FirstNReducer implements Operator.Reducer {

    private final int maxAmount;
    private List<String> keyColumns;

    Row currentRow = null;
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
    public void apply(Row inputRow, OutputCollector collector) {
        if (inputRow.isTerminal()) {
            collector.collect(inputRow);
            return;
        }

        if (currentRow == null || !equalByColumns(inputRow, currentRow, keyColumns)) {
            currentCount = 1;
            currentRow = inputRow;
        } else {
            currentCount++;
        }

        if (currentCount <= maxAmount) {
            collector.collect(inputRow);
        }
    }
}
