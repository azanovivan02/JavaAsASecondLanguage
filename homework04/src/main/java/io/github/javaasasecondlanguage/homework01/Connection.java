package io.github.javaasasecondlanguage.homework01;

public class Connection {
    private final CompNode node;
    private final int gate;

    public Connection(CompNode node, int gate) {
        this.node = node;
        this.gate = gate;
    }

    public CompNode getNode() {
        return node;
    }

    public int getGate() {
        return gate;
    }
}
