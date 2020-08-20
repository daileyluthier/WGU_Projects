/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import static DAO.DBConnection.conn;
import Model.Address;
import Model.Customer;
import static View_Controller.LogInScreenController.loggedUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author daileyluthier
 */
public class CustomerDAO implements CustomerDAOInterface {
    
    private final static Connection conn = DBConnection.conn;
    
    public CustomerDAO() {}
    
    // queries database for customers by id

    /**
     *
     * @param customerId
     * @return
     */
    @Override
    public Customer getCustomer(int customerId) {
        String getCustomersQuery = "SELECT * FROM customer WHERE customerId = ?";
        Customer inquiredCustomer = new Customer();
        
        try {
            PreparedStatement statement = conn.prepareStatement(getCustomersQuery);
            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                inquiredCustomer.setCustomerName(resultSet.getString("customerName"));
                inquiredCustomer.setCustomerId(resultSet.getInt("customerId"));
                inquiredCustomer.setActive(resultSet.getInt("active"));

                AddressDAOInterface address = new AddressDAO();
                inquiredCustomer.setAddress(address.getAddress(resultSet.getInt("addressId")));
                
            } else {
                return null;
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    return inquiredCustomer;
    }
    
    // queries database for all active customers
    /**
     *
     * @return
     */
    @Override
    public ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        String getCustomerByIdQuery = "SELECT * FROM customer WHERE active = 1";
        
        try {
            PreparedStatement statement = conn.prepareStatement(getCustomerByIdQuery);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Customer getCustomerQuery = new Customer();
                getCustomerQuery.setCustomerId(resultSet.getInt("customerId"));
                getCustomerQuery.setCustomerName(resultSet.getString("customerName"));
                getCustomerQuery.setActive(resultSet.getInt("active"));
                
                AddressDAOInterface address = new AddressDAO();
                getCustomerQuery.setAddress(address.getAddress(resultSet.getInt("addressId")));
                
                customers.add(getCustomerQuery);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return customers;
    }
    
    //queries database for the max customer Id, good for sorting and finding when the next customer id is available
    private static int getMaxCustomerId() {
        int maxCustomerId = 0;
        String maxCustomerIdQuery = "SELECT MAX(customerId) FROM customer";
        
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(maxCustomerIdQuery);
            
            if (resultSet.next()) {
                maxCustomerId = resultSet.getInt(1);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return maxCustomerId + 1;
    }
    
    // adds a new customer to the database
    /**
     *
     * @param customer
     * @return
     */
    @Override
    public int addCustomer(Customer customer) {
        String addCustomerQuery = String.join(" ", 
            "INSERT INTO customer (customerId, customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy)",
            "VALUES (?, ?, ?, 1, NOW(), ?, NOW(), ?)");
        
        int customerId = getMaxCustomerId();
        try {
            PreparedStatement statement = conn.prepareStatement(addCustomerQuery);
            statement.setInt(1, customerId);
            statement.setString(2, customer.getCustomerName());
            statement.setInt(3, customer.getAddress().getAddressId());
            statement.setString(4, loggedUser.getUserName());
            statement.setString(5, loggedUser.getUserName());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return customerId;
    }
    
    //updates existing customer in database
    /**
     *
     * @param customer
     */
    @Override
    public void updateCustomer(Customer customer) {
        String updateCustomerQuery = String.join(" ", 
            "UPDATE customer",
            "SET customerName=?, addressId=?, lastUpdate=NOW(), lastUpdateBy=?",
            "WHERE customerId = ?");
        
        try {
            PreparedStatement statement = conn.prepareStatement(updateCustomerQuery);
            statement.setString(1, customer.getCustomerName());
            statement.setInt(2, customer.getAddress().getAddressId());
            statement.setString(3, loggedUser.getUserName());
            statement.setInt(4, customer.getCustomerId());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //deletes customer
    @Override
    public  void inactiveCustomer(Customer customer) {
        String inactiveCustomerQuery = "UPDATE customer SET active = 0 WHERE customerId = ?";
        
        try {
            PreparedStatement statement = conn.prepareStatement(inactiveCustomerQuery);
            statement.setInt(1, customer.getCustomerId());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}