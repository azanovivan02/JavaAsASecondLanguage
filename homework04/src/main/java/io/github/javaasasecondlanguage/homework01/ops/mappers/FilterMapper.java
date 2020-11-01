package io.github.javaasasecondlanguage.homework01.ops.mappers;

import io.github.javaasasecondlanguage.homework01.OutputCollector;
import io.github.javaasasecondlanguage.homework01.Record;
import io.github.javaasasecondlanguage.homework01.ops.Operator;

import java.util.function.Function;

/**
 * Drops records if they return true on predicate.
 */
public class FilterMapper implements Operator.Mapper {

    private final Function<Record, Boolean> predicate;

    public FilterMapper(Function<Record, Boolean> predicate) {
        this.predicate = predicate;
    }

    @Override
    public void apply(Record inputRecord, OutputCollector collector) {
        var isAcceptable = predicate.apply(inputRecord);
        if (isAcceptable) {
            collector.collect(inputRecord);
        }
    }
}
