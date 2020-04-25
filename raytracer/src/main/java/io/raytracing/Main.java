package io.raytracing;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //primaryStage.setTitle("Курсовая работа - Миневска Ани Стоянова");

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("MainForm.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (root != null) {
            Scene scene = new Scene(root);

            primaryStage.setScene(scene);
        }
        primaryStage.show();
    }
}
