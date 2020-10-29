package io.github.javaasasecondlanguage.homework01.ops.reducers;

import io.github.javaasasecondlanguage.homework01.OutputCollector;
import io.github.javaasasecondlanguage.homework01.Row;
import io.github.javaasasecondlanguage.homework01.ops.OpUtils;
import io.github.javaasasecondlanguage.homework01.ops.Operator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Collections.sort;

public class Sorter implements Operator.Reducer {

    private final List<Row> accumulatedRows = new ArrayList<>();

    private final Order order;

    private List<String> keyColumns;
    private Comparator<Row> rowComparator;

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

        Comparator<Row> comparator = (o1, o2) -> OpUtils.compareRows(o1, o2, keyColumns);
        if (order == Order.DESCENDING) {
            this.rowComparator = comparator.reversed();
        } else {
            this.rowComparator = comparator;
        }
    }

    @Override
    public void apply(Row inputRow, OutputCollector collector) {
        if (!inputRow.isTerminal()) {
            accumulatedRows.add(inputRow);
            return;
        }

        sort(accumulatedRows, rowComparator);
        for (Row row : accumulatedRows) {
            collector.collect(row);
        }
        collector.collect(Row.terminalRow());
    }

    public enum Order {
        ASCENDING,
        DESCENDING
    }
}
