package io.github.javaasasecondlanguage.homework01.ops.mappers;

import io.github.javaasasecondlanguage.homework01.Row;
import io.github.javaasasecondlanguage.homework01.nodes.OutputCollector;
import io.github.javaasasecondlanguage.homework01.ops.Operator.Mapper;

public class RetainColumnsMapper implements Mapper {

    private final String[] retainedColumns;

    public RetainColumnsMapper(String...retainedColumns) {
        this.retainedColumns = retainedColumns;
    }

    @Override
    public void apply(Row inputRow, OutputCollector collector) {
        Row newRow = inputRow.copyColumns(retainedColumns);
        collector.collect(newRow);
    }
}
