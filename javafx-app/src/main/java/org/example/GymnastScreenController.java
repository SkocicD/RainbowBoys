package org.example;

import org.objects.Gymnast;

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

import java.util.ArrayList;
import java.util.HashSet;

public class GymnastScreenController implements Initializable{
    @FXML public VBox center;
    @FXML public HBox topHBox;
    @FXML public HBox middleHBox;
    @FXML public TableView<Gymnast> gymnastTable;
    @FXML public TextField nameField;
    @FXML public ComboBox classField;
    @FXML public TableColumn<Gymnast, String> firstNameColumn;
    @FXML public TableColumn<Gymnast, String> lastNameColumn;
    @FXML public TableColumn<Gymnast, String> classNameColumn;
    @FXML public Button printButton;
    @FXML public Button showPrintListButton;

    private final StringProperty nameFieldText = new SimpleStringProperty();
    private final StringProperty classFieldText = new SimpleStringProperty();
    private final StringProperty printListText = new SimpleStringProperty("Show List to Print (0)");

    private HashSet<Integer> printSet = new HashSet<>();

    public void initialize(URL location, ResourceBundle resources){
        topHBox.spacingProperty().bind(center.widthProperty().multiply(.1));
        gymnastTable.prefWidthProperty().bind(center.widthProperty().multiply(.8));
        showPrintListButton.textProperty().bind(printListText);
        nameFieldText.bindBidirectional(nameField.textProperty());
        ObservableList<String> comboBoxOptions = FXCollections.observableArrayList(
            "class 1",
            "class 2"
        );
        classFieldText.bindBidirectional(classField.valueProperty());
        classField.setItems(comboBoxOptions);

        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        classNameColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getGroup().getName());
        });
        gymnastTable.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);
        searchDB();
    }

    public void searchDB(){
        String clsname = classFieldText.get();
        if (clsname == null)
            clsname = "";
        String name[] = nameFieldText.get().split(" ");
        
        ArrayList<Gymnast> gs = null;
        if (name.length == 1)
            gs = Gymnast.getGymnastsBySingleName(name[0], clsname);
        else if (name.length == 2)
            gs = Gymnast.getGymnastsByFullName(name[0], name[1], clsname);
        
        if (gs != null){
            gymnastTable.getItems().clear();
            for (Gymnast g:gs)
                gymnastTable.getItems().add(g);
        }
    }

    public void addSelectedGymnastsToPrint(){
        for (Gymnast g: gymnastTable.getSelectionModel().getSelectedItems()){
            printSet.add(g.getId());
        }
        printListText.set("Show List to Print (" + printSet.size() + ")");
        System.out.println(printSet.size());
    }

    public void selectAllShown(){
        gymnastTable.getSelectionModel().selectAll();
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
