
package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.fxml.*;

import java.sql.*;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 300, 200);

        primaryStage.setTitle("JavaFX Maven Example");
        primaryStage.setScene(scene);
        primaryStage.show();
        Button button = new Button("Connect to DB");
        root.getChildren().add(button);
        button.setOnAction(e -> connectToDatabase());
        //Label label = new Label("Hello, JavaFX!");
        //root.getChildren().add(label);
    }

    public static void main(String[] args) {
        launch(args);
        System.out.println(Main.class.getResource("/org/example/hello_world.fxml"));
    }


    public void connectToDatabase(){
        String url = "jdbc:postgresql://localhost:5432/rainbow_boys"; // Update with your database name
        String user = "test"; // Update with your username
        String password = "test"; // Update with your password

        Connection conn = null;

        try {
            // Establish connection
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to PostgreSQL database successfully!");
            String sql = "INSERT INTO gymnasts (first_name, last_name) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                // Set parameters
                pstmt.setString(1, "Alex"); // Replace with your value
                pstmt.setString(2, "Skocic"); // Replace with your value

                // Execute insert
                int rowsAffected = pstmt.executeUpdate();
                System.out.println("Rows inserted: " + rowsAffected);
            }
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        } finally {
            // Close connection if it's not null
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
