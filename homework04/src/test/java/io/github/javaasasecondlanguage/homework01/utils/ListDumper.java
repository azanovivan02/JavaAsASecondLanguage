package io.github.javaasasecondlanguage.homework01.utils;

import io.github.javaasasecondlanguage.homework01.OutputCollector;
import io.github.javaasasecondlanguage.homework01.Record;
import io.github.javaasasecondlanguage.homework01.ops.Operator;

import java.util.ArrayList;
import java.util.List;

public class ListDumper implements Operator.Mapper {

    private final List<Record> records = new ArrayList<>();

    public List<Record> getRecords() {
        return records;
    }

    @Override
    public void apply(Record inputRecord, OutputCollector collector) {
        records.add(inputRecord);
        collector.collect(inputRecord);
    }
}
