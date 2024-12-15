package com.library.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.library.util.DbConnection;

public class Helper {

    public static int getEntityId(String entityName, String primaryColumnName) {
        String sql = "SELECT max(" + primaryColumnName + ") FROM " + entityName;

        try (Connection connection = DbConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) + 1;
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }

        return 1;
    }

    public static int getEntityId(String entityName) {
        String sql = "SELECT max(id) FROM " + entityName;

        try (Connection connection = DbConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) + 1;
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }

        return 1;
    }

}
