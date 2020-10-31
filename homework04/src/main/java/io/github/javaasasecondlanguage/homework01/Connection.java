package io.github.javaasasecondlanguage.homework01;

public class Connection {
    private final ProcNode node;
    private final int gate;

    public Connection(ProcNode node, int gate) {
        this.node = node;
        this.gate = gate;
    }

    public ProcNode getNode() {
        return node;
    }

    public int getGate() {
        return gate;
    }
}
