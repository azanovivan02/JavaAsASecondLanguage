package io.github.javaasasecondlanguage.homework01.ops.mappers;

import io.github.javaasasecondlanguage.homework01.OutputCollector;
import io.github.javaasasecondlanguage.homework01.Row;
import io.github.javaasasecondlanguage.homework01.ops.Operator.Mapper;

import java.util.List;

public class RetainColumnsMapper implements Mapper {

    private final List<String> retainedColumns;

    public RetainColumnsMapper(List<String> retainedColumns) {
        this.retainedColumns = retainedColumns;
    }

    @Override
    public void apply(Row inputRow, OutputCollector collector) {
        Row newRow = inputRow.copyColumns(retainedColumns);
        collector.collect(newRow);
    }
}
