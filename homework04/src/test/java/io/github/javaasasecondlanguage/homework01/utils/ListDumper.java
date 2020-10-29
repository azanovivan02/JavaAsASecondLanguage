package io.github.javaasasecondlanguage.homework01.utils;

import io.github.javaasasecondlanguage.homework01.OutputCollector;
import io.github.javaasasecondlanguage.homework01.Row;
import io.github.javaasasecondlanguage.homework01.ops.Operator;

import java.util.ArrayList;
import java.util.List;

public class ListDumper implements Operator.Mapper {

    private final List<Row> rows = new ArrayList<>();

    public List<Row> getRows() {
        return rows;
    }

    @Override
    public void apply(Row inputRow, OutputCollector collector) {
        rows.add(inputRow);
        collector.collect(inputRow);
    }
}
