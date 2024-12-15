package com.library.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbConnection {
    private static String url;
    private static String user;
    private static String password;
    private static Connection connection;
    private static final Logger logger = Logger.getLogger(DbConnection.class.getName());

    private DbConnection() {
    }

    public static Connection getConnection() {
        try {
            if(connection != null && !connection.isClosed()) {
                return connection;
            }

            Properties props = new Properties();
            try (FileInputStream fis = new FileInputStream("config.properties")) {
                props.load(fis);
            }

            url = props.getProperty("db.url");
            user = props.getProperty("db.user");
            password = props.getProperty("db.password");

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (ClassNotFoundException | SQLException | IOException e) {
            logger.log(Level.SEVERE, "Error: ", e);
            return null;
        } 
    }
}
