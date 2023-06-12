package com.rytuo.drawing;

import java.util.ArrayList;
import java.util.List;

import com.rytuo.drawing.figures.Circle;
import com.rytuo.drawing.figures.Line;
import com.rytuo.drawing.figures.Point;

public abstract class ADrawingApi implements DrawingApi {

    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 400;
    private static final String DEFAULT_TITLE = "default";

    protected int width = DEFAULT_WIDTH;
    protected int height = DEFAULT_HEIGHT;
    protected String title = DEFAULT_TITLE;
    protected final List<Circle> circles = new ArrayList<>();
    protected final List<Line> lines = new ArrayList<>();


    @Override
    public void resetDrawing() {
        width = DEFAULT_WIDTH;
        height = DEFAULT_HEIGHT;
        title = DEFAULT_TITLE;
        circles.clear();
        lines.clear();
    }

    @Override
    public void setDrawingAreaSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public int getDrawingAreaWidth() {
        return width;
    }

    @Override
    public int getDrawingAreaHeight() {
        return height;
    }

    public List<Circle> getCircles() {
        return circles;
    }

    public List<Line> getLines() {
        return lines;
    }

    @Override
    public void drawCircle(Point center, int radius) {
        circles.add(new Circle(center, radius));
    }

    @Override
    public void drawLine(com.rytuo.drawing.figures.Point from, Point to) {
        lines.add(new Line(from, to));
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }
}
