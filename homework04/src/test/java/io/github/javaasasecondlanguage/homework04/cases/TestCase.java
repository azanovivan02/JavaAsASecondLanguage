package io.github.javaasasecondlanguage.homework04.cases;

import io.github.javaasasecondlanguage.homework04.ProcGraph;
import io.github.javaasasecondlanguage.homework04.Record;

import java.util.List;

public interface TestCase {
    void launch();
    ProcGraph createGraph();
    List<List<Record>> createInputs();
}
