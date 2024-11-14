package org.objects;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.beans.property.*;

public class Gymnast {

    private final StringProperty firstName = new SimpleStringProperty();
    private final StringProperty lastName = new SimpleStringProperty();
    private LocalDate birthdate;
    private Group group;
    private final IntegerProperty id = new SimpleIntegerProperty();

    public Gymnast(int id){
		this.id.set(id);
    }
	public Gymnast(int id, String firstName, String lastName, Group group){
		this.id.set(id);
		this.firstName.set(firstName);
		this.lastName.set(lastName);
		this.group = group;
	}
	public String getFirstName(){
		return firstName.get();
	}
	public String getLastName(){
		return lastName.get();
	}

	public Group getGroup(){
		return this.group;
	}
    
    public boolean pullGymnastById(){
		try (Connection con = DatabaseConnector.connect()){
			try (PreparedStatement call = con.prepareStatement("SELECT * FROM get_gymnast(?)")){
				call.setInt(1, this.id.get());
			// "try with resources" to automatically close connections
				try(ResultSet results = call.executeQuery()){
					int count = 0;
					String firstName = "";
					String lastName = "";
					while (results.next()){
						firstName = results.getString("first_name");
						lastName = results.getString("last_name");
						count++;
					}
					if (count > 1)
						throw new Exception("Multiple of the same ID was found...");
					this.firstName.set(firstName);
					this.lastName.set(lastName);
					return true;
				}
			}
			catch(Exception e){
				e.printStackTrace();
				return false;
			}
		} catch (SQLException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
    }
	public static ArrayList<Gymnast> getGymnastsByName(String firstName, String lastName, String cname){
		try (Connection con = DatabaseConnector.connect()){
			try (PreparedStatement call = con.prepareStatement("SELECT * FROM get_gymnasts_by_name(?, ?, ?)")){
				call.setString(1, firstName);
				call.setString(2, lastName);
				call.setString(3, cname);
				try(ResultSet results = call.executeQuery()){
					ArrayList<Gymnast> resultList = new ArrayList<>();
					int count = 0;
					String fname = "";
					String lname = "";
					String className = "";
					int classId = -1;
					int gymnastId = -1;
					while (results.next()){
						fname = results.getString("first_name");
						lname = results.getString("last_name");
						className = results.getString("class_name");
						classId = results.getInt("class_id");
						gymnastId = results.getInt("gymnast_id");
						Group g = new Group(classId, className);
						Gymnast gy = new Gymnast(gymnastId, fname, lname, g);
						resultList.add(gy);
					}
					System.out.println(resultList.size());
					return resultList;
				}
			}
			catch(Exception e){
				e.printStackTrace();
				return null;
			}
		} catch (SQLException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
    }
	
}
