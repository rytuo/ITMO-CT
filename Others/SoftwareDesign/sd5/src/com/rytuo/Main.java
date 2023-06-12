package com.rytuo;

import com.rytuo.drawing.awt.AwtDrawingApi;
import com.rytuo.drawing.DrawingApi;
import com.rytuo.drawing.fx.FxDrawingApi;
import com.rytuo.graph.AdjacencyMatrixGraph;
import com.rytuo.graph.EdgesListGraph;
import com.rytuo.graph.Graph;

public class Main {

    private final static String[] DEFAULT_PARAMETERS = new String[] {"awt", "list"};

    public static void main(String... args) {
        String[] parameters = DEFAULT_PARAMETERS.clone();
        if (args.length > 0) {
            parameters[0] = args[0];
        }
        if (args.length > 1) {
            parameters[1] = args[1];
        }

        DrawingApi drawingApi = switch (parameters[0]) {
            case "awt" -> AwtDrawingApi.getInstance();
            case "fx" -> FxDrawingApi.getInstance();
            default -> throw new RuntimeException("Invalid drawing api");
        };

        Graph graph = switch (parameters[1]) {
            case "list" -> new EdgesListGraph(drawingApi);
            case "matrix" -> new AdjacencyMatrixGraph(drawingApi);
            default -> throw new RuntimeException("Invalid graph implementation");
        };

        fillGraph(graph);
        graph.drawGraph();
    }

    private static void fillGraph(Graph graph) {
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 0);
        graph.addEdge(2, 4);
        graph.addEdge(1, 4);
    }
}
