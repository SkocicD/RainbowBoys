package org.objects;

import javafx.beans.property.*;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


// TODO: change this classname
public class Group{

	private ArrayList<Coach> coaches;
	private final StringProperty name = new SimpleStringProperty();
	private final IntegerProperty id = new SimpleIntegerProperty();
	private ArrayList<DayTime> classTimes;


	public Group (int id, String name){
		this.name.set(name);
		this.id.set(id);
	}
	public Group (String name){
		this.name.set(name);
	}

	public String getName(){
		return name.get();
	}
}
