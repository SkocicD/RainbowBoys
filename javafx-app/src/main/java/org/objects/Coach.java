package org.objects;

import java.util.ArrayList;
import java.sql.*;

public class Coach{

	private String name;
	private ArrayList<Class> classes;
	private int id;


	public Coach(int id){
		this.id = id;
		pullById();
	}
	public Coach(int id, String name){
		this.id = id;
		this.name = name;
		pullById();
	}
	public Coach (String name){
		this.name = name;
	}

	public String getName() { return name; }
	public void setName(String name) {
		this.name = name; 
		saveToDatabase();
	}

	public boolean pullById(){
		// "try with resources" to automatically close connections
		try (Connection con = DatabaseConnector.connect()){
			try (PreparedStatement call = con.prepareStatement("SELECT * FROM get_coach(?)"))){
				call.setInt(1, this.id);
				// "try with resources" to automatically close connections
				try(ResultSet results = call.executeQuery()){
					int count = 0;
					String name = "";
					while (results.next()){
						name = results.getString("first_name") + " " + results.getString("last_name");
						count++;
					}
					if (count > 1)
						throw new Exception("Multiple of the same ID was found...");
					this.name = name;

					return true;
				}
				catch(Exception e){
					e.printStackTrace();
					return false;
				}
			}
			try (PreparedStatement call = con.prepareStatement("SELECT * FROM get_classes_for_coach(?)"))){

				call.setInt(1, this.id);
				// "try with resources" to automatically close connections
				try(ResultSet results = call.executeQuery()){
					int count = 0;
					while (results.next()){

						count++;
					}
					if (count > 1)
						throw new Exception("Multiple of the same ID was found...");
					this.name = name;

					return true;
				}
				catch(Exception e){
					e.printStackTrace();
					return false;
				}
		}
		catch(SQLException e){
			System.out.println("Something went wrong searching for coaches");
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public boolean saveToDatabase(){
		try (Connection con = DatabaseConnector.connect()) {
			PreparedStatement call = con.prepareCall("call update_coach(?, ?)");
			call.setString(1, this.name);
			call.setInt(2, 60);
			call.execute();
			System.out.println("Coach updated successfully");
			call.close();
			return true;
		}
		catch (SQLException e){
			System.out.println("Something went wrong saving the coach");
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
}
