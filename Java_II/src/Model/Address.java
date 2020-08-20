/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Exceptions.InvalidAddressException;
import Exceptions.InvalidCityException;
import Exceptions.InvalidCountryException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author daileyluthier
 */
public class Address {
    
    //primary key unique Id for address
    private int addressId;
    
    //address attributes
    private String address;
        public StringProperty address1Property = new SimpleStringProperty((String) address);

    private String address2;
        public StringProperty address2Property = new SimpleStringProperty((String) address2);

    private String postalCode;
        public StringProperty postalCodeProperty = new SimpleStringProperty((String) postalCode);

    private String phone;
        public StringProperty phoneProperty = new SimpleStringProperty((String) phone);
        
    private int addressCityId;
    
    //reference to the city class to assign city
    public City city;
    
    //constructor
    public Address() {
        
    }
    
    //getter and setter for address id
    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }
    //getter and setter for address1
    public String getAddress() {
        return address;
    }

    public void setAddress(String address1) {
        this.address = address1;
    }
    
    public StringProperty addressProperty() {
        return address1Property;
    }
    
    //getter and setter for address2
    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }
    
    public StringProperty address2Property() {
        return address2Property;
    }
    
    //getter and setter for postal code
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public StringProperty postalCodeProperty() {
        return postalCodeProperty;
    }
    
    //getter and setter for phone number
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public StringProperty phoneProperty() {
        return phoneProperty;
    }
    
    //getter and setter for address City id
    public int getAddressCityId() {
        return addressCityId;
    }

    public void setAddressCityId(int addressCityId) {
        this.addressCityId = addressCityId;
    }
    
    //setter and getter of City class 
    public void setCity(City city) {
        this.city = city;
    }
    
    public City getCity() {
        return this.city;
    }
    
     public boolean validateInput() throws InvalidAddressException, InvalidCityException, InvalidCountryException {
        if (this.address.equals("")) {
            throw new InvalidAddressException("Address is a required field.");
        }
        
        if (this.address2.equals("")) {
            throw new InvalidAddressException("Address 2 is a required field, if there is no apartment just input a space");
        }
        
        if (this.postalCode.equals("")) {
            throw new InvalidAddressException("Postal code is a required field.");
        }
        
        if (this.phone.equals("")) {
            throw new InvalidAddressException("Phone number is a required field.");
        }
        
        
            this.city.validateInput();
        
        return true;
    }
    
}
