package org.controllers;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.layout.Priority;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import org.objects.*;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/fxml/main.fxml"));
        Parent root = loader.load();
        MainController cont = loader.getController();
        Scene scene = new Scene(root);
        ToggleGroup headerToggleGroup = new ToggleGroup();

        primaryStage.setTitle("Rainbow Boys");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
