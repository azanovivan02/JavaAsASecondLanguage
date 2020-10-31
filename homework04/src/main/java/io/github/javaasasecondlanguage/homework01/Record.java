package io.github.javaasasecondlanguage.homework01;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static io.github.javaasasecondlanguage.homework01.Utils.smartEquals;
import static java.lang.String.format;

/**
 * A group of key-value pairs. Represents a single unit of data
 * <p>
 * Keys (also known as columns) can be represented only as String objects.
 * Values should be only Strings and Numbers.
 */
public class Record {

    private final Map<String, Object> data;

    public Record(Map<String, Object> data) {
        if (data != null) {
            this.data = new LinkedHashMap<>(data);
        } else {
            this.data = null;
        }
    }

    public Map<String, Object> getData() {
        return data;
    }

    public Object get(String column) {
        return data.get(column);
    }

    public String getString(String column) {
        return data.get(column).toString();
    }

    public Double getDoubleOrNull(String column) {
        String stringValue = this.get(column).toString();
        try {
            return Double.parseDouble(stringValue);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Map<String, Object> getAll(Collection<String> columns) {
        var partOfData = new LinkedHashMap<>(data);
        partOfData
                .keySet()
                .retainAll(columns);
        return partOfData;
    }

    public Record set(String column, Object value) {
        this.data.put(column, value);
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

    public Record copyColumns(Collection<String> columns) {
        var newValues = new LinkedHashMap<String, Object>();
        for (String column : columns) {
            newValues.put(
                    column,
                    data.get(column)
            );
        }
        return new Record(newValues);
    }

    public Record copyColumnsExcept(Collection<String> excludedColumns) {
        var newValues = new LinkedHashMap<>(data);
        newValues
                .keySet()
                .removeAll(excludedColumns);
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

        Map<String, Object> leftValues = leftRecord.getData();
        Map<String, Object> rightValues = rightRecord.getData();

        return smartEquals(leftValues, rightValues);
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
