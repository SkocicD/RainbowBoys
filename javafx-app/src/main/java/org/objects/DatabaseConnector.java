package org.objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    public static Connection connect() throws SQLException {

	// TODO: update this to be a .env

        String url = "jdbc:postgresql://localhost:5432/rainbow_boys";
        String user = "davidskocic";
        String password = "Me$merize";
        
        return DriverManager.getConnection(url, user, password);
    }
}
