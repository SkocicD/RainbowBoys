package org.objects;

import org.helpers.*;

import java.sql.*;
import java.time.*;
import java.util.ArrayList;
import javafx.beans.property.*;
import java.time.LocalDate;

public class Gymnast{ 

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty firstName = new SimpleStringProperty();
    private final StringProperty lastName = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> birthdate = new SimpleObjectProperty<>();
    private final IntegerProperty age = new SimpleIntegerProperty();
    private LocalDate[][] progress = new LocalDate[6][Helpers.NUM_SKILLS_TIM];

    public Gymnast(Gymnast g){
        this.id.set(g.getId());
        this.firstName.set(g.getFirstName());
        this.lastName.set(g.getLastName());
        this.birthdate.set(g.getBirthdate());
        this.age.set(g.getAge());
        for (int r = 0; r < this.progress.length; r++)
            for (int c = 0; c < this.progress[0].length; c++)
                this.progress[r][c] = g.getProgress()[r][c];
    }
    
    

    public Gymnast (ResultSet r){
        try {
            if (Helpers.inResultSet(r, "id")) this.id.set(r.getInt("id"));
            if (Helpers.inResultSet(r,"first_name")) this.firstName.set(r.getString("first_name"));
            if (Helpers.inResultSet(r, "last_name")) this.lastName.set(r.getString("last_name"));
            if (Helpers.inResultSet(r, "birthdate")) {
                this.birthdate.set(r.getDate("birthdate").toLocalDate());
                this.age.set(Period.between(this.birthdate.get(),LocalDate.now()).getYears());
            }
            for (int row = 0; row < Helpers.EVENT_COLUMNS.length; row++){
                if (Helpers.inResultSet(r, Helpers.EVENT_COLUMNS[row])) {
                    Date[] daterow = (Date[]) r.getArray(Helpers.EVENT_COLUMNS[row]).getArray();
                    for (int col = 0; col < daterow.length; col++)
                        progress[row][col] = (daterow[col]!=null) ? daterow[col].toLocalDate() : null;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean inResultSet(ResultSet r, String columnname){
        try {
            ResultSetMetaData metaData = r.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                if (metaData.getColumnName(i).equalsIgnoreCase(columnname)) {
                    return true;
                }
            }
            return false;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public String getFirstName(){ return firstName.get(); }
    public StringProperty firstNameProperty(){ return firstName; }

    public String getLastName(){ return lastName.get(); }
    public StringProperty lastNameProperty(){ return lastName; }

    public int getId(){ return id.get(); }
    public IntegerProperty idProperty(){ return id; }

    public LocalDate getBirthdate(){ return birthdate.get(); }
    public ObjectProperty birthdateProperty(){ return birthdate; }

    public int getAge(){ return age.get(); } 
    public IntegerProperty ageProperty(){ return age; }

    public LocalDate[][] getProgress() { return progress; }
}
