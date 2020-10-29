package io.github.javaasasecondlanguage.homework01.utils;

import io.github.javaasasecondlanguage.homework01.Row;
import io.github.javaasasecondlanguage.homework01.nodes.CompNode;
import io.github.javaasasecondlanguage.homework01.ops.Operator;
import io.github.javaasasecondlanguage.homework01.ops.Operator.Mapper;
import io.github.javaasasecondlanguage.homework01.ops.Operator.Reducer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.singletonMap;

public class TestUtils {

    public static List<Row> convertToRows(String columnName, Collection<?> inputValues) {
        return inputValues
                .stream()
                .map(value -> new Row(singletonMap(columnName, value)))
                .collect(Collectors.toList());
    }

    public static List<Row> convertToRows(String[] schema, Object[]... inputTuples) {
        ArrayList<Row> outputRows = new ArrayList<>();
        for (Object[] tuple : inputTuples) {
            Row row = new Row(new HashMap<>());
            for (int columnIndex = 0; columnIndex < schema.length; columnIndex++) {
                String columnName = schema[columnIndex];
                Object columnValue = tuple[columnIndex];
                row.set(columnName, columnValue);
            }
            outputRows.add(row);
        }

        return outputRows;
    }

    public static void pushAllRowsThenTerminal(CompNode node, List<Row> rows) {
        for (Row row : rows) {
            node.pushIntoZero(row);
        }
        node.pushIntoZero(Row.terminalRow());
    }

    public static List<Row> applyToAllRowsThenTerminal(Reducer operator, List<Row> rows) {
        ListOutputCollector collector = new ListOutputCollector();

        for (Row row : rows) {
            operator.apply(row, collector);
        }
        operator.apply(Row.terminalRow(), collector);

        return collector.getCollectedRows();
    }

    public static List<Row> applyToAllRows(Mapper operator, List<Row> rows) {
        ListOutputCollector collector = new ListOutputCollector();

        for (Row row : rows) {
            operator.apply(row, collector);
        }

        return collector.getCollectedRows();
    }
}
