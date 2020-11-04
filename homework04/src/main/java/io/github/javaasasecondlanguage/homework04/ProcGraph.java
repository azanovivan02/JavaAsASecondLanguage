package io.github.javaasasecondlanguage.homework04;

import io.github.javaasasecondlanguage.homework04.nodes.ProcNode;

import java.util.ArrayList;
import java.util.List;

public class ProcGraph {
    private final List<ProcNode> inputNodes;
    private final List<ProcNode> outputNodes;

    public ProcGraph(List<ProcNode> inputNodes, List<ProcNode> outputNodes) {
        if (inputNodes == null) {
            inputNodes = List.of();
        }
        if (outputNodes == null) {
            outputNodes = List.of();
        }

        this.inputNodes = new ArrayList<>(inputNodes);
        this.outputNodes = new ArrayList<>(outputNodes);
    }

    public List<ProcNode> getInputNodes() {
        return inputNodes;
    }

    public List<ProcNode> getOutputNodes() {
        return outputNodes;
    }
}
