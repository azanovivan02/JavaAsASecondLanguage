package io.github.javaasasecondlanguage.homework01;

import io.github.javaasasecondlanguage.homework01.cases.TfIdfCase;
import io.github.javaasasecondlanguage.homework01.nodes.CompNode;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.github.javaasasecondlanguage.homework01.ui.GraphVisualizer.visualizeGraph;

public class VisualizationTest {

    @Disabled
    @Test
    void launch() throws InterruptedException {
        TfIdfCase testCase = new TfIdfCase();
        List<CompNode> compGraph = testCase.createGraph();
        visualizeGraph(compGraph);
        Thread.sleep(Long.MAX_VALUE);
    }
}
