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

public class ErrorMessageController implements Initializable{

    @FXML public Button okButton;
    @FXML public Label messageLabel;

    public void initialize(URL location, ResourceBundle resources){
        
    }

    public void setMessage(String message){ 
        messageLabel.setText(message);

    }

    public void closeWindow(){
        HelperFunctions.closeWindow(okButton);
    }


}
