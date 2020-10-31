package io.github.javaasasecondlanguage.homework01.ops.mappers;

import io.github.javaasasecondlanguage.homework01.OutputCollector;
import io.github.javaasasecondlanguage.homework01.Record;
import io.github.javaasasecondlanguage.homework01.ops.Operator;

import java.util.function.Function;

public class AddColumnMapper implements Operator.Mapper {

    private final Function<Record, ?> lambda;
    private String outputColumn;

    public AddColumnMapper(String outputColumn, Function<Record, ?> lambda) {
        this.lambda = lambda;
        this.outputColumn = outputColumn;
    }

    @Override
    public void apply(Record inputRecord, OutputCollector collector) {
        var outputValue = lambda.apply(inputRecord);

        var outputRecord = inputRecord
                .copy()
                .set(outputColumn, outputValue);
        collector.collect(outputRecord);
    }
}
