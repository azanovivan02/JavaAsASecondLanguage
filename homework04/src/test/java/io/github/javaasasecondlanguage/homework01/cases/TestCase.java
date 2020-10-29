package io.github.javaasasecondlanguage.homework01.cases;

import io.github.javaasasecondlanguage.homework01.Row;
import io.github.javaasasecondlanguage.homework01.nodes.CompNode;

import java.util.List;

public interface TestCase {
    void launch();
    List<CompNode> createGraph();
    List<List<Row>> createInputs();
}
