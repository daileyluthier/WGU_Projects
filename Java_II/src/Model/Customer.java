/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Exceptions.InvalidCustomerException;
import Exceptions.InvalidAddressException;
import Exceptions.InvalidCityException;
import Exceptions.InvalidCountryException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author daileyluthier
 */
public final class Customer {
    //primary / unique identifier for Customer Class
    private int customerId;
    
    //other attributes
    private String customerName;
    public int active;
    public StringProperty customerNameProperty = new SimpleStringProperty((String) customerName);
    public int customerAddressId;
    
    //reference to address class for customer
    public Address address;
    
    //getters and setters of customer Id
    public int getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    //getters and setters of active Status
    public int getActive() {
        return this.active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    //getters and setters of customer name
    public String getCustomerName() {
        return this.customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public StringProperty customerNameProperty() {
        return customerNameProperty;
    }
    
    //getters and setters of customer addressId
    public int getCustomerAddressId() {
        return this.customerAddressId;
    }

    public void setCustomerAddressId(int customerAddressId) {
        this.customerAddressId = customerAddressId;
    }
    
    //getter and setter for Address
    public Address getAddress() {
        return this.address;
    }
    
    public void setAddress(Address address) {
        this.address = address;
    }

    //validation tool to check the required customerName field was entered, then calls the subsequent address validation
    public boolean validateInput() throws InvalidCustomerException, InvalidAddressException, InvalidCityException, InvalidCountryException {
       if (this.customerName.equals("")) {
            throw new InvalidCustomerException("Customer name is required.");
        }
        
        
            this.address.validateInput();

        return true;
    }
}
