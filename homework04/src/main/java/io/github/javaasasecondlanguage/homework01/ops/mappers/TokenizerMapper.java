package io.github.javaasasecondlanguage.homework01.ops.mappers;

import io.github.javaasasecondlanguage.homework01.OutputCollector;
import io.github.javaasasecondlanguage.homework01.Record;
import io.github.javaasasecondlanguage.homework01.ops.Operator;

import static java.util.List.of;

public class TokenizerMapper implements Operator.Mapper {

    private static final String SPLIT_PATTERN = "[\\s,\\.\\!\\;\\?\\'\\:\"]+";

    private final String inputColumn;
    private final String outputColumn;

    public TokenizerMapper(String inputColumn, String outputColumn) {
        this.inputColumn = inputColumn;
        this.outputColumn = outputColumn;
    }

    @Override
    public void apply(Record inputRecord, OutputCollector collector) {
        var inputValue = inputRecord.getString(inputColumn);
        var words = inputValue.split(SPLIT_PATTERN);
        for (String word : words) {
            var newRecord = inputRecord
                    .copyColumnsExcept(of(inputColumn))
                    .set(outputColumn, word);
            collector.collect(newRecord);
        }
    }
}
