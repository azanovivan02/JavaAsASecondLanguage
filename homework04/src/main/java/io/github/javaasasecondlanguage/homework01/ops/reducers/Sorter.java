package io.github.javaasasecondlanguage.homework01.ops.reducers;

import io.github.javaasasecondlanguage.homework01.OutputCollector;
import io.github.javaasasecondlanguage.homework01.Record;
import io.github.javaasasecondlanguage.homework01.ops.OpUtils;
import io.github.javaasasecondlanguage.homework01.ops.Operator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Collections.sort;

public class Sorter implements Operator.Reducer {

    private final List<Record> accumulatedRecords = new ArrayList<>();

    private final Order order;

    private List<String> keyColumns;
    private Comparator<Record> recordComparator;

    public Sorter(Order order) {
        this.order = order;
    }

    @Override
    public List<String> getKeyColumns() {
        return keyColumns;
    }

    @Override
    public void setKeyColumns(List<String> keyColumns) {
        this.keyColumns = keyColumns;

        Comparator<Record> comparator = (o1, o2) -> OpUtils.compareRecords(o1, o2, keyColumns);
        if (order == Order.DESCENDING) {
            this.recordComparator = comparator.reversed();
        } else {
            this.recordComparator = comparator;
        }
    }

    @Override
    public void apply(Record inputRecord, OutputCollector collector) {
        if (!inputRecord.isTerminal()) {
            accumulatedRecords.add(inputRecord);
            return;
        }

        sort(accumulatedRecords, recordComparator);
        for (Record record : accumulatedRecords) {
            collector.collect(record);
        }
        collector.collect(Record.terminalRecord());
    }

    public enum Order {
        ASCENDING,
        DESCENDING
    }
}
