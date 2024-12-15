package com.library.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/library_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection connection;

    private DbConnection() {
    }

    public static Connection getConnection() {
        try {
            if(connection != null && !connection.isClosed()) {
                return connection;
            }
            Class.forName("com.mysql.cj.jdbc.Driver");
            return connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
            return null;
        }
    }
}
