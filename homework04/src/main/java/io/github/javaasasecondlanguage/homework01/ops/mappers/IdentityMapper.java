package io.github.javaasasecondlanguage.homework01.ops.mappers;

import io.github.javaasasecondlanguage.homework01.OutputCollector;
import io.github.javaasasecondlanguage.homework01.Row;
import io.github.javaasasecondlanguage.homework01.ops.Operator.Mapper;

public class IdentityMapper implements Mapper {

    @Override
    public void apply(Row inputRow, OutputCollector collector) {
        collector.collect(inputRow);
    }
}
