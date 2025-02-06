package org.helpers;

import org.objects.*;
import org.controllers.ErrorMessageController;

import java.sql.*;
import javafx.fxml.*;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.beans.value.*;
import javafx.stage.*;
import javafx.collections.*;
import java.time.LocalDate;

public class Helpers{
    public static String[] EVENTS = {"Floor", "Pommel", "Rings", "Vault", "Parallel Bars", "High Bar"};
    public static String[] EVENT_COLUMNS = {"floor_progress", "pommel_progress", "rings_progress", "vault_progress", "pbar_progress", "hbar_progress"}; 
    public static final int NUM_SKILLS_TIM = 10;
    public static final int NUM_MENS_EVENTS = 6;
    public static void closeWindow(Object o){
        Node n = (Node) o;
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }
    public static FXMLLoader openWindow(String fxmlFile, String title){
        try {
            FXMLLoader loader = new FXMLLoader(Helpers.class.getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.initModality(Modality.APPLICATION_MODAL); // Block interaction with other windows
            stage.setScene(new Scene(root));
            stage.show();
            return loader;
        } catch (Exception e) {
            e.printStackTrace(); // Handle any loading errors
            return null;
        }
    }
    public static boolean inResultSet(ResultSet r, String columnname){
        try {
            ResultSetMetaData metaData = r.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                if (metaData.getColumnName(i).equalsIgnoreCase(columnname)) {
                    return true;
                }
            }
            return false;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public static void errorPopup(String title, String message) {
        ((ErrorMessageController)Helpers.openWindow("/org/example/error_message.fxml",title).getController()).setMessage(message);
    }

}
