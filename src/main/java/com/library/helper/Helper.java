package com.library.helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.library.util.DbConnection;

public class Helper {

    public static int getEntityId(String entityName, String primaryColumnName){
        try {
            PreparedStatement preparedStatement = DbConnection.getConnection().prepareStatement("SELECT max(?) FROM ?");
            preparedStatement.setString(1, primaryColumnName);
            preparedStatement.setString(2, entityName);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.getInt(1) + 1;
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return 1;
        }
    }


    public static int getEntityId(String entityName){
        try {
            PreparedStatement preparedStatement = DbConnection.getConnection().prepareStatement("SELECT max(id) FROM ?");
            preparedStatement.setString(1, entityName);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.getInt(1) + 1;
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return 1;
        }
    }
}
