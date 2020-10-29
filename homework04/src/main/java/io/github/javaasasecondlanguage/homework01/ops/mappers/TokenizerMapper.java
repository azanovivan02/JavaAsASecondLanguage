package io.github.javaasasecondlanguage.homework01.ops.mappers;

import io.github.javaasasecondlanguage.homework01.OutputCollector;
import io.github.javaasasecondlanguage.homework01.Row;
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
        var inputValue = inputRow.getString(inputColumn);
        var words = inputValue.split("[\\s,\\.\\!\\;\\?\\'\\:\"]+");
        for (String word : words) {
            var newRow = inputRow
                    .copyColumnsExcept(inputColumn)
                    .set(outputColumn, word);
            collector.collect(newRow);
        }
    }
}
