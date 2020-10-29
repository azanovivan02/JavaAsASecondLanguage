package io.github.javaasasecondlanguage.homework01.ops.mappers;

import io.github.javaasasecondlanguage.homework01.Row;
import io.github.javaasasecondlanguage.homework01.nodes.OutputCollector;
import io.github.javaasasecondlanguage.homework01.ops.Operator;

import java.util.function.Function;

public class LambdaMapper<I> implements Operator.Mapper {

    private final Function<I, ?> lambda;
    private String column;

    public LambdaMapper(String column, Function<I, ?> lambda) {
        this.lambda = lambda;
        this.column = column;
    }

    @Override
    public void apply(Row inputRow, OutputCollector collector) {
        I inputValue = (I) inputRow.get(column);
        Object outputValue = lambda.apply(inputValue);

        Row outputRow = inputRow
                .copy()
                .set(column, outputValue);
        collector.collect(outputRow);
    }
}
