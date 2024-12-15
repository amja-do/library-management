package com.library.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {
    private static String url;
    private static String user;
    private static String password;
    private static Connection connection;

    private DbConnection() {
    }

    public static Connection getConnection() {
        try {
            if(connection != null && !connection.isClosed()) {
                return connection;
            }

            Properties props = new Properties();
            props.load(new FileInputStream("config.properties"));

            url = props.getProperty("db.url");
            user = props.getProperty("db.user");
            password = props.getProperty("db.password");

            Class.forName("com.mysql.cj.jdbc.Driver");
            return connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException | IOException e) {
            System.out.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
            return null;
        }
    }
}
