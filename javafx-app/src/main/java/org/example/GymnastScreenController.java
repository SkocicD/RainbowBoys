package org.example;

import org.example.MainController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

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

public class GymnastScreenController implements Initializable{
    @FXML
    public VBox center;
    @FXML
    public HBox topHBox;
    @FXML
    public HBox middleHBox;
    @FXML
    public TableView gymnastTable;
    @FXML
    public TextField nameField;

    private final StringProperty nameFieldText = new SimpleStringProperty();

    public void initialize(URL location, ResourceBundle resources){
        //firstHBox.prefHeightProperty().bind(center.heightProperty().multiply(0.25));
        topHBox.spacingProperty().bind(center.widthProperty().multiply(.1));
        gymnastTable.prefWidthProperty().bind(center.widthProperty().multiply(.8));
        System.out.println(MainController.USER);
        nameFieldText.bindBidirectional(nameField.textProperty());
    }

    public void searchDB(){
        System.out.println(nameFieldText);
    }

}
