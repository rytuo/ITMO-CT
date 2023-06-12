package com.rytuo.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.rytuo.drawing.DrawingApi;
import com.rytuo.drawing.figures.Point;

public class EdgesListGraph extends Graph {

    protected record Edge(int from, int to) {}

    private final List<Edge> edges = new ArrayList<>();

    public EdgesListGraph(DrawingApi drawingApi) {
        super(drawingApi);
    }

    @Override
    public void addEdge(int fromV, int toV) {
        this.edges.add(new Edge(fromV, toV));
    }

    @Override
    public void drawGraph() {
        drawingApi.resetDrawing();
        drawingApi.setTitle("edges list graph");
        drawingApi.setDrawingAreaSize(1200, 800);

        Set<Integer> vertices = new HashSet<>();
        edges.forEach(edge -> {
            vertices.add(edge.from);
            vertices.add(edge.to);
        });

        Point center = new Point(600, 400);
        int radius = 250;

        Map<Integer, Point> points = this.calculatePoints(vertices, center, radius);

        points.values().forEach(point -> drawingApi.drawCircle(point, 50));
        edges.forEach(edge -> drawingApi.drawLine(points.get(edge.from), points.get(edge.to)));

        drawingApi.showDrawing();
    }
}
