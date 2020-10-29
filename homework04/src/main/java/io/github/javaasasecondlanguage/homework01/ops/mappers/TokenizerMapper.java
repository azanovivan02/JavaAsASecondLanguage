package io.github.javaasasecondlanguage.homework01.ops.mappers;

import io.github.javaasasecondlanguage.homework01.Row;
import io.github.javaasasecondlanguage.homework01.nodes.OutputCollector;
import io.github.javaasasecondlanguage.homework01.ops.Operator;

public class TokenizerMapper implements Operator.Mapper {

    private String inputColumn;
    private String outputColumn;

    public TokenizerMapper(String inputColumn, String outputColumn) {
        this.inputColumn = inputColumn;
        this.outputColumn = outputColumn;
    }

    @Override
    public void apply(Row inputRow, OutputCollector collector) {
        String inputValue = inputRow.getString(inputColumn);
        String[] words = inputValue.split("[\\s,\\.\\!\\;\\?\\'\\:\"]+");
        for (String word : words) {
            Row newRow = inputRow
                    .copyColumnsExcept(inputColumn)
                    .set(outputColumn, word);
            collector.collect(newRow);
        }
    }
}
