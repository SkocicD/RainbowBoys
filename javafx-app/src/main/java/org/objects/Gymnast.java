package org.objects;

import java.sql.*;
import java.time.*;
import java.util.ArrayList;
import javafx.beans.property.*;

public class Gymnast {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty firstName = new SimpleStringProperty();
    private final StringProperty lastName = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> birthdate = new SimpleObjectProperty<>();
    private final IntegerProperty age = new SimpleIntegerProperty();

    public Gymnast(int id, String firstName, String lastName, LocalDate birthdate){
        this.id.set(id);
        this.firstName.set(firstName);
        this.lastName.set(lastName);
        this.birthdate.set(birthdate);
        this.age.set(Period.between(this.birthdate.get(),LocalDate.now()).getYears());
    }

    public Gymnast (ResultSet r){
        try {
            this.id.set(r.getInt("id"));
            this.firstName.set(r.getString("first_name"));
            this.lastName.set(r.getString("last_name"));
            this.birthdate.set(r.getDate("birthdate").toLocalDate());
            this.age.set(Period.between(this.birthdate.get(),LocalDate.now()).getYears());
        }
        catch(Exception e){
            System.out.println("Unable to create a gymnast from the result set");
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
}
