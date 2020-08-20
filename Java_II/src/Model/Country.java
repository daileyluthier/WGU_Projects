/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Exceptions.InvalidCountryException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author daileyluthier
 */
public class Country {
    
    //primary key and unique identifier for country class
    private int countryId;
    
    //country name
    private String country;
    public StringProperty countryProperty = new SimpleStringProperty((String) country);

    // constructor
    public Country () {}
    
    //getter and setter for the country id
    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    //getter and setter for the country name
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
    public StringProperty countryProperty() {
        return countryProperty;
    }

    public boolean validateInput() throws InvalidCountryException {
        if (this.country.equals("")) {
            throw new InvalidCountryException("Please enter a country's name");
        }

        return true;
    }
}
