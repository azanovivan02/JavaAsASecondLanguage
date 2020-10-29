package io.github.javaasasecondlanguage.homework01;

import io.github.javaasasecondlanguage.homework01.ops.Operator;
import io.github.javaasasecondlanguage.homework01.ops.Operator.Joiner;
import io.github.javaasasecondlanguage.homework01.ops.Operator.Reducer;
import io.github.javaasasecondlanguage.homework01.ops.reducers.Sorter;
import io.github.javaasasecondlanguage.homework01.ops.reducers.Sorter.Order;

import java.util.List;

import static io.github.javaasasecondlanguage.homework01.ops.reducers.Sorter.Order.ASCENDING;

public class GraphPartBuilder {

    public static GraphPartBuilder startFrom(CompNode node) {
        GraphPartBuilder graphBuilder = new GraphPartBuilder();
        graphBuilder.startNode = node;
        graphBuilder.endNode = node;
        return graphBuilder;
    }

    public static GraphPartBuilder startWith(Operator operator) {
        return startFrom(new CompNode(operator));
    }

    private CompNode startNode;
    private CompNode endNode;

    private GraphPartBuilder() {
    }

    private GraphPartBuilder(CompNode startNode, CompNode endNode) {
        this.startNode = startNode;
        this.endNode = endNode;
    }

    public CompNode getStartNode() {
        return startNode;
    }

    public CompNode getEndNode() {
        return endNode;
    }

    public GraphPartBuilder then(Operator operator) {
        var newNode = new CompNode(operator);
        endNode.addConnection(newNode, 0);
        endNode = newNode;
        return this;
    }

    public GraphPartBuilder sortBy(List<String> keyColumns, Order order) {
        var sorter = new Sorter(order);
        sorter.setKeyColumns(keyColumns);
        return then(sorter);
    }

    public GraphPartBuilder sortBy(List<String> keyColumns) {
        return sortBy(keyColumns, ASCENDING);
    }

    public GraphPartBuilder reduceBy(List<String> keyColumns, Reducer reducer) {
        reducer.setKeyColumns(keyColumns);
        return then(reducer);
    }

    public GraphPartBuilder sortThenReduceBy(List<String> keyColumns, Reducer reducer) {
        return sortBy(keyColumns)
                .reduceBy(keyColumns, reducer);
    }

    public GraphPartBuilder join(GraphPartBuilder rightGraphBuilder, List<String> keyColumns, Joiner joiner) {
        joiner.setKeyColumns(keyColumns);

        var joinNode = new CompNode(joiner);
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
