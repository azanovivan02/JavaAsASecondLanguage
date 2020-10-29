package io.github.javaasasecondlanguage.homework01;

import java.util.ArrayList;
import java.util.List;

public class CompGraph {
    private final List<CompNode> inputNodes;
    private final List<CompNode> outputNodes;

    public CompGraph(List<CompNode> inputNodes, List<CompNode> outputNodes) {
        if (inputNodes == null) {
            inputNodes = List.of();
        }
        if (outputNodes == null) {
            outputNodes = List.of();
        }

        this.inputNodes = new ArrayList<>(inputNodes);
        this.outputNodes = new ArrayList<>(outputNodes);
    }

    public List<CompNode> getInputNodes() {
        return inputNodes;
    }

    public List<CompNode> getOutputNodes() {
        return outputNodes;
    }
}
