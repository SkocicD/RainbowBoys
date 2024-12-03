package org.objects;

import java.sql.*;
import java.util.ArrayList;
import org.objects.Gymnast;


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

    public static ArrayList<Gymnast> getGymnastsByFullName(String firstName, String lastName, String clsname){
        try {
            Connection con = DatabaseConnector.connect();
            PreparedStatement call = con.prepareStatement("SELECT * FROM get_gymnasts_by_full_name(?, ?, ?)");
            call.setString(1, firstName);
            call.setString(2, lastName);
            call.setString(3, clsname);

            ResultSet results = call.executeQuery();
            ArrayList<Gymnast> resultList = new ArrayList<>();

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

    public static ArrayList<Gymnast> getGymnastsBySingleName(String name, String clsname){
        try {
            Connection con = DatabaseConnector.connect();
            PreparedStatement call = con.prepareStatement("SELECT * FROM get_gymnasts_by_single_name(?, ?)");
            call.setString(1, name);
            call.setString(2, clsname);

            ResultSet results = call.executeQuery();
            ArrayList<Gymnast> resultList = new ArrayList<>();

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
}
