package org.controllers;


import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.beans.value.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainController implements Initializable{
    

    @FXML
    public ToggleButton gymnastHeaderButton;
    @FXML
    public ToggleButton classHeaderButton;
    @FXML
    public ToggleButton coachHeaderButton;
    public ToggleGroup headerToggle;
    @FXML
    private StackPane screenContainer;
    @FXML
    public HBox headerBox;
    private HashMap<String, Node> screens;

    private Parent gymnastScreen;
    private Parent coachScreen;
    private Parent classScreen;


    public void initialize(URL location, ResourceBundle resources){
        headerToggle = new ToggleGroup();
        gymnastHeaderButton.setToggleGroup(headerToggle);
        classHeaderButton.setToggleGroup(headerToggle);
        coachHeaderButton.setToggleGroup(headerToggle);

        gymnastHeaderButton.setSelected(true);

        screens = new HashMap<>();

        try{
            gymnastScreen = FXMLLoader.load(getClass().getResource("/org/fxml/gymnast_screen.fxml"));
            coachScreen = FXMLLoader.load(getClass().getResource("/org/fxml/coach_screen.fxml"));
            classScreen = FXMLLoader.load(getClass().getResource("/org/fxml/class_screen.fxml"));
            screens.put("gymnast", gymnastScreen);
            screens.put("coach", coachScreen);
            screens.put("class", classScreen);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        loadScreen("gymnast");
        headerToggle.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

            public void changed(ObservableValue<? extends Toggle> ov,
                    Toggle toggle, Toggle new_toggle) {
                if (new_toggle == null) {
                    toggle.setSelected(true);
                }
            }
        });
        }
    
    public HBox getTopBox(){
        return headerBox;
    }
    
    @FXML
    public void showGymnastsTab(){
        // if the screen is already on the gymnast screen do nothing
        if (screenContainer.getChildren().get(0) == gymnastScreen) return;
        loadScreen("gymnast");
        System.out.println("gymnast");
    }
    @FXML
    public void showClassesTab(){
        // if the screen is already on the classes screen do nothing
        if (screenContainer.getChildren().get(0) == classScreen) return;
        loadScreen("class");
        System.out.println("classes");
    }
    @FXML
    public void showCoachesTab(){
        // if the screen is already on the coaches screen do nothing
        if (screenContainer.getChildren().get(0) == coachScreen) return;
        loadScreen("coach");
        System.out.println("coaches");
    }

    private void loadScreen(String screen){
        screenContainer.getChildren().setAll(screens.get(screen));
    }
}
