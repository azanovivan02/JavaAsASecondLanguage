package io.github.javaasasecondlanguage.homework04.nodes;

import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.RoutingCollector;

public interface ProcNode {

    RoutingCollector getCollector();

    void push(Record inputRecord, int gateNumber);
}
