package com.rytuo.drawing;

import java.util.List;

import com.rytuo.drawing.figures.Circle;
import com.rytuo.drawing.figures.Line;
import com.rytuo.drawing.figures.Point;

/**
 * Interface for drawing implementations.
 * Note that all implementations are singleton,
 * so you can  draw only one picture at time.
 */
public interface DrawingApi {

    void resetDrawing();

    void setDrawingAreaSize(int width, int height);
    int getDrawingAreaWidth();
    int getDrawingAreaHeight();

    void drawCircle(Point center, int radius);
    void drawLine(Point from, Point to);

    List<Circle> getCircles();
    List<Line> getLines();

    String getTitle();
    void setTitle(String title);

    void showDrawing();
}
