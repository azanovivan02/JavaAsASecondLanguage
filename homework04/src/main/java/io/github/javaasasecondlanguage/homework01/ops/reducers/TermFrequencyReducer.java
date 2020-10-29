package io.github.javaasasecondlanguage.homework01.ops.reducers;

import io.github.javaasasecondlanguage.homework01.OutputCollector;
import io.github.javaasasecondlanguage.homework01.Row;
import io.github.javaasasecondlanguage.homework01.ops.OpUtils;
import io.github.javaasasecondlanguage.homework01.ops.Operator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class TermFrequencyReducer implements Operator.Reducer {

    private String termColumn;
    private String outputColumn;
    private List<String> keyColumns;

    private Row currentRow = null;
    private Map<String, Integer> wordCounts = new HashMap<>();

    public TermFrequencyReducer(String termColumn, String outputColumn) {
        this.termColumn = termColumn;
        this.outputColumn = outputColumn;
    }

    @Override
    public List<String> getKeyColumns() {
        return keyColumns;
    }

    @Override
    public void setKeyColumns(List<String> keyColumns) {
        this.keyColumns = keyColumns;
    }

    @Override
    public void apply(Row inputRow, OutputCollector collector) {
        if (inputRow.isTerminal()) {
            outputTfRows(collector);
            currentRow = null;
            wordCounts.clear();
            collector.collect(inputRow);
            return;
        }

        if (!OpUtils.equalByColumns(inputRow, currentRow, keyColumns)) {
            outputTfRows(collector);
            wordCounts.clear();
            currentRow = inputRow;
        }

        var currentWord = inputRow.getString(termColumn);
        var currentCount = wordCounts.getOrDefault(currentWord, 0);
        wordCounts.put(currentWord, currentCount + 1);
    }

    private void outputTfRows(OutputCollector collector) {
        if (currentRow == null) {
            return;
        }

        var totalCount = getTotalCount();
        var sortedWordCounts = new TreeMap<String, Integer>(wordCounts);

        for (var entry : sortedWordCounts.entrySet()) {
            String term = entry.getKey();
            Integer termCount = entry.getValue();
            float frequency = ((float) termCount) / totalCount;
            Row newRow = currentRow
                    .copyColumns(keyColumns)
                    .set(termColumn, term)
                    .set(outputColumn, frequency);
            collector.collect(newRow);
        }

    }

    private int getTotalCount() {
        int totalCount = 0;
        for (Integer count : wordCounts.values()) {
            totalCount += count;
        }
        return totalCount;
    }
}
