package org.example;

import org.objects.Gymnast;

import org.example.MainController;
import javafx.beans.property.*;
import javafx.collections.FXCollections;

import javafx.scene.control.cell.PropertyValueFactory;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.beans.value.*;

import java.util.ArrayList;

public class GymnastScreenController implements Initializable{
    @FXML public VBox center;
    @FXML public HBox topHBox;
    @FXML public HBox middleHBox;
    @FXML public TableView gymnastTable;
    @FXML public TextField nameField;
    @FXML public TableColumn<Gymnast, String> firstNameColumn;
    @FXML public TableColumn<Gymnast, String> lastNameColumn;
    @FXML public TableColumn<Gymnast, String> classNameColumn;

    private final StringProperty nameFieldText = new SimpleStringProperty();

    public void initialize(URL location, ResourceBundle resources){
        topHBox.spacingProperty().bind(center.widthProperty().multiply(.1));
        gymnastTable.prefWidthProperty().bind(center.widthProperty().multiply(.8));
        nameFieldText.bindBidirectional(nameField.textProperty());

        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        classNameColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getGroup().getName());
        });
    }

    public void searchDB(){
        String name[] = nameFieldText.get().split(" ");
        String fname = "";
        String lname = "";

        if (name.length == 1){
            fname = name[0];
            lname = name[0];
        }
        if (name.length == 2){
            fname = name[0];
            lname = name[1];
        }
            
        ArrayList<Gymnast> gs = Gymnast.getGymnastsByName(fname,lname);
        gymnastTable.getItems().clear();
        for (Gymnast g:gs)
            //System.out.println(g.getFirstName();
            gymnastTable.getItems().add(g);
        
        System.out.println(nameFieldText);
    }

}
