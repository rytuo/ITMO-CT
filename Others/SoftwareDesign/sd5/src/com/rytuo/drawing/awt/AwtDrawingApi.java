package com.rytuo.drawing.awt;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.rytuo.drawing.ADrawingApi;

public class AwtDrawingApi extends ADrawingApi {

    private static AwtDrawingApi instance = null;

    public static AwtDrawingApi getInstance() {
        if (instance == null) {
            instance = new AwtDrawingApi();
        }
        return instance;
    }

    private void draw(Graphics graphics) {
        this.getCircles().forEach(circle -> {
            int x = circle.center().x() - circle.radius() / 2;
            int y = circle.center().y() - circle.radius() / 2;
            graphics.drawOval(x, y, circle.radius(), circle.radius());
        });
        this.getLines().forEach(line ->
                graphics.drawLine(line.from().x(), line.from().y(), line.to().x(), line.to().y())
        );
    }

    @Override
    public String getTitle() {
        String title = super.getTitle();
        return "Awt is drawing " + (title == null ? "" : title);
    }

    @Override
    public void showDrawing() {
        AwtDrawingApi awtDrawingApi = AwtDrawingApi.getInstance();
        Frame frame = new Frame() {
            @Override
            public void paint(Graphics graphics) {
                awtDrawingApi.draw(graphics);
            }
        };
        frame.setTitle(awtDrawingApi.getTitle());
        frame.setSize(awtDrawingApi.getDrawingAreaWidth(), awtDrawingApi.getDrawingAreaHeight());
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        frame.setVisible(true);
    }
}
