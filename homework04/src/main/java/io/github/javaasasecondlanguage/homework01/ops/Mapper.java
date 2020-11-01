package io.github.javaasasecondlanguage.homework01.ops;

import io.github.javaasasecondlanguage.homework01.OutputCollector;
import io.github.javaasasecondlanguage.homework01.Record;

public interface Mapper {
    void apply(Record inputRecord, OutputCollector collector);
}
