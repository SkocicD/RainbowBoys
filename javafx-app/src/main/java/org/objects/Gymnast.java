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
    public int getId(){ return id.get(); }

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
	public static ArrayList<Gymnast> getGymnastsByFullName(String firstName, String lastName, String cname){
		try (Connection con = DatabaseConnector.connect()){
			try (PreparedStatement call = con.prepareStatement("SELECT * FROM get_gymnasts_by_full_name(?, ?, ?)")){
				call.setString(1, firstName);
				call.setString(2, lastName);
				call.setString(3, cname);
				try(ResultSet results = call.executeQuery()){
					ArrayList<Gymnast> resultList = new ArrayList<>();
					while (results.next()){
						Group g = new Group(results.getInt("class_id"), results.getString("class_name"));
						Gymnast gy = new Gymnast(results.getInt("gymnast_id"),results.getString("first_name"),results.getString("last_name"), g);
						resultList.add(gy);
					}
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

    public static ArrayList<Gymnast> getGymnastsBySingleName(String name, String cname){
		try (Connection con = DatabaseConnector.connect()){
			try (PreparedStatement call = con.prepareStatement("SELECT * FROM get_gymnasts_by_single_name(?, ?)")){
				call.setString(1, name);
				call.setString(2, cname);
				try(ResultSet results = call.executeQuery()){
					ArrayList<Gymnast> resultList = new ArrayList<>();
					while (results.next()){
						Group g = new Group(results.getInt("class_id"), results.getString("class_name"));
						Gymnast gy = new Gymnast(results.getInt("gymnast_id"),results.getString("first_name"),results.getString("last_name"), g);
						resultList.add(gy);
					}
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
