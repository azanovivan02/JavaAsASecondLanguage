package io.github.javaasasecondlanguage.homework01.ops.reducers;

import io.github.javaasasecondlanguage.homework01.Row;
import io.github.javaasasecondlanguage.homework01.nodes.OutputCollector;
import io.github.javaasasecondlanguage.homework01.ops.Operator;

import static io.github.javaasasecondlanguage.homework01.ops.OpUtils.equalByColumns;

public class FirstNReducer implements Operator.Reducer {

    private final int maxAmount;
    private final String[] groupByColumns;

    Row currentRow = null;
    private int currentCount = 0;

    public FirstNReducer(int maxAmount, String...groupByColumns) {
        this.maxAmount = maxAmount;
        this.groupByColumns = groupByColumns;
    }

    @Override
    public void apply(Row inputRow, OutputCollector collector) {
        if (inputRow.isTerminal()) {
            collector.collect(inputRow);
            return;
        }

        if (currentRow == null || !equalByColumns(inputRow, currentRow, groupByColumns)) {
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
