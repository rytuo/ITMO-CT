package com.rytuo.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.rytuo.drawing.DrawingApi;
import com.rytuo.drawing.figures.Point;

public class AdjacencyMatrixGraph extends Graph {

    private final Map<Integer, Set<Integer>> matrix = new HashMap<>();

    public AdjacencyMatrixGraph(DrawingApi drawingApi) {
        super(drawingApi);
    }

    @Override
    public void addEdge(int from, int to) {
        if (!matrix.containsKey(from)) {
            matrix.put(from, new HashSet<>());
        }
        matrix.get(from).add(to);
    }

    @Override
    public void drawGraph() {
        drawingApi.resetDrawing();
        drawingApi.setTitle("adjacency matrix graph");
        drawingApi.setDrawingAreaSize(1200, 800);

        Set<Integer> vertices = new HashSet<>();
        matrix.forEach((from, tos) -> tos.forEach(to -> {
            vertices.add(from);
            vertices.add(to);
        }));

        Point center = new Point(600, 400);
        int radius = 250;

        Map<Integer, Point> points = this.calculatePoints(vertices, center, radius);

        points.values().forEach(point -> drawingApi.drawCircle(point, 50));
        matrix.forEach((from, tos) -> tos.forEach(to ->
                drawingApi.drawLine(points.get(from), points.get(to))
        ));
        drawingApi.showDrawing();
    }
}
