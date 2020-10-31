package io.github.javaasasecondlanguage.homework01;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * A group of key-value pairs. Represents a single unit of data
 *
 * Keys (also known as columns) can be represented only as String objects.
 * Values should be only Strings and Numbers.
 */
public class Record {

    private Map<String, Object> data;

    public Record(Map<String, Object> data) {
        this.data = data;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public String get(String columnName) {
        return data.get(columnName).toString();
    }

    public Double getDoubleOrNull(String columnName) {
        String stringValue = this.get(columnName);
        try {
            return Double.parseDouble(stringValue);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Record set(String columnName, Object value) {
        this.data.put(columnName, value);
        return this;
    }

    public Record setAll(Map<String, Object> inputEntries) {
        this.data.putAll(inputEntries);
        return this;
    }

    public Record copy() {
        return new Record(
                new LinkedHashMap<>(data)
        );
    }

    public Record copyColumns(List<String> columns) {
        var newValues = new LinkedHashMap<String, Object>();
        for (String column : columns) {
            newValues.put(
                    column,
                    data.get(column)
            );
        }
        return new Record(newValues);
    }

    public Record copyColumnsExcept(String... excludedColumns) {
        var newValues = new LinkedHashMap<>(data);
        newValues
                .keySet()
                .removeAll(Arrays.asList(excludedColumns));
        return new Record(newValues);
    }

    public boolean isTerminal() {
        return data == null;
    }

    public static Record terminalRecord() {
        return new Record(null);
    }

    @Override
    public String toString() {
        String valuesString = data
                .entrySet()
                .stream()
                .map(e -> format("%s: %s", e.getKey(), getFormattedValue(e)))
                .collect(Collectors.joining(", "));
        return format("Record {%s}", valuesString);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Record leftRecord = this;
        Record rightRecord = (Record) o;

        return Utils.recordsEqual(leftRecord, rightRecord, 0.001);
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
