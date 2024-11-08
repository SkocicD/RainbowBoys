package org.example;

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

public class CoachScreenController implements Initializable{

    @FXML
    public VBox center;
    @FXML
    public HBox topHBox;
    @FXML
    public HBox middleHBox;
    @FXML
    public TableView coachTable;

    public void initialize(URL location, ResourceBundle resources){
        //firstHBox.prefHeightProperty().bind(center.heightProperty().multiply(0.25));
        topHBox.spacingProperty().bind(center.widthProperty().multiply(.1));
        coachTable.prefWidthProperty().bind(center.widthProperty().multiply(.8));
    }

}
