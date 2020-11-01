package io.github.javaasasecondlanguage.homework01.nodes;

import io.github.javaasasecondlanguage.homework01.Record;
import io.github.javaasasecondlanguage.homework01.RoutingCollector;

public interface ProcNode {

    RoutingCollector getCollector();

    void push(Record inputRecord, int gateNumber);
}
