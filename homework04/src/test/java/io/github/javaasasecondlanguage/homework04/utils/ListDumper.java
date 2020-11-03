package io.github.javaasasecondlanguage.homework04.utils;

import io.github.javaasasecondlanguage.homework04.Collector;
import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.ops.Mapper;

import java.util.ArrayList;
import java.util.List;

public class ListDumper implements Mapper {

    private final List<Record> records = new ArrayList<>();

    public List<Record> getRecords() {
        return records;
    }

    @Override
    public void apply(Record inputRecord, Collector collector) {
        records.add(inputRecord);
        collector.collect(inputRecord);
    }
}
