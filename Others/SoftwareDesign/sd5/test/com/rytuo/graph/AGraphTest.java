package com.rytuo.graph;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rytuo.drawing.DrawingApi;
import com.rytuo.drawing.figures.Point;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public abstract class AGraphTest {

    private final Random random = new Random();

    private Graph graph;

    @Mock
    private DrawingApi drawingApi;

    protected abstract Graph initGraph(DrawingApi drawingApi);

    @BeforeEach
    void beforeEach() {
        graph = initGraph(drawingApi);
    }

    @Test
    void testGraph() {
        int n = 5;
        Set<Integer> vertices = new HashSet<>();

        for (int i = 0; i < n; i++) {
            int a = random.nextInt(), b = random.nextInt();
            vertices.add(a);
            vertices.add(b);
            graph.addEdge(a, b);
        }

        graph.drawGraph();

        verify(drawingApi).resetDrawing();
        verify(drawingApi, times(vertices.size())).drawCircle(any(Point.class), any(Integer.class));
        verify(drawingApi, times(n)).drawLine(any(Point.class), any(Point.class));
        verify(drawingApi).showDrawing();
    }
}
