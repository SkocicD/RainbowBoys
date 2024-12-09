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
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.beans.value.*;
import javafx.scene.input.*;

public class CoachScreenController implements Initializable{

    @FXML public VBox center;
    @FXML public HBox topHBox;
    @FXML public HBox middleHBox;
    @FXML public TextField nameField;
    @FXML public TableView<Coach> coachTable;
    @FXML public TableColumn<Coach, String> firstNameColumn;
    @FXML public TableColumn<Coach, String> lastNameColumn;

    public void initialize(URL location, ResourceBundle resources){
        //firstHBox.prefHeightProperty().bind(center.heightProperty().multiply(0.25));
        topHBox.spacingProperty().bind(center.widthProperty().multiply(.1));
        coachTable.prefWidthProperty().bind(center.widthProperty().multiply(.8));

        nameField.addEventFilter(KeyEvent.KEY_TYPED, e -> {
            if (nameField.getText().indexOf(' ') != -1 && e.getCharacter().equals(" "))
                e.consume();
        });

        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty()); 
        firstNameColumn.prefWidthProperty().bind(coachTable.widthProperty().multiply(0.5));
        lastNameColumn.prefWidthProperty().bind(coachTable.widthProperty().multiply(0.5));

        coachTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2)
                openCoachEditor();
        });

        fillCoachTable();


    }

    public void fillCoachTable(){
        String name[] = nameField.getText().split(" ");
        String fname = null, lname = null;
        if (name.length > 0) fname = name[0]; 
        if (name.length > 1) lname = name[1]; 
        coachTable.setItems(DatabaseConnector.getCoaches(fname, lname));
    }
    public void openAddCoachWindow(){
        HelperFunctions.openWindow("/org/example/add_coach.fxml", "Add a New Coach");
    }
    public void openCoachEditor() {
        Coach c = coachTable.getSelectionModel().getSelectedItem();
        if (c == null) return;

        EditCoachController ctrl = (EditCoachController)HelperFunctions.openWindow("/org/example/edit_coach.fxml", "Assign Classes to a Coach").getController();
        ctrl.setCoachInfo(new Coach(c));
    }

}
