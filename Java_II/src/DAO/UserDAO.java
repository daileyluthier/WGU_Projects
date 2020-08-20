/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import static DAO.DBConnection.conn;
import Model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author daileyluthier
 */

//This class is the DAO object to communicate with the Users table in the database

public class UserDAO implements UserDAOInterface {

    //constructor 
    public UserDAO() {}
    
    private static User currentUser;
    
    public static User getCurrentUser() {
        return currentUser;
    }

    //log in query
    @Override
    public User logIn(String userName, String password) {
        String getUserQuery = "SELECT * FROM user WHERE userName = ? AND password = ?"; 
        User queryUser = new User();

        try{
            PreparedStatement statement = conn.prepareStatement(getUserQuery);
            statement.setString(1, userName);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()){
                queryUser.setUserId(resultSet.getInt("userId"));
                queryUser.setUserName(resultSet.getString("userName"));
                queryUser.setPassword(resultSet.getString("password"));
                queryUser.setUserId(resultSet.getInt("userId"));
                queryUser.setActive(resultSet.getInt("active"));
            } else {
                return null;
            }
        } catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        
        return queryUser;
    }
    
    //get a list of all users

    /**
     *
     * @param user
     * @return
     */
    @Override
    public ObservableList<User> getAllActiveUsers() {
        ObservableList<User> users = FXCollections.observableArrayList();
        String getCustomerQuery = "SELECT * FROM user WHERE active = 1"; 

        try {
            PreparedStatement statement = conn.prepareStatement(getCustomerQuery);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                User queryUser = new User();
                queryUser.setUserId(resultSet.getInt("userId"));
                queryUser.setUserName(resultSet.getString("userName"));
                queryUser.setPassword(resultSet.getString("password"));
                queryUser.setActive(resultSet.getInt("active"));

                users.add(queryUser);
            }
        } catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        
        return users;
    }
    
    @Override
    public ObservableList<User> getActiveUsers() {
        ObservableList<User> users = FXCollections.observableArrayList();
        String getCustomerQuery = "SELECT * FROM user WHERE active = 1"; 

        try {
            PreparedStatement statement = conn.prepareStatement(getCustomerQuery);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                User queryUser = new User();
                queryUser.setUserId(resultSet.getInt("userId"));
                queryUser.setUserName(resultSet.getString("userName"));
                queryUser.setPassword(resultSet.getString("password"));
                queryUser.setActive(resultSet.getInt("active"));

                users.add(queryUser);
            }
        } catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        
        return users;
    }
    
    @Override
    public User getUserFromId(int userId) {
        String getUserFromIdQuery = "SELECT * FROM user WHERE userId=?";
        User user = new User();
        
        try {
            PreparedStatement stmt = conn.prepareStatement(getUserFromIdQuery);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                user.setUserId(rs.getInt("userId"));
                user.setUserName(rs.getString("userName"));
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }
    
}
