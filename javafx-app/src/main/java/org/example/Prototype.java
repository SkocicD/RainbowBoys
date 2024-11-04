package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Prototype extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/prototype.fxml"));
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/org/example/style.css").toExternalForm());
        primaryStage.setTitle("Rainbow Boys");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
