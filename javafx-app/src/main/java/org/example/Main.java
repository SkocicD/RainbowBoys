
package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Move MenuBar to macOS top bar if needed (optional)
        // System.setProperty("apple.laf.useScreenMenuBar", "true");

        try{
            System.out.println("here");
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/hello_world.fxml"));
            primaryStage.setTitle("Hello World");
            primaryStage.setScene(new Scene(root, 300, 200));
            primaryStage.show();
        }   
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
