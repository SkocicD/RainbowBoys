package org.example;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class RadioButtons extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/radio_buttons.fxml"));
        Scene scene = new Scene(root, 300, 200);

        // Load the custom CSS file
        scene.getStylesheets().add(getClass().getResource("/org/example/style.css").toExternalForm());

        primaryStage.setTitle("Rectangular Radio Buttons");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
