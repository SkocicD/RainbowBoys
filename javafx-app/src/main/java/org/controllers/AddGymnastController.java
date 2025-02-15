package org.controllers;

import org.objects.*;
import org.helpers.*;

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

public class AddGymnastController implements Initializable{

    @FXML public DatePicker birthdateField;
    @FXML public TextField firstNameField;
    @FXML public TextField lastNameField;
    @FXML public ComboBox<RainbowClass> classField;
    @FXML public Button addButton;
    @FXML public Button cancelButton;


    public void initialize(URL location, ResourceBundle resources){
        classField.setItems(DatabaseConnector.getClasses(null));
        firstNameField.addEventFilter(KeyEvent.KEY_TYPED, e -> {
            if (e.getCharacter().equals(" "))
                e.consume();
        });
        lastNameField.addEventFilter(KeyEvent.KEY_TYPED, e -> {
            if (e.getCharacter().equals(" "))
                e.consume();
        });
        cancelButton.setOnAction(event->Helpers.closeWindow(event.getSource()));
    }
    

    public void addGymnast(){
        if (firstNameField.getText().equals("")){
            error("Enter a First Name");
            return;
        }
        if (lastNameField.getText().equals("")) {
            error("Enter a Last Name");
            return;
        }
        if (birthdateField.getValue() == null){
            error("Enter a birthdate");
            return;
        }
        if (classField.getValue() == null){
            error("Select a class");
            return;
        }
        String fname, lname;
        LocalDate bdate;
        RainbowClass rclass;

        fname = firstNameField.getText();
        lname = lastNameField.getText();
        bdate = birthdateField.getValue();
        rclass = classField.getValue();
        
        DatabaseConnector.insert_gymnast(fname,lname,bdate,rclass.getId());
        System.out.println("Gymnast Added");
        Helpers.closeWindow(addButton);

    }


    public void error(String message){
        System.out.println(message);
    }
}
