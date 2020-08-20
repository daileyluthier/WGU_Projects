
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

/**
 *
 * @author michaeldailey
 */


import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author michaeldailey
 */
public class DBConnection {
    //JDBC URL parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//3.227.166.251/U05Fl9";
    private static final String jdbcURL = protocol + vendorName + ipAddress;
    
    //Driver and Connection Interface reference
    private static final String MYSQLJDBCDriver = "com.mysql.jdbc.Driver";
    static Connection conn;
    
    private static final String username = "U05Fl9"; //username
    private static final String password = "53688485961"; //password
    
    public static void startConnection() throws ClassNotFoundException, SQLException, Exception
    {  
        try 
        {
            Class.forName(MYSQLJDBCDriver);
            conn = (Connection)DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("The connection was successful!!");
        } 
        catch (ClassNotFoundException e) 
        {
            System.out.println("Class not found" + e.getMessage());
        }
        catch (SQLException e) 
        {
            System.out.println("SQLException: " + e.getMessage()); 
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }

    }
    
    public static void closeConnection() throws ClassNotFoundException, SQLException, Exception
    {
        try
        {
            conn.close();
            System.out.println("Connection Closed");
        }
        catch (SQLException e)
        {
            System.out.print("SQLException: " + e.getMessage());
        }
    }
    
    public static Connection getConnection() {
        return conn;
    }
}