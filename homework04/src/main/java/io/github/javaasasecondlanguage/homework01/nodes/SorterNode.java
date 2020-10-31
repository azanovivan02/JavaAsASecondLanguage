package io.github.javaasasecondlanguage.homework01.nodes;

import io.github.javaasasecondlanguage.homework01.Record;
import io.github.javaasasecondlanguage.homework01.ops.OpUtils;
import io.github.javaasasecondlanguage.homework01.ops.Operator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Collections.sort;

public class SorterNode extends ProcNode {

    private final List<Record> accumulatedRecords = new ArrayList<>();

    private final Order order;
    private final List<String> keyColumns;
    private final Comparator<Record> recordComparator;

    public SorterNode(List<String> keyColumns, Order order) {
        this.order = order;
        this.keyColumns = keyColumns;

        Comparator<Record> comparator = (o1, o2) -> OpUtils.compareRecords(o1, o2, keyColumns);
        if (order == Order.DESCENDING) {
            this.recordComparator = comparator.reversed();
        } else {
            this.recordComparator = comparator;
        }
    }

    @Override
    public Operator getOperator() {
        return new Operator() {};
    }

    @Override
    public void push(Record inputRecord, int gateNumber) {
        if (!inputRecord.isTerminal()) {
            accumulatedRecords.add(inputRecord);
            return;
        }

        sort(accumulatedRecords, recordComparator);
        for (Record record : accumulatedRecords) {
            collect(record);
        }
        collect(Record.terminalRecord());
    }

    public enum Order {
        ASCENDING,
        DESCENDING
    }
}
