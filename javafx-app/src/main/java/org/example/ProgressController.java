package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class ProgressController {

    @FXML private GridPane skillsGrid;

    private static String[] EVENTS = {"Floor", "Pommel", "Rings", "Vault", "Parallel Bars", "High Bar"};        // Number of rows
    private static final int COLUMNS = 21;   // Checkboxes per row

    private CheckBox[][] checkBoxes = new CheckBox[EVENTS.length][COLUMNS];

    @FXML
    public void initialize() {
        for (int i = 0; i < EVENTS.length; i++){
            // Create a label for each row
            skillsGrid.add(new Label(EVENTS[i]), 0, i);

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
