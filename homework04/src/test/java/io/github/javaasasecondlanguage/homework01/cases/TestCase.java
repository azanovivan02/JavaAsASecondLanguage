package io.github.javaasasecondlanguage.homework01.cases;

import io.github.javaasasecondlanguage.homework01.ProcGraph;
import io.github.javaasasecondlanguage.homework01.Record;

import java.util.List;

public interface TestCase {
    void launch();
    ProcGraph createGraph();
    List<List<Record>> createInputs();
}
