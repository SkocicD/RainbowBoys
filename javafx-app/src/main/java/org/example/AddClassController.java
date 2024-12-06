package org.example;

import org.objects.*;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.beans.value.*;
import javafx.stage.Stage;
import javafx.collections.*;
import java.time.LocalDate;

public class AddClassController implements Initializable{

    @FXML public TextField nameField;
    @FXML public Button addButton;
    @FXML public Button cancelButton;


    public void initialize(URL location, ResourceBundle resources){ }
    
    public void addClass(){

        if (nameField.getText().equals("")){
            error("Enter a class name");
            return;
        }
        String name;

        name = nameField.getText();
        
        DatabaseConnector.insertClass(name);
        System.out.println("Class Added");
        HelperFunctions.closeWindow(addButton);
    }

    public void closeWindow(){
        HelperFunctions.closeWindow(cancelButton);
    }


    public void error(String message){
        System.out.println(message);
    }
}
