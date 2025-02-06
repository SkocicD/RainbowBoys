package org.objects;

import org.helpers.*;

import java.util.ArrayList;
import java.sql.*;
import javafx.beans.property.*;

public class Coach{

    private final StringProperty firstName = new SimpleStringProperty();
    private final StringProperty lastName = new SimpleStringProperty();
    private final IntegerProperty id = new SimpleIntegerProperty();


    public Coach(Coach c){
        this.id.set(c.getId());
        this.firstName.set(c.getFirstName());
        this.lastName.set(c.getLastName());
    }

    public Coach (ResultSet r){
        try{
            if (Helpers.inResultSet(r, "id")) this.id.set(r.getInt("id"));
            if (Helpers.inResultSet(r, "first_name")) this.firstName.set(r.getString("first_name"));
            if (Helpers.inResultSet(r, "last_name")) this.lastName.set(r.getString("last_name"));
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public int getId() { return id.get(); }
    public String getLastName() { return firstName.get(); }
    public String getFirstName() { return lastName.get(); }
    public StringProperty firstNameProperty() { return firstName; }
    public StringProperty lastNameProperty() { return lastName; }
}
