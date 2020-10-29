package io.github.javaasasecondlanguage.homework01;


import io.github.javaasasecondlanguage.homework01.cases.TestCase;
import io.github.javaasasecondlanguage.homework01.cases.TfIdfCase;
import io.github.javaasasecondlanguage.homework01.nodes.CompNode;

import java.util.Arrays;
import java.util.List;

import static io.github.javaasasecondlanguage.homework01.ui.GraphVisualizer.visualizeGraph;

public class Main {

    public static void main(String[] args) {
        List<TestCase> testCases = Arrays.asList(
//                new BaseCase()
//                new TableCase()
//                new JoinCase()
                new TfIdfCase()
//                new TermFrequencyCase()
        );
        for (TestCase testCase : testCases) {
            String caseName = testCase.getClass().getSimpleName();

            System.out.printf("\n== %s ========\n\n", caseName);
            testCase.launch();

            List<CompNode> compGraph = testCase.createGraph();
            visualizeGraph(compGraph);
        }
    }

}
