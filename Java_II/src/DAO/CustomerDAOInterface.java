/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Customer;
import javafx.collections.ObservableList;

/**
 *
 * @author daileyluthier
 */
public interface CustomerDAOInterface {

    public int addCustomer(Customer customer);
        
    public ObservableList<Customer> getAllCustomers();
        
    public Customer getCustomer(int customerId);
    
    public void inactiveCustomer(Customer customer);
    
    public void updateCustomer(Customer customer);
    
}
