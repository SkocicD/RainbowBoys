package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.time.LocalDate;
import org.objects.*;

public class ProgressController {

    @FXML private GridPane skillsGrid;
    @FXML private Button cancelButton;
    @FXML private Label gymnastDetailsLabel;

    private static final int COLUMNS = 27;   // Checkboxes per row

    private CheckBox[][] checkBoxes = new CheckBox[HelperFunctions.EVENTS.length][COLUMNS];
    private LocalDate[][] progress;

    @FXML
    public void initialize() {
        for (int i = 0; i < HelperFunctions.EVENTS.length; i++){
            // Create a label for each row
            skillsGrid.add(new Label(HelperFunctions.EVENTS[i]), 0, i);

            // Create a container for the checkboxes (1px between them)
            HBox checkBoxRow = new HBox(1);
            for (int col = 0; col < COLUMNS; col++) {
                // wrappers for use in lambda
                final int row = i;
                final int column = col;
                CheckBox checkBox = new CheckBox();
                checkBox.setOnAction(event -> checkOff(row,column));
                checkBoxes[i][col] = checkBox;
                checkBoxRow.getChildren().add(checkBox);
            }
            skillsGrid.add(checkBoxRow, 1, i);
        }
        cancelButton.setOnAction(event->HelperFunctions.closeWindow(event.getSource()));
    }

    public void setGymnastInfo(int id, String name, int age) {
        gymnastDetailsLabel.setText(name + ", " + age + " years old");
        progress = DatabaseConnector.getProgress(id);
        for (int r = 0; r < 6; r++)
            for (int c = 0; c < COLUMNS; c++)
                if (progress[r][c] != null)
                    checkBoxes[r][c].setSelected(true);
        
    }


    private void checkOff(int row, int col){
        if (checkBoxes[row][col].isSelected())
            for (int c = col; c >= 0; c--)
                checkBoxes[row][c].setSelected(true);
        if (!checkBoxes[row][col].isSelected())
            for (int c = col; c < checkBoxes[0].length; c++)
                checkBoxes[row][c].setSelected(false);
    }

}
