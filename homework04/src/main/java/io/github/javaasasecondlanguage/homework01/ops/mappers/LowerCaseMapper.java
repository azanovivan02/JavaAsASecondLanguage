package io.github.javaasasecondlanguage.homework01.ops.mappers;

import io.github.javaasasecondlanguage.homework01.OutputCollector;
import io.github.javaasasecondlanguage.homework01.Record;
import io.github.javaasasecondlanguage.homework01.ops.Operator;

public class LowerCaseMapper implements Operator.Mapper {

    private final String column;

    public LowerCaseMapper(String column) {
        this.column = column;
    }

    @Override
    public void apply(Record inputRecord, OutputCollector collector) {
        var inputValue = inputRecord.getString(column);
        var lowerCaseValue = inputValue.toLowerCase();

        var outputRecord = inputRecord
                .copy()
                .set(column, lowerCaseValue);
        collector.collect(outputRecord);
    }
}
