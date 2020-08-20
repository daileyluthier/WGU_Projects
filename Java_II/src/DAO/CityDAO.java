/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import static DAO.DBConnection.conn;
import Model.City;
import static View_Controller.LogInScreenController.loggedUser;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author daileyluthier
 */
public class CityDAO implements CityDAOInterface{
    
    //constructor
    public CityDAO() {
        
    }
    
    //Retrieve city
    @Override
    public City getCity(int cityId) {
        String getCityFromIdQuery = "SELECT * FROM city WHERE cityId = ?";
        City getCityFromId = new City();
        
        try {
            PreparedStatement statement = conn.prepareStatement(getCityFromIdQuery);
            statement.setInt(1, cityId);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                getCityFromId.setCityId(resultSet.getInt("cityId"));
                getCityFromId.setCity(resultSet.getString("city"));
                
                CountryDAOInterface country = (CountryDAOInterface) new CountryDAO();
                getCityFromId.setCountry(country.getCountry(resultSet.getInt("countryId")));
            } else {
                return null;
                }
        } 
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return getCityFromId;
    }
    
    //Retrieve cityId
    @Override
    public int getCityId(String city) {
        String getCityIdQuery = "SELECT cityId FROM city WHERE city = ?";
        int getCityId = 0;
        
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(getCityIdQuery);
            
            if (resultSet.next()) {
                getCityId = resultSet.getInt(1);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return getCityId;
    }
    
    //retrieve the max city id for primary/unique key assignment
    private static int getMaxCityId() {
        int maxCityId = 0;
        String maxCityIdQuery = "SELECT MAX(cityId) FROM city";
        
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(maxCityIdQuery);
            
            if (resultSet.next()) {
                maxCityId = resultSet.getInt(1);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return maxCityId + 1;
    }

    /**
     *
     * @param city
     * @return
     */
    @Override
    public int addCity(City city) {
        String addCityQuery = String.join(" ",
                "INSERT INTO city (cityId, city, countryId, createDate, createdBy, lastUpdate, lastUpdateBy)",
                "VALUES (?, ?, ?, NOW(), ?, NOW(), ?)");
        
        int cityId = getMaxCityId();
        try {
            PreparedStatement statement = conn.prepareStatement(addCityQuery);
            statement.setInt(1, cityId);
            statement.setString(2, city.getCity());
            statement.setInt(3, city.getCountry().getCountryId());
            statement.setString(4, loggedUser.getUserName());
            statement.setString(5, loggedUser.getUserName());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cityId;
    }
    
    //update City in the database
    @Override
    public void updateCity(City city) {
        String updateCityQuery = String.join(" ",
                "UPDATE city",
                "SET city=?, countryId=?, lastUpdate=NOW(), lastUpdateBy=?",
                "WHERE cityId=?");
        
        try {
            PreparedStatement statement = conn.prepareStatement(updateCityQuery);
            statement.setString(1, city.getCity());
            statement.setInt(2, city.getCountry().getCountryId());
            statement.setString(3, loggedUser.getUserName());
            statement.setInt(4, city.getCityId());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //delete city from the database
    @Override
    public void deleteCity(City city) {
        String deleteCityQuery = "DELETE FROM city WHERE cityId = ?";
        
        try {
            PreparedStatement statement = conn.prepareStatement(deleteCityQuery);
            statement.setInt(1, city.getCityId());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}