package io.github.javaasasecondlanguage.homework01.nodes;

import io.github.javaasasecondlanguage.homework01.Record;
import io.github.javaasasecondlanguage.homework01.ops.Operator;

import java.util.LinkedList;
import java.util.List;

import static io.github.javaasasecondlanguage.homework01.Utils.compareRecords;

public class JoinerNode extends ProcNode {

    public final List<String> keyColumns;

    public final LinkedList<Record> leftRecords = new LinkedList<>();
    public final LinkedList<Record> rightRecords = new LinkedList<>();

    public JoinerNode(List<String> keyColumns) {
        this.keyColumns = keyColumns;
    }

    @Override
    public Operator getOperator() {
        return null;
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
                var comparisonResult = compareRecords(leftRecord, rightRecord, keyColumns);
                if (comparisonResult == 0) {
                    Record joinedRecord = leftRecord.copy().setAll(rightRecord.getData());
                    collect(joinedRecord);
                }
            }
        }
        collect(Record.terminalRecord());
    }
}
