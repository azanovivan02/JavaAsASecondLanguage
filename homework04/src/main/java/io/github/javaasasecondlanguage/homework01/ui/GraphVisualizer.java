package io.github.javaasasecondlanguage.homework01.ui;

import io.github.javaasasecondlanguage.homework01.ProcGraph;
import io.github.javaasasecondlanguage.homework01.ProcNode;
import io.github.javaasasecondlanguage.homework01.Connection;
import io.github.javaasasecondlanguage.homework01.ops.Operator.OpType;
import org.graphstream.graph.Element;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.layout.HierarchicalLayout;
import org.graphstream.ui.swing.SwingGraphRenderer;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.view.GraphRenderer;
import org.graphstream.ui.view.Viewer.ThreadingModel;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GraphVisualizer {

    public static void visualizeGraph(ProcGraph procGraph) {
        System.setProperty("gs.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        System.setProperty("org.graphstream.ui", "swing");

        Graph visualGraph = new SingleGraph("Main");
//        visualGraph.setAttribute("ui.stylesheet", "url('/Users/ivan.azanov/Documents/Spark/JavaAsASecondLanguage/homework04/src/main/resources/style.css')");
        visualGraph.setAttribute("ui.stylesheet", STYLESHEET);

        visualGraph.setAttribute("layout.force", 0.99);
        visualGraph.setAttribute("layout.quality", 4);

        Map<ProcNode, Node> procVisualNodeMapping = new LinkedHashMap<>();
        for (ProcNode procNode : procGraph.getInputNodes()) {
            visit(procNode, visualGraph, null, procVisualNodeMapping);
        }

        SwingViewer viewer = new SwingViewer(visualGraph, ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.enableAutoLayout();
        GraphRenderer renderer = new SwingGraphRenderer();
        viewer.addView(SwingViewer.DEFAULT_VIEW_ID, renderer);
    }

    private static void visit(ProcNode currentProcNode, Graph visualGraph, Node previousVisualNode, Map<ProcNode, Node> procVisualNodeMapping) {
        if (procVisualNodeMapping.containsKey(currentProcNode)) {
            Node currentVisualNode = procVisualNodeMapping.get(currentProcNode);
            addEdgeToPreviousNodeIfNeeded(visualGraph, previousVisualNode, currentVisualNode);
            return;
        }

        Node currentVisualNode = createVisualNode(currentProcNode, visualGraph);
        procVisualNodeMapping.put(currentProcNode, currentVisualNode);

        addEdgeToPreviousNodeIfNeeded(visualGraph, previousVisualNode, currentVisualNode);
        addCssClasses(currentProcNode, previousVisualNode, currentVisualNode);

        for (Connection info : currentProcNode.getConnections()) {
            visit(info.getNode(), visualGraph, currentVisualNode, procVisualNodeMapping);
        }
    }

    private static Node createVisualNode(ProcNode currentProcNode, Graph visualGraph) {
        Object operator = currentProcNode.getOperator();
        if (operator == null) {
            throw new IllegalStateException("No operator");
        }
        String nodeLabel = operator.getClass().getSimpleName();
        UUID nodeId = UUID.randomUUID();
        Node currentVisualNode = visualGraph.addNode(nodeLabel + nodeId);
        currentVisualNode.setAttribute("ui.label", nodeLabel);
        return currentVisualNode;
    }

    private static void addEdgeToPreviousNodeIfNeeded(Graph visualGraph, Node previousVisualNode, Node currentVisualNode) {
        if (previousVisualNode != null) {
            String edgeId = UUID.randomUUID().toString();
            visualGraph.addEdge(edgeId, previousVisualNode, currentVisualNode, true);
        }
    }

    private static void addCssClasses(ProcNode currentProcNode, Node previousVisualNode, Node currentVisualNode) {
        if (previousVisualNode == null) {
            currentVisualNode.setAttribute("ui.class", "input");
            currentVisualNode.setAttribute("y", 1000);
            return;
        }
        if (currentProcNode.getConnections().isEmpty()) {
//            currentVisualNode.setAttribute("x", 0);
//            currentVisualNode.setAttribute("x", -1000);
//            currentVisualNode.setAttribute("y", -1000);
            currentVisualNode.setAttribute("ui.class", "output");
            return;
        }
        OpType opType = currentProcNode.getOpType();
        switch (opType) {
            case REDUCER: {
                currentVisualNode.setAttribute("ui.class", "reducer");
                break;
            }
            case JOINER: {
                currentVisualNode.setAttribute("ui.class", "joiner");
                break;
            }
        }
    }

    private static HierarchicalLayout createLayout(List<ProcNode> startProcNodes, Map<ProcNode, Node> compVisualNodeMapping) {
//        HierarchicalLayout layout = new HierarchicalLayout();
        String[] rootIds =
                startProcNodes
                        .stream()
                        .map(compVisualNodeMapping::get)
                        .map(Element::getId).toArray(String[]::new);
//        layout.setRoots(rootIds);
//        layout.setQuality(4);
//        layout.setForce(10);
//        return layout;

        return new HierarchicalLayout();
    }

    public static final String STYLESHEET = "/*\n" +
            "*/\n" +
            "graph {\n" +
            "    padding: 100px, 100px;\n" +
            "}\n" +
            "\n" +
            "node {\n" +
            "    text-size: 30;\n" +
            "    text-alignment: at-right;\n" +
            "    text-offset: 30, 30;\n" +
            "    text-background-mode: plain;\n" +
            "\n" +
            "    size: 15px;\n" +
            "    fill-color: #fefeff;\n" +
            "    stroke-color: #3c3c3b;\n" +
            "    stroke-mode: plain;\n" +
            "    stroke-width: 3;\n" +
            "}\n" +
            "\n" +
            "\n" +
            "node.input {\n" +
            "    text-style: bold;\n" +
            "    fill-color: #d1e651;\n" +
            "}\n" +
            "\n" +
            "node.output {\n" +
            "    text-style: bold;\n" +
            "    fill-color: #fd2a38;\n" +
            "}\n" +
            "\n" +
            "node.reducer {\n" +
            "    fill-color: #27ace4;\n" +
            "}\n" +
            "\n" +
            "node.joiner {\n" +
            "    fill-color: #ffc453;\n" +
            "}\n" +
            "\n" +
            "edge {\n" +
            "    arrow-shape: arrow;\n" +
            "    arrow-size: 10px, 4px;\n" +
            "}";
}
