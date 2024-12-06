package org.objects;

import java.sql.*;
import java.util.ArrayList;
import javafx.collections.*;
import org.objects.*;
import java.time.LocalDate;
import org.example.HelperFunctions;


public class DatabaseConnector {
    public static Connection connect() throws SQLException {

	// TODO: update this to be a .env

        String url = "jdbc:postgresql://localhost:5432/rainbow_boys";
        String user = "davidskocic";
        String password = "Me$merize";
        
        return DriverManager.getConnection(url, user, password);
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
    public static LocalDate[][] getProgress(int id){
        try {
            Connection con = DatabaseConnector.connect();
            PreparedStatement call = con.prepareStatement("SELECT * FROM get_gymnast(?)");
            call.setInt(1,id);
            ResultSet results = call.executeQuery();
            LocalDate[][] dates = new LocalDate[6][27];

            
            while (results.next()){
                for (int i = 0; i < HelperFunctions.EVENT_COLUMNS.length; i++){
                    Date[] daterow = (Date[]) results.getArray(HelperFunctions.EVENT_COLUMNS[i]).getArray();
                    for (int j=0; j < daterow.length; j++)
                        dates[i][j] = (daterow[j]!=null) ? daterow[j].toLocalDate() : null;
                }
            }

            results.close();
            call.close();
            con.close();

            return dates;

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
    public static void updateGymnast(int id, LocalDate[][] progress){
        try {
            Connection con = DatabaseConnector.connect();
            CallableStatement call = con.prepareCall("CALL update_gymnast(?,?,?,?,?,?,?)");
            call.setInt(1,id);
            // sql dates use a different object type
            Date[][] sqlDates = new Date[progress.length][progress[0].length];
            for (int r = 0; r < progress.length; r++){
                for (int c = 0; c < progress[0].length; c++)
                    sqlDates[r][c] = (progress[r][c] != null) ? Date.valueOf(progress[r][c]) : null;
                call.setArray(r+2, con.createArrayOf("Date", sqlDates[r]));
            }

            call.executeUpdate();
            call.close();
            con.close();
            System.out.println("Gymnast Successfully Updated");

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
            CallableStatement call = con.prepareCall("CALL insert_gymnast_classe(?, ?)");
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
}
