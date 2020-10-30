package io.github.javaasasecondlanguage.homework01;

import io.github.javaasasecondlanguage.homework01.cases.BaseCase;
import io.github.javaasasecondlanguage.homework01.graphs.TfIdf;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.github.javaasasecondlanguage.homework01.ui.GraphVisualizer.visualizeGraph;

/**
 * Only launched manually - remove "Disabled" first
 */
public class VisualizationTest {

    @Disabled
    @Test
    void launch() throws InterruptedException {
//        BaseCase baseCase = new BaseCase();
//        var compGraph = baseCase.createGraph();
        var compGraph = TfIdf.createGraph();
        visualizeGraph(compGraph);
        Thread.sleep(Long.MAX_VALUE);
    }
}
