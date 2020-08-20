/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.User;
import javafx.collections.ObservableList;

/**
 *
 * @author daileyluthier
 */
public interface UserDAOInterface {
    public ObservableList<User> getAllActiveUsers();
    
    public ObservableList<User> getActiveUsers();
    
    public User logIn(String username, String password);
    
    public User getUserFromId(int userId);
    
}
