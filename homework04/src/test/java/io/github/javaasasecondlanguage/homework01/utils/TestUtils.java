package io.github.javaasasecondlanguage.homework01.utils;

import io.github.javaasasecondlanguage.homework01.Record;
import io.github.javaasasecondlanguage.homework01.nodes.ProcNode;
import io.github.javaasasecondlanguage.homework01.ops.Mapper;
import io.github.javaasasecondlanguage.homework01.ops.Reducer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonMap;

public class TestUtils {

    public static List<Record> convertToRecords(String columnName, Collection<?> inputValues) {
        return inputValues
                .stream()
                .map(value -> new Record(singletonMap(columnName, value)))
                .collect(Collectors.toList());
    }

    public static List<Record> convertToRecords(String[] schema, Object[]... inputTuples) {
        var outputRecords = new ArrayList<Record>();
        for (var tuple : inputTuples) {
            if (schema.length != tuple.length) {
                throw new IllegalArgumentException("Different number of column names and actual columns");
            }

            var record = new Record(new HashMap<>());
            for (int columnIndex = 0; columnIndex < schema.length; columnIndex++) {
                String columnName = schema[columnIndex];
                Object columnValue = tuple[columnIndex];
                record.set(columnName, columnValue);
            }
            outputRecords.add(record);
        }

        return outputRecords;
    }

    public static void pushAllRecordsThenTerminal(ProcNode node, List<Record> records) {
        for (var record : records) {
            node.push(record, 0);
        }
        node.push(Record.terminalRecord(), 0);
    }

    public static List<Record> applyToAllRecordsThenTerminal(Reducer operator, List<Record> records) {
//        var collector = new ListOutputCollector();
//
//        for (var record : records) {
//            operator.apply(record, collector);
//        }
//        operator.apply(Record.terminalRecord(), collector);
//
//        return collector.getCollectedRecords();
        return emptyList();
    }

    public static List<Record> applyToAllRecords(Mapper operator, List<Record> records) {
        var collector = new ListOutputCollector();

        for (var record : records) {
            operator.apply(record, collector);
        }

        return collector.getCollectedRecords();
    }

    public static List<Record> applyReducerToAllGroups(Reducer reducer, List<RecordGroup> inputGroups) {
        var collector = new ListOutputCollector();
        for (RecordGroup group : inputGroups) {
            for (Record record : group.getRecords()) {
                reducer.apply(record, collector, group.getGroupByKeys());
            }
            reducer.signalGroupWasFinished(collector, group.getGroupByKeys());
        }
        return collector.getCollectedRecords();
    }
}
