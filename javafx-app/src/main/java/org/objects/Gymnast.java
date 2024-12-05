package org.objects;

import java.sql.*;
import java.time.*;
import java.util.ArrayList;
import javafx.beans.property.*;
import java.time.LocalDate;

public class Gymnast {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty firstName = new SimpleStringProperty();
    private final StringProperty lastName = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> birthdate = new SimpleObjectProperty<>();
    private final IntegerProperty age = new SimpleIntegerProperty();
    private LocalDate[][] progress = new LocalDate[6][27];

    public Gymnast(int id, String firstName, String lastName, LocalDate birthdate){
        this.id.set(id);
        this.firstName.set(firstName);
        this.lastName.set(lastName);
        this.birthdate.set(birthdate);
        this.age.set(Period.between(this.birthdate.get(),LocalDate.now()).getYears());
    }
    
    

    public Gymnast (ResultSet r){
        try {
            if (inResultSet(r, "id")) {
                this.id.set(r.getInt("id"));
                this.progress = DatabaseConnector.getProgress(this.id.get());
            }
            if (inResultSet(r,"first_name")) this.firstName.set(r.getString("first_name"));
            if (inResultSet(r, "last_name")) this.lastName.set(r.getString("last_name"));
            if (inResultSet(r, "birthdate")) {
                this.birthdate.set(r.getDate("birthdate").toLocalDate());
                this.age.set(Period.between(this.birthdate.get(),LocalDate.now()).getYears());
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
