package io.github.javaasasecondlanguage.homework01.utils;

import io.github.javaasasecondlanguage.homework01.Record;
import io.github.javaasasecondlanguage.homework01.Collector;

import java.util.ArrayList;
import java.util.List;

public class ListCollector implements Collector {

    private final List<Record> collectedRecords = new ArrayList<>();

    public List<Record> getCollectedRecords() {
        return collectedRecords;
    }

    @Override
    public void collect(Record outputRecord) {
        collectedRecords.add(outputRecord);
    }
}
