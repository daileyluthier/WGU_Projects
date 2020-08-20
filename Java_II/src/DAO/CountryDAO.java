/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import static DAO.DBConnection.conn;
import Model.Country;
import static View_Controller.LogInScreenController.loggedUser;
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
public class CountryDAO implements CountryDAOInterface {
    
    //constructor
    public CountryDAO() {}
    
    //retrieve all available countries in the database
    @Override
    public ObservableList<Country> getAllCountries() {
           ObservableList<Country> allCountries = FXCollections.observableArrayList();
           String getAllCountriesQuery = "SELECT * FROM country";

           try {
               PreparedStatement statement = conn.prepareStatement(getAllCountriesQuery);
               ResultSet resultSet = statement.executeQuery();

               while (resultSet.next()) {
                   Country country = new Country();
                   country.setCountryId(resultSet.getInt("countryId"));
                   country.setCountry(resultSet.getString("country"));
                   allCountries.add(country);
               }
           }
           catch (SQLException e) {
               System.out.println(e.getMessage());
           }
           return allCountries;
       }
    
    @Override
    public int getCountryId(String country) {
        String getCityIdQuery = "SELECT countryId FROM country WHERE country = ?";
        int getCountryId = 0;
        
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(getCityIdQuery);
            
            if (resultSet.next()) {
                getCountryId = resultSet.getInt(1);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return getCountryId;
    }

       //get country via the id
       @Override
       public Country getCountry(int countryId) {
           String getCountryFromIdQuery = "SELECT * FROM country WHERE countryId = ?";
           Country getCountryFromId = new Country();

           try {
               PreparedStatement statement = conn.prepareStatement(getCountryFromIdQuery);
               statement.setInt(1, countryId);
               ResultSet resultSet = statement.executeQuery();

               if (resultSet.next()) {
                   getCountryFromId.setCountry(resultSet.getString("country"));
                   getCountryFromId.setCountryId(resultSet.getInt("countryId"));
                   
               } else {
                   return null;
               }
           }
           catch (SQLException e) {
               System.out.println(e.getMessage());
           }
           return getCountryFromId;
       }
       
       //get max id to set the next unique key for country class
       private static int getMaxCountryId() {
           int maxCountryId = 0;
           String maxCountryIdQuery = "SELECT MAX(countryId) FROM country";

           try {
               Statement statement = conn.createStatement();
               ResultSet resultSet = statement.executeQuery(maxCountryIdQuery);

               if (resultSet.next()) {
                   maxCountryId = resultSet.getInt(1);
               }
           }
           catch (SQLException e) {
               System.out.println(e.getMessage());
           }
           return maxCountryId + 1;
       }

       //add country to the database
       @Override
       public int addCountry(Country country) {
           String addCountryQuery = String.join(" ",
                   "INSERT INTO country (countryId, country, createDate, createdBy, lastUpdate, lastUpdateBy)",
                   "VALUES (?, ?, NOW(), ?, NOW(), ?)");

           int countryId = getMaxCountryId();
           try {
               PreparedStatement statement = conn.prepareStatement(addCountryQuery);
               statement.setInt(1, countryId);
               statement.setString(2, country.getCountry());
               statement.setString(3, loggedUser.getUserName());
               statement.setString(4, loggedUser.getUserName());
               statement.executeUpdate();
           }
           catch (SQLException e) {
               System.out.println(e.getMessage());
           }
           return countryId;
       }
       
       //update country in database
       @Override
       public void updateCountry(Country country) {
           String updateCountryQuery = String.join(" ",
                   "UPDATE country",
                   "SET country=?, lastUpdate=NOW(), lastUpdateBy=?",
                   "WHERE countryId=?");

           try {
               PreparedStatement statement = conn.prepareStatement(updateCountryQuery);
               statement.setString(1, country.getCountry());
               statement.setString(2, loggedUser.getUserName());
               statement.setInt(3, country.getCountryId());
               statement.executeUpdate();
           }
           catch (SQLException e) {
               System.out.println(e.getMessage());
           }
       }
       
       //delete country from the database
       @Override
       public void deleteCountry(Country country) {
           String deleteCountryQuery = "DELETE FROM country WHERE countryId = ?";

           try {
               PreparedStatement statement = conn.prepareStatement(deleteCountryQuery);
               statement.setInt(1, country.getCountryId());
               statement.executeUpdate();
           }
           catch (SQLException e) {
               System.out.println(e.getMessage());
           }
       }
}
