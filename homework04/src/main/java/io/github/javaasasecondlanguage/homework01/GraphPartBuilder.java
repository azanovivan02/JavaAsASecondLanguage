package io.github.javaasasecondlanguage.homework01;

import io.github.javaasasecondlanguage.homework01.nodes.JoinerNode;
import io.github.javaasasecondlanguage.homework01.nodes.MapperNode;
import io.github.javaasasecondlanguage.homework01.nodes.ProcNode;
import io.github.javaasasecondlanguage.homework01.nodes.ReducerNode;
import io.github.javaasasecondlanguage.homework01.ops.Operator;
import io.github.javaasasecondlanguage.homework01.ops.Operator.Joiner;
import io.github.javaasasecondlanguage.homework01.ops.Operator.Mapper;
import io.github.javaasasecondlanguage.homework01.ops.Operator.Reducer;
import io.github.javaasasecondlanguage.homework01.ops.reducers.Sorter;
import io.github.javaasasecondlanguage.homework01.ops.reducers.Sorter.Order;

import java.util.List;

import static io.github.javaasasecondlanguage.homework01.ops.reducers.Sorter.Order.ASCENDING;

public class GraphPartBuilder {

    public static GraphPartBuilder startFrom(ProcNode node) {
        GraphPartBuilder graphBuilder = new GraphPartBuilder();
        graphBuilder.startNode = node;
        graphBuilder.endNode = node;
        return graphBuilder;
    }

    public static GraphPartBuilder startWith(Operator operator) {
        if (operator instanceof Mapper) {
            var mapper = (Mapper) operator;
            return startFrom(new MapperNode(mapper));
        } else if (operator instanceof Reducer) {
            var reducer = (Reducer) operator;
            return startFrom(new ReducerNode(reducer));
        } else {
            var joiner = (Joiner) operator;
            return startFrom(new JoinerNode(joiner));
        }
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
        reducer.setKeyColumns(keyColumns);
        var newNode = new ReducerNode(reducer);
        endNode.addConnection(newNode, 0);
        endNode = newNode;
        return this;
    }

    public GraphPartBuilder sortBy(List<String> keyColumns, Order order) {
        var sorter = new Sorter(order);
        return reduceBy(keyColumns, sorter);
    }

    public GraphPartBuilder sortBy(List<String> keyColumns) {
        var sorter = new Sorter(ASCENDING);
        return reduceBy(keyColumns, sorter);
    }

    public GraphPartBuilder sortThenReduceBy(List<String> keyColumns, Reducer reducer) {
        return sortBy(keyColumns)
                .reduceBy(keyColumns, reducer);
    }

    public GraphPartBuilder join(GraphPartBuilder rightGraphBuilder, List<String> keyColumns, Joiner joiner) {
        joiner.setKeyColumns(keyColumns);

        var joinNode = new JoinerNode(joiner);
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
