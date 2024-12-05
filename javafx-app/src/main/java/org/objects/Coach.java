package org.objects;

import java.util.ArrayList;
import java.sql.*;

public class Coach{

    private String name;
    private int id;


    public Coach (String name){
        this.name = name;
    }

    public String getName() { return name; }
}
