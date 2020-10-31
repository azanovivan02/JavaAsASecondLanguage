package io.github.javaasasecondlanguage.homework01.ops;

import io.github.javaasasecondlanguage.homework01.OutputCollector;
import io.github.javaasasecondlanguage.homework01.Record;

import java.util.LinkedList;
import java.util.List;

import static io.github.javaasasecondlanguage.homework01.Utils.compareRecords;

public class InnerJoin implements Operator.Joiner {

    LinkedList<Record> leftRecords = new LinkedList<>();
    LinkedList<Record> rightRecords = new LinkedList<>();

    private List<String> keyColumns;

    @Override
    public List<String> getKeyColumns() {
        return keyColumns;
    }

    @Override
    public void setKeyColumns(List<String> keyColumns) {
        this.keyColumns = keyColumns;
    }

    @Override
    public void applyLeft(Record inputRecord, OutputCollector collector) {
        leftRecords.add(inputRecord);
        outputJoinedRecordsIfPossible(collector);
    }

    @Override
    public void applyRight(Record inputRecord, OutputCollector collector) {
        rightRecords.add(inputRecord);
        outputJoinedRecordsIfPossible(collector);
    }

    private void outputJoinedRecordsIfPossible(OutputCollector collector) {
        if (leftRecords.isEmpty() || !leftRecords.getLast().isTerminal()) {
            return;
        }
        if (rightRecords.isEmpty() || !rightRecords.getLast().isTerminal()) {
            return;
        }

        leftRecords.removeLast();
        rightRecords.removeLast();

        for (var rightRecord : rightRecords) {
            for (var leftRecord : leftRecords) {
                var comparisonResult = compareRecords(leftRecord, rightRecord, keyColumns);
                if (comparisonResult == 0) {
                    Record joinedRecord = leftRecord.copy().setAll(rightRecord.getData());
                    collector.collect(joinedRecord);
                }
            }
        }
        collector.collect(Record.terminalRecord());
    }

}
