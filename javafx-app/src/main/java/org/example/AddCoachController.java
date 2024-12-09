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

public class AddCoachController implements Initializable{

    @FXML public TextField firstNameField;
    @FXML public TextField lastNameField;
    @FXML public Button addButton;
    @FXML public Button cancelButton;


    public void initialize(URL location, ResourceBundle resources){ }
    
    public void addCoach(){

        if (lastNameField.getText().equals("")){
            HelperFunctions.errorPopup("Input Error", "Enter a First Name");
            return;
        }
        if (firstNameField.getText().equals("")){
            HelperFunctions.errorPopup("Input Error", "Enter a Last Name");
            return;
        }

        String fname, lname;

        fname = firstNameField.getText();
        lname = lastNameField.getText();
        
        DatabaseConnector.insertCoach(fname,lname);
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
