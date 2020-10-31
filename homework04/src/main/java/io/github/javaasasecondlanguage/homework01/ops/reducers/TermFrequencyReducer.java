package io.github.javaasasecondlanguage.homework01.ops.reducers;

import io.github.javaasasecondlanguage.homework01.OutputCollector;
import io.github.javaasasecondlanguage.homework01.Record;
import io.github.javaasasecondlanguage.homework01.ops.Operator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TermFrequencyReducer implements Operator.Reducer {

    private final String termColumn;
    private final String outputColumn;
    private final Map<String, Integer> wordCounts = new HashMap<>();

    public TermFrequencyReducer(String termColumn, String outputColumn) {
        this.termColumn = termColumn;
        this.outputColumn = outputColumn;
    }

    @Override
    public void apply(Record inputRecord, OutputCollector collector, Map<String, Object> groupByValues) {
        var currentWord = inputRecord.getString(termColumn);
        var currentCount = wordCounts.getOrDefault(currentWord, 0);
        wordCounts.put(currentWord, currentCount + 1);
    }

    @Override
    public void signalGroupWasFinished(OutputCollector collector, Map<String, Object> groupByValues) {
        var totalCount = getTotalCount();
        var sortedWordCounts = new TreeMap<>(wordCounts);
        for (var entry : sortedWordCounts.entrySet()) {
            String term = entry.getKey();
            Integer termCount = entry.getValue();
            float frequency = ((float) termCount) / totalCount;
            Record outputRecord = new Record(groupByValues);
            outputRecord = outputRecord
                    .set(termColumn, term)
                    .set(outputColumn, frequency);

            collector.collect(outputRecord);
        }

        wordCounts.clear();
    }

    private int getTotalCount() {
        int totalCount = 0;
        for (Integer count : wordCounts.values()) {
            totalCount += count;
        }
        return totalCount;
    }
}
