package org.example;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.layout.Priority;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

public class FirstFrontEnd extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/main_front_end.fxml"));
        Parent root = loader.load();
        Controller cont = loader.getController();
        Scene scene = new Scene(root);
        ToggleGroup headerToggleGroup = new ToggleGroup();

        scene.getStylesheets().add(getClass().getResource("/org/example/styles.css").toExternalForm());

        primaryStage.setTitle("Rectangular Radio Buttons");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
