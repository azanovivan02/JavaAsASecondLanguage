package io.github.javaasasecondlanguage.homework01;

import io.github.javaasasecondlanguage.homework01.nodes.CompNode;
import io.github.javaasasecondlanguage.homework01.ops.Operator;
import io.github.javaasasecondlanguage.homework01.ops.Operator.Joiner;
import io.github.javaasasecondlanguage.homework01.ops.Operator.Reducer;
import io.github.javaasasecondlanguage.homework01.ops.reducers.Sorter;
import io.github.javaasasecondlanguage.homework01.ops.reducers.Sorter.Order;

import java.util.List;

import static io.github.javaasasecondlanguage.homework01.ops.reducers.Sorter.Order.ASCENDING;

public class GraphBuilder {

    public static GraphBuilder startWith(CompNode node) {
        GraphBuilder graphBuilder = new GraphBuilder();
        graphBuilder.startNode = node;
        graphBuilder.endNode = node;
        return graphBuilder;
    }

    public static GraphBuilder startWith(Operator operator) {
        return startWith(new CompNode(operator));
    }

    private CompNode startNode;
    private CompNode endNode;

    private GraphBuilder() {
    }

    private GraphBuilder(CompNode startNode, CompNode endNode) {
        this.startNode = startNode;
        this.endNode = endNode;
    }

    public CompNode getStartNode() {
        return startNode;
    }

    public CompNode getEndNode() {
        return endNode;
    }

    public GraphBuilder then(Operator operator) {
        CompNode newNode = new CompNode(operator);
        endNode.addConnection(newNode, 0);
        endNode = newNode;
        return this;
    }

    public GraphBuilder sortBy(List<String> keyColumns, Order order) {
        Sorter sorter = new Sorter(order);
        sorter.setKeyColumns(keyColumns);
        return then(sorter);
    }

    public GraphBuilder sortBy(List<String> keyColumns) {
        return sortBy(keyColumns, ASCENDING);
    }

    public GraphBuilder reduceBy(List<String> keyColumns, Reducer reducer) {
        reducer.setKeyColumns(keyColumns);
        return then(reducer);
    }

    public GraphBuilder sortThenReduceBy(List<String> keyColumns, Reducer reducer) {
        return sortBy(keyColumns)
                .reduceBy(keyColumns, reducer);
    }

    public GraphBuilder join(GraphBuilder rightGraphBuilder, List<String> keyColumns, Joiner joiner) {
        joiner.setKeyColumns(keyColumns);

        CompNode joinNode = new CompNode(joiner);
        CompNode leftInputNode = this.endNode;
        CompNode rightInputNode = rightGraphBuilder.endNode;

        leftInputNode.addConnection(joinNode, 0);
        rightInputNode.addConnection(joinNode, 1);
        this.endNode = joinNode;

        return this;
    }

    public GraphBuilder branch() {
        return new GraphBuilder(startNode, endNode);
    }
}
