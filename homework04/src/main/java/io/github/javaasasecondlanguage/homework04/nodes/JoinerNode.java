package io.github.javaasasecondlanguage.homework04.nodes;

import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.RoutingCollector;

import java.util.LinkedList;
import java.util.List;

import static io.github.javaasasecondlanguage.homework04.Utils.compareRecordsByKeys;

public class JoinerNode implements ProcNode {

    private final RoutingCollector collector = new RoutingCollector();

    private final LinkedList<Record> leftRecords = new LinkedList<>();
    private final LinkedList<Record> rightRecords = new LinkedList<>();

    private final List<String> keyColumns;

    public JoinerNode(List<String> keyColumns) {
        this.keyColumns = keyColumns;
    }

    @Override
    public RoutingCollector getCollector() {
        return collector;
    }

    @Override
    public void push(Record inputRecord, int gateNumber) {
        switch (gateNumber) {
            case 0: {
                leftRecords.add(inputRecord);
                break;
            }
            case 1: {
                rightRecords.add(inputRecord);
                break;
            }
            default: {
                throw new IllegalArgumentException("Gate does not exist: " + gateNumber);
            }
        }

        outputJoinedRecordsIfPossible();
    }

    public void outputJoinedRecordsIfPossible() {
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
                var comparisonResult = compareRecordsByKeys(leftRecord, rightRecord, keyColumns);
                if (comparisonResult == 0) {
                    Record joinedRecord = leftRecord.copy().setAll(rightRecord.getData());
                    collector.collect(joinedRecord);
                }
            }
        }
        collector.collect(Record.terminalRecord());
    }
}
