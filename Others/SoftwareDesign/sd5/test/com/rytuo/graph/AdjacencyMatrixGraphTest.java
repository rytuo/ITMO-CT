package com.rytuo.graph;

import com.rytuo.drawing.DrawingApi;

public class AdjacencyMatrixGraphTest extends AGraphTest {
    @Override
    protected Graph initGraph(DrawingApi drawingApi) {
        return new AdjacencyMatrixGraph(drawingApi);
    }
}
