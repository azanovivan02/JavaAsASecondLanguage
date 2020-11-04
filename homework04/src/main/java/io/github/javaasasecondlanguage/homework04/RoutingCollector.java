package io.github.javaasasecondlanguage.homework04;

import io.github.javaasasecondlanguage.homework04.nodes.ProcNode;

import java.util.ArrayList;
import java.util.List;

public class RoutingCollector implements Collector {

    private final List<Connection> connections = new ArrayList<>();

    public final List<Connection> getConnections() {
        return connections;
    }

    public final void addConnection(ProcNode node, int gate) {
        Connection connection = new Connection(node, gate);
        connections.add(connection);
    }

    @Override
    public void collect(Record outputRecord) {
        for (Connection info : connections) {
            var node = info.getNode();
            var gateNumber = info.getGate();
            node.push(outputRecord, gateNumber);
        }
    }
}
