package io.github.javaasasecondlanguage.homework01.ops.reducers;

import io.github.javaasasecondlanguage.homework01.OutputCollector;
import io.github.javaasasecondlanguage.homework01.Row;
import io.github.javaasasecondlanguage.homework01.ops.OpUtils;
import io.github.javaasasecondlanguage.homework01.ops.Operator;

import java.util.List;

public class CountReducer implements Operator.Reducer {

    private String outputCountColumn;
    private List<String> keyColumns;

    Row currentRow = null;
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
    public void apply(Row inputRow, OutputCollector collector) {
        if (inputRow.isTerminal()) {
            if (currentRow != null) {
                outputCountRow(collector);
            }

            currentRow = null;
            collector.collect(inputRow);
            return;
        }

        if (currentRow == null || !OpUtils.equalByColumns(inputRow, currentRow, keyColumns)) {
            if (currentRow != null) {
                outputCountRow(collector);
            }
            currentCount = 1;
            currentRow = inputRow;
        } else {
            currentCount++;
        }
    }

    private void outputCountRow(OutputCollector collector) {
        Row newRow = currentRow.copyColumns(keyColumns);
        newRow.set(outputCountColumn, currentCount);
        collector.collect(newRow);
    }

}
