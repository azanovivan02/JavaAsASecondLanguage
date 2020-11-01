package io.github.javaasasecondlanguage.homework01.nodes;

import io.github.javaasasecondlanguage.homework01.Connection;
import io.github.javaasasecondlanguage.homework01.Record;

import java.util.ArrayList;
import java.util.List;

public abstract class ProcNode {

    private final List<Connection> connections;

    public ProcNode() {
        this.connections = new ArrayList<>();
    }

    public final List<Connection> getConnections() {
        return connections;
    }

    public final void addConnection(ProcNode node, int gate) {
        Connection connection = new Connection(node, gate);
        connections.add(connection);
    }

    public final void collect(Record record) {
        for (Connection info : connections) {
            var node = info.getNode();
            var gateNumber = info.getGate();
            node.push(record, gateNumber);
        }
    }

    public abstract void push(Record inputRecord, int gateNumber);
}
