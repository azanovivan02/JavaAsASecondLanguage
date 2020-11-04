package io.github.javaasasecondlanguage.homework04;

import io.github.javaasasecondlanguage.homework04.nodes.JoinerNode;
import io.github.javaasasecondlanguage.homework04.nodes.MapperNode;
import io.github.javaasasecondlanguage.homework04.nodes.ProcNode;
import io.github.javaasasecondlanguage.homework04.nodes.ReducerNode;
import io.github.javaasasecondlanguage.homework04.nodes.SortOrder;
import io.github.javaasasecondlanguage.homework04.nodes.SorterNode;
import io.github.javaasasecondlanguage.homework04.ops.Mapper;
import io.github.javaasasecondlanguage.homework04.ops.Reducer;
import io.github.javaasasecondlanguage.homework04.ops.mappers.IdentityMapper;

import java.util.List;

import static io.github.javaasasecondlanguage.homework04.nodes.SortOrder.ASCENDING;

public class GraphPartBuilder {

    public static GraphPartBuilder startFrom(ProcNode node) {
        GraphPartBuilder graphBuilder = new GraphPartBuilder();
        graphBuilder.startNode = node;
        graphBuilder.endNode = node;
        return graphBuilder;
    }

    public static GraphPartBuilder init() {
        var identityMapper = new IdentityMapper();
        return startFrom(new MapperNode(identityMapper));
    }

    private ProcNode startNode;
    private ProcNode endNode;

    private GraphPartBuilder() {
    }

    private GraphPartBuilder(ProcNode startNode, ProcNode endNode) {
        this.startNode = startNode;
        this.endNode = endNode;
    }

    public ProcNode getStartNode() {
        return startNode;
    }

    public ProcNode getEndNode() {
        return endNode;
    }

    private GraphPartBuilder then(ProcNode node) {
        endNode.getCollector().addConnection(node, 0);
        endNode = node;
        return this;
    }

    public GraphPartBuilder map(Mapper mapper) {
        var node = new MapperNode(mapper);
        return then(node);
    }

    public GraphPartBuilder reduceBy(List<String> keyColumns, Reducer reducer) {
        var node = new ReducerNode(reducer, keyColumns);
        return then(node);
    }

    public GraphPartBuilder sortBy(List<String> keyColumns, SortOrder order) {
        var node = new SorterNode(keyColumns, order);
        return then(node);
    }

    public GraphPartBuilder sortBy(List<String> keyColumns) {
        return sortBy(keyColumns, ASCENDING);
    }

    public GraphPartBuilder sortThenReduceBy(List<String> keyColumns, Reducer reducer) {
        return sortBy(keyColumns)
                .reduceBy(keyColumns, reducer);
    }

    public GraphPartBuilder join(GraphPartBuilder rightGraphBuilder, List<String> keyColumns) {
        var joinNode = new JoinerNode(keyColumns);
        var leftInputNode = this.endNode;
        var rightInputNode = rightGraphBuilder.endNode;

        leftInputNode.getCollector().addConnection(joinNode, 0);
        rightInputNode.getCollector().addConnection(joinNode, 1);
        this.endNode = joinNode;

        return this;
    }

    public GraphPartBuilder branch() {
        return new GraphPartBuilder(startNode, endNode);
    }
}
