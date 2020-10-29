package io.github.javaasasecondlanguage.homework01.ops.reducers;

import io.github.javaasasecondlanguage.homework01.Row;
import io.github.javaasasecondlanguage.homework01.nodes.OutputCollector;
import io.github.javaasasecondlanguage.homework01.ops.OpUtils;
import io.github.javaasasecondlanguage.homework01.ops.Operator;

import java.util.List;

public class SumReducer implements Operator.Reducer {

    private final String inputColumn;
    private final String outputSumColumn;
    private List<String> keyColumns;

    Row currentRow = null;
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
    public void apply(Row inputRow, OutputCollector collector) {
        if (inputRow.isTerminal()) {
            if (currentRow != null) {
                outputCountRow(collector);
            }

            currentRow = null;
            currentSum = 0;
            collector.collect(inputRow);
            return;
        }

        if (currentRow == null || !OpUtils.equalByColumns(inputRow, currentRow, keyColumns)) {
            if (currentRow != null) {
                outputCountRow(collector);
            }
            currentRow = inputRow;
            currentSum = 0;
        }

        currentSum += inputRow.getDouble(inputColumn);
    }

    private void outputCountRow(OutputCollector collector) {
        Row newRow = currentRow.copyColumns(keyColumns);
        newRow.set(outputSumColumn, currentSum);
        collector.collect(newRow);
    }

}
