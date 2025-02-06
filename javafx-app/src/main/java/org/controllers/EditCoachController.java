package org.controllers;

import org.objects.*;
import org.helpers.*;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.time.LocalDate;
import javafx.collections.*;
import javafx.stage.*;

public class EditCoachController {

    @FXML private Button cancelButton;
    @FXML private Button updateButton;
    @FXML private Button addClassButton;
    @FXML private Label coachNameLabel;
    @FXML private ListView<RainbowClass> classesList;
    @FXML private ComboBox<RainbowClass> classesDropdown;


    private Coach c;

    @FXML
    public void initialize() {
        cancelButton.setOnAction(event->Helpers.closeWindow(event.getSource()));
        ObservableList<RainbowClass> classList = DatabaseConnector.getClasses(null);
        classesDropdown.setItems(classList);
        classesDropdown.getSelectionModel().select(classList.get(0));

        
        classesList.setPrefHeight(50);
    }

    public Stage getStage(){
        return (Stage)classesList.getScene().getWindow();
    }

    public void setCoachInfo(Coach c){
        this.c = c;
        classesList.setItems(DatabaseConnector.getClassCoaches(c.getId()));
    }

    public void updateClassCoaches(){
        DatabaseConnector.deleteClassCoaches(c.getId());
        for (RainbowClass rclass:classesList.getItems())
            DatabaseConnector.insertClassCoaches(c.getId(), rclass.getId());
        Helpers.closeWindow(updateButton);
    }
    
    public void addClassToList(){
        RainbowClass selectedClass = classesDropdown.getValue();
        for (RainbowClass c: classesList.getItems()){
            if (c.getId() == selectedClass.getId())
                return;
        }
        classesList.getItems().add(selectedClass);
    }
    public void removeSelectedItems(){
        ObservableList<RainbowClass> selectedItems =classesList.getSelectionModel().getSelectedItems();
        ObservableList<RainbowClass> itemsToRemove = FXCollections.observableArrayList(selectedItems);
        classesList.getItems().removeAll(itemsToRemove);
    }


}




