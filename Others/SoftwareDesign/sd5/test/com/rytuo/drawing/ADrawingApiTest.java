package com.rytuo.drawing;

import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rytuo.drawing.figures.Circle;
import com.rytuo.drawing.figures.Line;
import com.rytuo.drawing.figures.Point;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public abstract class ADrawingApiTest {

    private Random random = new Random();

    protected DrawingApi drawingApi;

    protected abstract DrawingApi initApi();

    @BeforeEach
    void beforeEach() {
        drawingApi = initApi();
    }

    private Point createPoint() {
        return new Point(random.nextInt(), random.nextInt());
    }

    @Test
    void testResetDrawing() {
        drawingApi.setDrawingAreaSize(37232, 27382);
        drawingApi.setTitle("hvbuabuavbq");
        drawingApi.drawLine(createPoint(), createPoint());
        drawingApi.drawLine(createPoint(), createPoint());
        drawingApi.drawCircle(createPoint(), 991839183);

        drawingApi.resetDrawing();

        assertThat(drawingApi.getDrawingAreaWidth()).isEqualTo(600);
        assertThat(drawingApi.getDrawingAreaHeight()).isEqualTo(400);
        assertThat(drawingApi.getTitle()).contains("default");
        assertThat(drawingApi.getLines()).isEmpty();
        assertThat(drawingApi.getCircles()).isEmpty();
    }

    @Test
    void testSetDrawingAreaSize() {
        int width = 23445667, height = 86756352;
        drawingApi.setDrawingAreaSize(width, height);
        assertThat(drawingApi.getDrawingAreaWidth()).isEqualTo(width);
        assertThat(drawingApi.getDrawingAreaHeight()).isEqualTo(height);
    }

    @Test
    void testCircles() {
        Point center = new Point(111, 222);
        int radius = 500;
        drawingApi.drawCircle(center, radius);
        assertThat(drawingApi.getCircles().size()).isOne();
        Circle circle = drawingApi.getCircles().get(0);
        assertThat(circle.center()).isEqualTo(center);
        assertThat(circle.radius()).isEqualTo(radius);
    }

    @Test
    void testLines() {
        Point from = new Point(872418, 4343);
        Point to = new Point(565645, 929292);
        drawingApi.drawLine(from, to);
        assertThat(drawingApi.getLines().size()).isOne();
        Line line = drawingApi.getLines().get(0);
        assertThat(line.from()).isEqualTo(from);
        assertThat(line.to()).isEqualTo(to);
    }

    @Test
    void testTitle() {
        String title = "jfiquiunbnbyvcrvtbynumi";
        drawingApi.setTitle(title);
        assertThat(drawingApi.getTitle()).contains(title);
    }
}
