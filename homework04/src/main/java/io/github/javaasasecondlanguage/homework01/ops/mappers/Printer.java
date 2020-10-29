package io.github.javaasasecondlanguage.homework01.ops.mappers;

import io.github.javaasasecondlanguage.homework01.Row;
import io.github.javaasasecondlanguage.homework01.nodes.OutputCollector;
import io.github.javaasasecondlanguage.homework01.ops.Operator;

public class Printer implements Operator.Mapper {

    private final String prefix;

    public Printer(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void apply(Row inputRow, OutputCollector collector) {
        System.out.printf("%s: %s%n", prefix, inputRow);
        collector.collect(inputRow);
    }
}
