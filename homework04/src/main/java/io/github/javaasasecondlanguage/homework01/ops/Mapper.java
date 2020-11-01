package io.github.javaasasecondlanguage.homework01.ops;

import io.github.javaasasecondlanguage.homework01.Collector;
import io.github.javaasasecondlanguage.homework01.Record;

public interface Mapper {
    void apply(Record inputRecord, Collector collector);
}