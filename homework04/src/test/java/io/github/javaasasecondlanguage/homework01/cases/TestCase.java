package io.github.javaasasecondlanguage.homework01.cases;

import io.github.javaasasecondlanguage.homework01.CompGraph;
import io.github.javaasasecondlanguage.homework01.Record;

import java.util.List;

public interface TestCase {
    void launch();
    CompGraph createGraph();
    List<List<Record>> createInputs();
}
