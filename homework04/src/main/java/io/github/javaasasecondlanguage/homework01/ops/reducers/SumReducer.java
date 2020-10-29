package io.github.javaasasecondlanguage.homework01.ops.reducers;

import io.github.javaasasecondlanguage.homework01.Row;
import io.github.javaasasecondlanguage.homework01.nodes.OutputCollector;
import io.github.javaasasecondlanguage.homework01.ops.OpUtils;
import io.github.javaasasecondlanguage.homework01.ops.Operator;

public class SumReducer implements Operator.Reducer {

    private final String inputColumn;
    private final String outputSumColumn;
    private final String[] groupByColumns;

    Row currentRow = null;
    double currentSum = 0;

    public SumReducer(String inputColumn, String outputSumColumn, String... groupByColumns) {
        this.inputColumn = inputColumn;
        this.outputSumColumn = outputSumColumn;
        this.groupByColumns = groupByColumns;
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

        if (currentRow == null || !OpUtils.equalByColumns(inputRow, currentRow, groupByColumns)) {
            if (currentRow != null) {
                outputCountRow(collector);
            }
            currentRow = inputRow;
            currentSum = 0;
        }

        currentSum += inputRow.getDouble(inputColumn);
    }

    private void outputCountRow(OutputCollector collector) {
        Row newRow = currentRow.copyColumns(groupByColumns);
        newRow.set(outputSumColumn, currentSum);
        collector.collect(newRow);
    }

}
