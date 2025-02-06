package org.helpers;

import java.sql.*;
import java.util.ArrayList;
import javafx.collections.*;
import org.objects.*;
import java.time.LocalDate;


public class DatabaseConnector {
    public static Connection connect() throws SQLException {

        Connection con = null;
        try{
            con = DriverManager.getConnection("jdbc:postgresql://pg-faf557a-skocicdavid-5428.b.aivencloud.com:17376/rainbow_boys?ssl=require&user=avnadmin&password=AVNS_PxjuOZ8Nk2Vz2TpjiLT");
        }catch(SQLException e){
            System.out.println("Unable to connect to the Database");
        }
	// TODO: update this to be a .env
        return con;
    }

    public static Gymnast getGymnast(int id){
        try {

            Connection con = DatabaseConnector.connect();
            PreparedStatement call = con.prepareStatement("SELECT * FROM get_gymnast(?)");
            call.setInt(1, id);

            ResultSet results = call.executeQuery();
            results.next();
            Gymnast g = new Gymnast(results);

            results.close();
            call.close();
            con.close();

            return g;

        } catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static ObservableList<Gymnast> getGymnasts(String firstName, String lastName, Integer age, String clsname){
        try {
            Connection con = DatabaseConnector.connect();
            PreparedStatement call = con.prepareStatement("SELECT * FROM get_gymnasts(?, ?, ?, ?)");
            
            call.setString(1, firstName); 
            call.setString(2, lastName); 
            if (age == null) call.setNull(3, Types.INTEGER); else call.setInt(3, age); 
            call.setString(4,clsname); 

            ResultSet results = call.executeQuery();
            ObservableList<Gymnast> resultList = FXCollections.observableArrayList(); 

            while (results.next())
                resultList.add(new Gymnast(results));

            results.close();
            call.close();
            con.close();

            return resultList;

        } catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    public static ObservableList<RainbowClass> getClassesForGymnast(int id){
        try {
            Connection con = DatabaseConnector.connect();
            PreparedStatement call = con.prepareStatement("SELECT * FROM get_classes_for_gymnast(?)");
            call.setInt(1, id);
            ResultSet results = call.executeQuery();
            ObservableList<RainbowClass> resultList = FXCollections.observableArrayList(); 

            while (results.next())
                resultList.add(new RainbowClass(results));

            results.close();
            call.close();
            con.close();

            return resultList;

        } catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    public static ObservableList<RainbowClass> getClasses(String name){
        try {
            Connection con = DatabaseConnector.connect();
            PreparedStatement call = con.prepareStatement("SELECT * FROM get_classes(?)");
            call.setString(1,name);
            ResultSet results = call.executeQuery();
            ObservableList<RainbowClass> resultList = FXCollections.observableArrayList(); 

            while (results.next())
                resultList.add(new RainbowClass(results));

            results.close();
            call.close();
            con.close();

            return resultList;

        } catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    public static void insert_gymnast(String fname, String lname, LocalDate birthdate, int class_id){
        try {
            Connection con = DatabaseConnector.connect();
            CallableStatement call = con.prepareCall("CALL insert_gymnast(?,?,?,?)");
            call.setString(1,fname);
            call.setString(2,lname);
            call.setDate(3,Date.valueOf(birthdate));
            call.setInt(4,class_id);
            call.executeUpdate();
            call.close();
            con.close();

        } catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    public static void updateGymnast(Gymnast g){
        try {
            Connection con = DatabaseConnector.connect();
            CallableStatement call = con.prepareCall("CALL update_gymnast(?,?,?,?,?,?,?)");
            call.setInt(1,g.getId());
            // sql dates use a different object type
            Date[][] sqlDates = new Date[g.getProgress().length][g.getProgress()[0].length];
            for (int r = 0; r < g.getProgress().length; r++){
                for (int c = 0; c < g.getProgress()[0].length; c++)
                    sqlDates[r][c] = (g.getProgress()[r][c] != null) ? Date.valueOf(g.getProgress()[r][c]) : null;
                call.setArray(r+2, con.createArrayOf("Date", sqlDates[r]));
            }

            call.executeUpdate();
            call.close();
            con.close();

        } catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    public static void deleteGymnastClasses(int gymnastId){
        try {
            Connection con = DatabaseConnector.connect();
            CallableStatement call = con.prepareCall("CALL delete_gymnast_classes(?)");
            call.setInt(1,gymnastId);
            call.executeUpdate();
            call.close();
            con.close();
        } catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    public static void insertGymnastClasses(int gymnastId, int classId){
        try {
            Connection con = DatabaseConnector.connect();
            CallableStatement call = con.prepareCall("CALL insert_gymnast_classes(?, ?)");
            call.setInt(1, gymnastId);
            call.setInt(2, classId);
            call.executeUpdate();
            call.close();
            con.close();
        } catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    public static void insertClass(String name){
        try {
            Connection con = DatabaseConnector.connect();
            CallableStatement call = con.prepareCall("CALL insert_class(?)");
            call.setString(1, name);
            call.executeUpdate();
            call.close();
            con.close();
        } catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    public static ObservableList<Coach> getCoaches(String fname, String lname){
        try {
            Connection con = DatabaseConnector.connect();
            PreparedStatement call = con.prepareStatement("SELECT * FROM get_coaches(?,?)");
            call.setString(1, fname);
            call.setString(2, lname);

            ResultSet results = call.executeQuery();
            ObservableList<Coach> resultList = FXCollections.observableArrayList(); 

            while (results.next())
                resultList.add(new Coach(results));

            call.close();
            con.close();

            return resultList;
        } catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    public static void insertCoach(String fname, String lname){
        try {
            Connection con = DatabaseConnector.connect();
            CallableStatement call = con.prepareCall("CALL insert_coach(?, ?)");
            call.setString(1, fname);
            call.setString(2, lname);
            call.executeUpdate();
            call.close();
            con.close();
        } catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    public static void deleteClassCoaches(int coachId){
        try {
            Connection con = DatabaseConnector.connect();
            CallableStatement call = con.prepareCall("CALL delete_class_coaches(?)");
            call.setInt(1, coachId);
            call.executeUpdate();
            call.close();
            con.close();
        } catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    public static void insertClassCoaches(int coachId, int classId){
        try {
            Connection con = DatabaseConnector.connect();
            CallableStatement call = con.prepareCall("CALL insert_class_coaches(?, ?)");
            call.setInt(1, coachId);
            call.setInt(2, classId);
            call.executeUpdate();
            call.close();
            con.close();
        } catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    public static ObservableList<RainbowClass> getClassCoaches(int coachId){
        try {
            Connection con = DatabaseConnector.connect();
            PreparedStatement call = con.prepareStatement("SELECT * FROM get_class_coaches(?)");
            call.setInt(1,coachId);
            ResultSet results = call.executeQuery();
            ObservableList<RainbowClass> resultList = FXCollections.observableArrayList(); 

            while (results.next())
                resultList.add(new RainbowClass(results));

            results.close();
            call.close();
            con.close();

            return resultList;

        } catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
