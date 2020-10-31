package io.github.javaasasecondlanguage.homework01.utils;

import io.github.javaasasecondlanguage.homework01.Record;
import io.github.javaasasecondlanguage.homework01.OutputCollector;

import java.util.ArrayList;
import java.util.List;

public class ListOutputCollector implements OutputCollector {

    private final List<Record> collectedRecords = new ArrayList<>();

    public List<Record> getCollectedRecords() {
        return collectedRecords;
    }

    @Override
    public void collect(Record outputRecord) {
        collectedRecords.add(outputRecord);
    }
}
