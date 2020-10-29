package io.github.javaasasecondlanguage.homework01.utils;

import io.github.javaasasecondlanguage.homework01.Row;
import io.github.javaasasecondlanguage.homework01.nodes.OutputCollector;

import java.util.ArrayList;
import java.util.List;

public class ListOutputCollector implements OutputCollector {

    private final List<Row> collectedRows = new ArrayList<>();

    public List<Row> getCollectedRows() {
        return collectedRows;
    }

    @Override
    public void collect(Row outputRow) {
        collectedRows.add(outputRow);
    }
}
