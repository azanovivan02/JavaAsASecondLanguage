package io.github.javaasasecondlanguage.homework01.ops.mappers;

import io.github.javaasasecondlanguage.homework01.Row;
import io.github.javaasasecondlanguage.homework01.nodes.OutputCollector;
import io.github.javaasasecondlanguage.homework01.ops.Operator;

import java.util.function.Function;

public class AddColumnMapper implements Operator.Mapper {

    private final Function<Row, ?> lambda;
    private String outputColumn;

    public AddColumnMapper(String outputColumn, Function<Row, ?> lambda) {
        this.lambda = lambda;
        this.outputColumn = outputColumn;
    }

    @Override
    public void apply(Row inputRow, OutputCollector collector) {
        Object outputValue = lambda.apply(inputRow);

        Row outputRow = inputRow
                .copy()
                .set(outputColumn, outputValue);
        collector.collect(outputRow);
    }
}