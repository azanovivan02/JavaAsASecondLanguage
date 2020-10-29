package io.github.javaasasecondlanguage.homework01;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class Row {

    private Map<String, Object> values;

    public Row(Map<String, Object> values) {
        this.values = values;
    }

    public Map<String, Object> getValues() {
        return values;
    }

    public Object get(String columnName) {
        return values.get(columnName);
    }

    public String getString(String columnName) {
        return get(columnName).toString();
    }

    public double getDouble(String columnName) {
        String stringValue = getString(columnName);
        return Double.parseDouble(stringValue);
    }

    public Comparable getComparable(String columnName) {
        return (Comparable) get(columnName);
    }

    public Row set(String columnName, Object value) {
        this.values.put(columnName, value);
        return this;
    }

    public Row setAll(Map<String, Object> inputEntries) {
        this.values.putAll(inputEntries);
        return this;
    }

    public Row copy() {
        return new Row(
                new LinkedHashMap<>(values)
        );
    }

    public Row copyColumns(List<String> columns) {
        LinkedHashMap<String, Object> newValues = new LinkedHashMap<>();
        for (String column : columns) {
            newValues.put(
                    column,
                    values.get(column)
            );
        }
        return new Row(newValues);
    }

    public Row copyColumnsExcept(String... excludedColumns) {
        LinkedHashMap<String, Object> newValues = new LinkedHashMap<>(values);
        newValues
                .keySet()
                .removeAll(Arrays.asList(excludedColumns));
        return new Row(newValues);
    }

    public boolean isTerminal() {
        return values == null;
    }

    public static Row terminalRow() {
        return new Row(null);
    }

    @Override
    public String toString() {
        String valuesString = values
                .entrySet()
                .stream()
                .map(e -> format("%s: %s", e.getKey(), getFormattedValue(e)))
                .collect(Collectors.joining(", "));
        return format("Row {%s}", valuesString);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Row leftRow = this;
        Row rightRow = (Row) o;

        return Utils.rowsEqual(leftRow, rightRow, 0.001);
    }

    private static String getFormattedValue(Map.Entry<String, Object> e) {
        Object value = e.getValue();
        if (value instanceof Double) {
            return format("%1$,.3f", value);
        } else {
            return value.toString();
        }
    }

}
