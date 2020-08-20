/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Country;
import javafx.collections.ObservableList;

/**
 *
 * @author daileyluthier
 */
public interface CountryDAOInterface {

    public int addCountry(Country country);
    
    public ObservableList<Country> getAllCountries();
    
    public Country getCountry(int countryId);
    
    public int getCountryId(String country);
    
    public void deleteCountry(Country country);
    
    public void updateCountry(Country country);
    
}
