package com.rytuo.drawing.fx;

import com.rytuo.drawing.ADrawingApi;

import javafx.application.Application;
import javafx.scene.canvas.GraphicsContext;

public class FxDrawingApi extends ADrawingApi {

    private static FxDrawingApi instance = null;

    public static FxDrawingApi getInstance() {
        if (instance == null) {
            instance = new FxDrawingApi();
        }
        return instance;
    }

    public void draw(GraphicsContext gc) {
        this.getCircles().forEach(circle -> {
            int x = circle.center().x() - circle.radius() / 2;
            int y = circle.center().y() - circle.radius() / 2;
            gc.strokeOval(x, y, circle.radius(), circle.radius());
        });
        this.getLines().forEach(line ->
                gc.strokeLine(line.from().x(), line.from().y(), line.to().x(), line.to().y())
        );
    }

    @Override
    public String getTitle() {
        String title = super.getTitle();
        return "Fx is drawing " + (title == null ? "" : title);
    }

    @Override
    public void showDrawing() {
        Application.launch(FxApplication.class);
    }
}
