/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.City;

/**
 *
 * @author daileyluthier
 */
public interface CityDAOInterface {

    public int addCity(City city);
    
    public City getCity(int cityId);
    
    public int getCityId(String city);
    
    public void deleteCity(City city);
    
    public void updateCity(City city);
    
}
