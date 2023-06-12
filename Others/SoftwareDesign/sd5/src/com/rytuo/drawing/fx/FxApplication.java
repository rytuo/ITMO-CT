package com.rytuo.drawing.fx;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class FxApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        FxDrawingApi fxDrawingApi = FxDrawingApi.getInstance();
        primaryStage.setTitle(fxDrawingApi.getTitle());
        Group root = new Group();
        Canvas canvas = new Canvas(fxDrawingApi.getDrawingAreaWidth(), fxDrawingApi.getDrawingAreaHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();
        fxDrawingApi.draw(gc);
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
