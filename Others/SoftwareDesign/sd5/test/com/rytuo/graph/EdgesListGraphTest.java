package com.rytuo.graph;

import com.rytuo.drawing.DrawingApi;

public class EdgesListGraphTest extends AGraphTest {
    @Override
    protected Graph initGraph(DrawingApi drawingApi) {
        return new EdgesListGraph(drawingApi);
    }
}
