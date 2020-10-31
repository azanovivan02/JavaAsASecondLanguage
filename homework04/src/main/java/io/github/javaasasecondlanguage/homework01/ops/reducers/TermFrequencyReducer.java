package io.github.javaasasecondlanguage.homework01.ops.reducers;

import io.github.javaasasecondlanguage.homework01.OutputCollector;
import io.github.javaasasecondlanguage.homework01.Record;
import io.github.javaasasecondlanguage.homework01.ops.OpUtils;
import io.github.javaasasecondlanguage.homework01.ops.Operator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TermFrequencyReducer implements Operator.Reducer {

    private String termColumn;
    private String outputColumn;
    private List<String> keyColumns;

    private Record currentRecord = null;
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
    public void apply(Record inputRecord, OutputCollector collector) {
        if (inputRecord.isTerminal()) {
            outputTfRecords(collector);
            currentRecord = null;
            wordCounts.clear();
            collector.collect(inputRecord);
            return;
        }

        if (!OpUtils.equalByColumns(inputRecord, currentRecord, keyColumns)) {
            outputTfRecords(collector);
            wordCounts.clear();
            currentRecord = inputRecord;
        }

        var currentWord = inputRecord.getString(termColumn);
        var currentCount = wordCounts.getOrDefault(currentWord, 0);
        wordCounts.put(currentWord, currentCount + 1);
    }

    private void outputTfRecords(OutputCollector collector) {
        if (currentRecord == null) {
            return;
        }

        var totalCount = getTotalCount();
        var sortedWordCounts = new TreeMap<String, Integer>(wordCounts);

        for (var entry : sortedWordCounts.entrySet()) {
            String term = entry.getKey();
            Integer termCount = entry.getValue();
            float frequency = ((float) termCount) / totalCount;
            Record newRecord = currentRecord
                    .copyColumns(keyColumns)
                    .set(termColumn, term)
                    .set(outputColumn, frequency);
            collector.collect(newRecord);
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
