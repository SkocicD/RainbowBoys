package org.controllers;

import org.objects.*;
import org.helpers.*;

import javafx.beans.property.*;
import javafx.collections.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.beans.value.*;
import javafx.scene.input.*;

import java.util.ArrayList;
import java.util.HashSet;

public class GymnastScreenController implements Initializable{
    @FXML public VBox center;
    @FXML public HBox topHBox;
    @FXML public HBox middleHBox;
    @FXML public TableView<Gymnast> gymnastTable;
    @FXML public TextField nameField;
    @FXML public TextField ageField;
    @FXML public ComboBox<Object> classField;
    @FXML public TableColumn<Gymnast, String> firstNameColumn;
    @FXML public TableColumn<Gymnast, String> lastNameColumn;
    @FXML public TableColumn<Gymnast, String> birthdateColumn;
    @FXML public TableColumn<Gymnast, Number> ageColumn;
    @FXML public Button printButton;

    private final StringProperty printListText = new SimpleStringProperty("Show List to Print (0)");

    private HashSet<Integer> printSet = new HashSet<>();

    public void initialize(URL location, ResourceBundle resources){

        topHBox.spacingProperty().bind(center.widthProperty().multiply(.1));
        gymnastTable.prefWidthProperty().bind(center.widthProperty().multiply(.8));

        firstNameColumn.prefWidthProperty().bind(gymnastTable.widthProperty().multiply(.25));
        lastNameColumn.prefWidthProperty().bind(gymnastTable.widthProperty().multiply(.25));
        ageColumn.prefWidthProperty().bind(gymnastTable.widthProperty().multiply(.25));
        birthdateColumn.prefWidthProperty().bind(gymnastTable.widthProperty().multiply(.25));

        // dont allow more than one space in the name field
        nameField.addEventFilter(KeyEvent.KEY_TYPED, e -> {
            if (nameField.getText().indexOf(' ') != -1 && e.getCharacter().equals(" "))
                e.consume();
        });

        // dont allow non digits in the age field
        ageField.addEventFilter(KeyEvent.KEY_TYPED, e -> {
            if (!e.getCharacter().matches("[0-9]"))
                e.consume();  
        });

        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty()); 
        ageColumn.setCellValueFactory(cellData -> cellData.getValue().ageProperty()); 
        birthdateColumn.setCellValueFactory(cellData -> cellData.getValue().birthdateProperty());
        // if there is a double click, call method
        gymnastTable.setOnMouseClicked(event -> {
            // Check if the click count is 2 for a double-click
            if (event.getClickCount() == 2) { 
                openGymnastEditor();
                // Get the selected row (item)
            }
        });
        refresh();
    }

    public void refresh(){
        classField.getItems().clear();
        classField.getItems().addAll(DatabaseConnector.getClasses(null));
        classField.getItems().add("No Class");
        if (classField.getValue() == null)
            classField.getSelectionModel().select("No Class");
        fillGymnastTable();
    }

    public void fillGymnastTable(){
        // query database
        RainbowClass rclass = null;
        if (classField.getValue() instanceof RainbowClass) rclass = (RainbowClass) classField.getValue(); 
        String clsname = null;
        if (rclass!=null) clsname = rclass.getName();

        Integer age = null;
        if (!ageField.getText().equals("")) age = Integer.parseInt(ageField.getText());

        String name[] = nameField.getText().split(" ");
        String fname = null, lname = null;
        if (name.length > 0) fname = name[0];
        if (name.length > 1) lname = name[1]; 

        gymnastTable.setItems(DatabaseConnector.getGymnasts(fname, lname, age, clsname));
    }


    public void openGymnastEditor(){
        Gymnast g = gymnastTable.getSelectionModel().getSelectedItem();
        if (g == null) return;

        int gymnastId = g.getId();
        System.out.println("Double-clicked on: " + gymnastId); 

        ProgressController ctrl = Helpers.openWindow("/org/fxml/progress_window.fxml", "Edit Gymnast Progress").getController();
        ctrl.getStage().setOnHidden(event-> refresh());
        ctrl.setGymnastInfo(new Gymnast(g)); 
    }

    public void openGymnastCreator(){
        Helpers.openWindow("/org/fxml/add_gymnast.fxml","Add Gymnast");
    }

    public void printPDF(){
        ObservableList<Gymnast> gymnasts = FXCollections.observableArrayList();
        for (Gymnast g: gymnastTable.getItems())
            gymnasts.add(DatabaseConnector.getGymnast(g.getId()));
        String className;
        if (classField.getValue() instanceof RainbowClass) className = ((RainbowClass)classField.getValue()).getName(); else className = "No Class";
        PDFPrinter.print(className, gymnasts);
    }
}
