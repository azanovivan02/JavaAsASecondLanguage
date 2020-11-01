package io.github.javaasasecondlanguage.homework01.ops.mappers;

import io.github.javaasasecondlanguage.homework01.OutputCollector;
import io.github.javaasasecondlanguage.homework01.Record;
import io.github.javaasasecondlanguage.homework01.ops.Operator.Mapper;

/**
 * Does absolutely nothing useful.
 */
public class IdentityMapper implements Mapper {

    @Override
    public void apply(Record inputRecord, OutputCollector collector) {
        collector.collect(inputRecord);
    }
}
