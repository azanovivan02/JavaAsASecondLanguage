package io.github.javaasasecondlanguage.homework01.ops.mappers;

import io.github.javaasasecondlanguage.homework01.OutputCollector;
import io.github.javaasasecondlanguage.homework01.Record;
import io.github.javaasasecondlanguage.homework01.ops.Operator;

public class Printer implements Operator.Mapper {

    private final String prefix;

    public Printer(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void apply(Record inputRecord, OutputCollector collector) {
        System.out.printf("%s: %s%n", prefix, inputRecord);
        collector.collect(inputRecord);
    }
}
