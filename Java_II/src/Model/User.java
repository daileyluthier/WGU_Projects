/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author daileyluthier
 */
public class User {
    
    //primary key and unique identifier for user class
    private int userId;
    
    //other attributes of class
    public static String userName;
    private String password;
    private int active;
    
    //constructor
    public User() {}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getActive() {
        return this.active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    //creates a string representation for the views
    @Override
    public String toString() {
        return getUserName();
    }
    
    //validates credentials of the user
    public boolean validate() throws AssertionError {
        assert !this.userName.equals("");
        assert !this.password.equals("");
        
        return true;
    }
    
}
