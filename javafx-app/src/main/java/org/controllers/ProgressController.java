package org.controllers;

import org.objects.*;
import org.helpers.*;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.time.LocalDate;
import javafx.collections.*;
import javafx.stage.*;

public class ProgressController {

    @FXML private GridPane skillsGrid;
    @FXML private Button cancelButton;
    @FXML private Button updateButton;
    @FXML private Button addClassButton;
    @FXML private Label gymnastDetailsLabel;
    @FXML private ListView<RainbowClass> classesList;
    @FXML private ComboBox<RainbowClass> classesDropdown;

    private CheckBox[][] checkBoxes = new CheckBox[Helpers.EVENTS.length][Helpers.NUM_SKILLS_TIM];
    private LocalDate[][] progressCopy = new LocalDate[Helpers.EVENTS.length][Helpers.NUM_SKILLS_TIM];
    private Gymnast g;

    @FXML
    public void initialize() {
        for (int i = 0; i < Helpers.EVENTS.length; i++){
            // Create a label for each row
            skillsGrid.add(new Label(Helpers.EVENTS[i]), 0, i);

            // Create a container for the checkboxes (1px between them)
            HBox checkBoxRow = new HBox(1);
            for (int col = 0; col < Helpers.NUM_SKILLS_TIM; col++) {
                // wrappers for use in lambda
                final int row = i;
                final int column = col;
                CheckBox checkBox = new CheckBox();
                checkBox.setOnAction(event -> checkOff(row,column));
                checkBoxes[i][col] = checkBox;
                checkBoxRow.getChildren().add(checkBox);
                if (col%2 == 1)
                    checkBoxRow.getChildren().add(new Label("  "));
            }
            skillsGrid.add(checkBoxRow, 1, i);
        }
        cancelButton.setOnAction(event->Helpers.closeWindow(event.getSource()));
        ObservableList<RainbowClass> classList = DatabaseConnector.getClasses(null);
        classesDropdown.setItems(classList);
        classesDropdown.getSelectionModel().select(classList.get(0));
        classesList.setPrefHeight(50);

    }

    public Stage getStage(){
        return (Stage)classesList.getScene().getWindow();
    }

    public void setGymnastInfo(Gymnast g){
        this.g = g;
        gymnastDetailsLabel.setText(g.getFirstName() + " " + g.getLastName() + ", " + g.getAge() + " years old");
        for (int r = 0; r < 6; r++){
            for (int c = 0; c < Helpers.NUM_SKILLS_TIM; c++){
                progressCopy[r][c] = g.getProgress()[r][c];
                if (g.getProgress()[r][c] != null)
                    checkBoxes[r][c].setSelected(true);
            }
        }
        classesList.setItems(DatabaseConnector.getClassesForGymnast(g.getId()));
    }

    public void updateGymnastInfo(){
        DatabaseConnector.updateGymnast(g);
        DatabaseConnector.deleteGymnastClasses(g.getId());
        for (RainbowClass rclass:classesList.getItems())
            DatabaseConnector.insertGymnastClasses(g.getId(), rclass.getId());
        Helpers.closeWindow(updateButton);
    }


    private void checkOff(int row, int col){
        if (checkBoxes[row][col].isSelected())
            for (int c = col; c >= 0; c--)
                checkBoxes[row][c].setSelected(true);
        for (int r = 0; r < 6; r++){
            for (int c = 0; c < Helpers.NUM_SKILLS_TIM; c++){
                if (checkBoxes[r][c].isSelected() && progressCopy[r][c]==null) g.getProgress()[r][c] = LocalDate.now();
                if (!checkBoxes[r][c].isSelected()) g.getProgress()[r][c] = null;
            }
        }
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




