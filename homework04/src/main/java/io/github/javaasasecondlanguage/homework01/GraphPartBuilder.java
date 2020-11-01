package io.github.javaasasecondlanguage.homework01;

import io.github.javaasasecondlanguage.homework01.nodes.JoinerNode;
import io.github.javaasasecondlanguage.homework01.nodes.MapperNode;
import io.github.javaasasecondlanguage.homework01.nodes.ProcNode;
import io.github.javaasasecondlanguage.homework01.nodes.ReducerNode;
import io.github.javaasasecondlanguage.homework01.nodes.SorterNode;
import io.github.javaasasecondlanguage.homework01.ops.Mapper;
import io.github.javaasasecondlanguage.homework01.ops.Reducer;

import java.util.List;

import static io.github.javaasasecondlanguage.homework01.nodes.SorterNode.Order.ASCENDING;

public class GraphPartBuilder {

    public static GraphPartBuilder startFrom(ProcNode node) {
        GraphPartBuilder graphBuilder = new GraphPartBuilder();
        graphBuilder.startNode = node;
        graphBuilder.endNode = node;
        return graphBuilder;
    }

    public static GraphPartBuilder startWith(Mapper mapper) {
        return startFrom(new MapperNode(mapper));
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

    public GraphPartBuilder map(Mapper mapper) {
        var newNode = new MapperNode(mapper);
        endNode.addConnection(newNode, 0);
        endNode = newNode;
        return this;
    }

    public GraphPartBuilder reduceBy(List<String> keyColumns, Reducer reducer) {
        var newNode = new ReducerNode(reducer, keyColumns);
        endNode.addConnection(newNode, 0);
        endNode = newNode;
        return this;
    }

    public GraphPartBuilder sortBy(List<String> keyColumns, SorterNode.Order order) {
        SorterNode newNode = new SorterNode(keyColumns, order);
        endNode.addConnection(newNode, 0);
        endNode = newNode;
        return this;
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

        leftInputNode.addConnection(joinNode, 0);
        rightInputNode.addConnection(joinNode, 1);
        this.endNode = joinNode;

        return this;
    }

    public GraphPartBuilder branch() {
        return new GraphPartBuilder(startNode, endNode);
    }
}
