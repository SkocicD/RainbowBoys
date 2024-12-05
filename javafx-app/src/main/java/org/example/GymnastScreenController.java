package org.example;

import org.objects.*;

import org.example.MainController;
import javafx.beans.property.*;
import javafx.collections.FXCollections;

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
    @FXML public ComboBox<RainbowClass> classField;
    @FXML public TableColumn<Gymnast, String> firstNameColumn;
    @FXML public TableColumn<Gymnast, String> lastNameColumn;
    @FXML public TableColumn<Gymnast, String> birthdateColumn;
    @FXML public TableColumn<Gymnast, Number> ageColumn;
    @FXML public Button printButton;
    @FXML public Button showPrintListButton;

    private final StringProperty printListText = new SimpleStringProperty("Show List to Print (0)");

    private HashSet<Integer> printSet = new HashSet<>();

    public void initialize(URL location, ResourceBundle resources){

        topHBox.spacingProperty().bind(center.widthProperty().multiply(.1));
        gymnastTable.prefWidthProperty().bind(center.widthProperty().multiply(.8));
        showPrintListButton.textProperty().bind(printListText);

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
        // setup gymnast table
        gymnastTable.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);
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
        classField.setItems(DatabaseConnector.getClasses(null));
        fillGymnastTable();
    }

    public void fillGymnastTable(){
        // query database
        RainbowClass rclass = classField.getValue();
        String clsname = null;
        if (rclass!=null) clsname = rclass.getName();

        Integer age = null;
        if (!ageField.getText().equals("")) age = Integer.parseInt(ageField.getText());

        String name[] = nameField.getText().split(" ");
        String fname = null, lname = null;
        if (name.length > 0) fname = name[0];
        if (name.length > 1) lname = name[1]; 
        if (name.length > 2) { showErrorPopup(); return; }

        gymnastTable.setItems(DatabaseConnector.getGymnasts(fname, lname, age, clsname));
    }

    public void addAllToPrint(){
        printSet.clear();
        for (Gymnast g: gymnastTable.getItems())
            printSet.add(g.getId());

        printListText.set("Show List to Print (" + printSet.size() + ")");
        System.out.println(printSet.size());
    }

    public void openGymnastEditor(){
        Gymnast g = gymnastTable.getSelectionModel().getSelectedItem();
        int gymnastId = 0;
        if (g != null) 
            gymnastId = g.getId();
        else
            return;
        System.out.println("Double-clicked on: " + gymnastId); 

        try {
            // Load the FXML for the error dialog
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/gymnast_progress.fxml"));
            Parent root = loader.load();
            //root.getStylesheets().add(getClass().getResource("/org/example/add_gymnast_styles.css").toExternalForm());

            // Create a new Stage for the error dialog
            Stage gymnastProgressStage = new Stage();
            gymnastProgressStage.setTitle("Track Gymnast Progress");
            gymnastProgressStage.initModality(Modality.APPLICATION_MODAL); // Block interaction with other windows
            gymnastProgressStage.setScene(new Scene(root));

            // Show the popup
            gymnastProgressStage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Handle any loading errors
        }
    }

    public void openGymnastCreator(){
        try {
            // Load the FXML for the error dialog
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/add_gymnast.fxml"));
            Parent root = loader.load();
            root.getStylesheets().add(getClass().getResource("/org/example/add_gymnast_styles.css").toExternalForm());

            // Create a new Stage for the error dialog
            Stage addGymnastStage = new Stage();
            addGymnastStage.setTitle("Add a New Gymnast");
            addGymnastStage.initModality(Modality.APPLICATION_MODAL); // Block interaction with other windows
            addGymnastStage.setScene(new Scene(root));

            // Show the popup
            addGymnastStage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Handle any loading errors
        }
    }

    public void showErrorPopup() {
        try {
            // Load the FXML for the error dialog
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/error_message.fxml"));
            Parent root = loader.load();

            // Create a new Stage for the error dialog
            Stage errorStage = new Stage();
            errorStage.setTitle("Error");
            errorStage.initModality(Modality.APPLICATION_MODAL); // Block interaction with other windows
            errorStage.setScene(new Scene(root));

            // Show the popup
            errorStage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Handle any loading errors
        }
    }
}
