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

public class ClassScreenController implements Initializable{

    @FXML public VBox center;
    @FXML public HBox topHBox;
    @FXML public HBox middleHBox;
    @FXML public ListView classList;
    @FXML public TextField nameField;

    public void initialize(URL location, ResourceBundle resources){
        //firstHBox.prefHeightProperty().bind(center.heightProperty().multiply(0.25));
        topHBox.spacingProperty().bind(center.widthProperty().multiply(.1));
        classList.prefWidthProperty().bind(center.widthProperty().multiply(.8));
        classList.setItems(DatabaseConnector.getClasses(null));
    }

    public void searchDB(){
        classList.getItems().clear();
        classList.setItems(DatabaseConnector.getClasses(nameField.getText()));
    }

    public void openAddClassWindow(){
        HelperFunctions.openWindow("/org/example/add_class.fxml", "Add a New Class");
    }

}
