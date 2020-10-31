package io.github.javaasasecondlanguage.homework01.ops.mappers;

import io.github.javaasasecondlanguage.homework01.OutputCollector;
import io.github.javaasasecondlanguage.homework01.Record;
import io.github.javaasasecondlanguage.homework01.ops.Operator;

public class TokenizerMapper implements Operator.Mapper {

    private String inputColumn;
    private String outputColumn;

    public TokenizerMapper(String inputColumn, String outputColumn) {
        this.inputColumn = inputColumn;
        this.outputColumn = outputColumn;
    }

    @Override
    public void apply(Record inputRecord, OutputCollector collector) {
        var inputValue = inputRecord.getString(inputColumn);
        var words = inputValue.split("[\\s,\\.\\!\\;\\?\\'\\:\"]+");
        for (String word : words) {
            var newRecord = inputRecord
                    .copyColumnsExcept(inputColumn)
                    .set(outputColumn, word);
            collector.collect(newRecord);
        }
    }
}
