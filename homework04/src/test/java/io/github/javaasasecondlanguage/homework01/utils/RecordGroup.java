package io.github.javaasasecondlanguage.homework01.utils;

import io.github.javaasasecondlanguage.homework01.Record;

import java.util.List;
import java.util.Map;

public class RecordGroup {

    public static RecordGroup group(Map<String, Object> groupByKeys, List<Record> records) {
        return new RecordGroup(groupByKeys, records);
    }

    private final  Map<String, Object> groupByKeys;
    private final List<Record> records;

    public RecordGroup(Map<String, Object> groupByKeys, List<Record> records) {
        this.groupByKeys = groupByKeys;
        this.records = records;
    }

    public Map<String, Object> getGroupByKeys() {
        return groupByKeys;
    }

    public List<Record> getRecords() {
        return records;
    }
}
