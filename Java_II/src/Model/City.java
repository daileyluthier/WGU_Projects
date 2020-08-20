/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Exceptions.InvalidCityException;
import Exceptions.InvalidCountryException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author daileyluthier
 */
public class City {
    
    //primary key unique identifier of class city
    private int cityId;
    
    //city name
    private String city;
    public StringProperty cityProperty = new SimpleStringProperty((String) city);
    
    //reference to country class
    public Country country;
    
    private int cityCountryId;
    
    //getter and setter for city id
    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    //getter and setter for city name
    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    
    public StringProperty cityProperty() {
        return cityProperty;
    }

    //getter and setter of country reference to country class
    public Country getCountry() {
        return this.country;
    }
    
    public void setCountry(Country country) {
        this.country = country;
    }
    
    public void setCityCountryId(int cityCountryId) {
        this.cityCountryId = cityCountryId;
    }
    
    public int getCityCountryId() {
        return this.cityCountryId;
    }
    
    //validates the proper city fields were filled in an calls country validation
    public boolean validateInput() throws InvalidCityException, InvalidCountryException {
        if (this.city.equals("")) {
            throw new InvalidCityException("Please enter a city name.");
        }
        
            this.country.validateInput();
        
        return true;
    }
}
