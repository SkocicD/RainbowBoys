package org.objects;

import javafx.beans.property.*;

import java.util.ArrayList;
import java.sql.*;

public class RainbowClass{

    private final StringProperty name = new SimpleStringProperty();
    private final IntegerProperty id = new SimpleIntegerProperty();


    public RainbowClass (ResultSet r){
        try {
            this.id.set(r.getInt("id"));
            this.name.set(r.getString("name"));
        } catch(Exception e){
            e.printStackTrace();
            System.out.println("Unable to create a class from the result set");
        }
    }

    public String getName(){ return name.get(); }
    public StringProperty getNameProperty(){ return name; }
    public String toString(){ return name.get(); }
    public int getId(){ return id.get(); }
}
