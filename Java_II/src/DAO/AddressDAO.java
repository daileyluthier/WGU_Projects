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
public class AddressDAO implements AddressDAOInterface{
        
    //constructor
    public AddressDAO() {}

    //retrieve the address via id from database

    /**
     *
     * @param addressId
     * @return
     */
    @Override
    public Address getAddress(int addressId) {
        String getAddressFromIdQuery = "SELECT * FROM address WHERE addressId = ?";
        Address getAddressFromId = new Address();

        try {
            PreparedStatement statement = conn.prepareStatement(getAddressFromIdQuery);
            statement.setInt(1, addressId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                getAddressFromId.setAddressId(resultSet.getInt("addressId"));
                getAddressFromId.setAddress(resultSet.getString("address"));
                getAddressFromId.setAddress2(resultSet.getString("address2"));
                getAddressFromId.setPostalCode(resultSet.getString("postalCode"));
                getAddressFromId.setPhone(resultSet.getString("phone"));

                CityDAOInterface city = (CityDAOInterface) new CityDAO();
                getAddressFromId.setCity(city.getCity(resultSet.getInt("cityId")));

            } else {
                return null;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return getAddressFromId;
    }
    
    //Retrieve cityId
    @Override
    public int getAddressId(String address) {
        String getAddressIdQuery = "SELECT addressId FROM address WHERE address = ?";
        int getAddressId = 0;

        try {
            PreparedStatement statement = conn.prepareStatement(getAddressIdQuery);
            statement.setString(1, address);
            ResultSet resultSet = statement.executeQuery();
        
            if (resultSet.next()) {
                getAddressId = resultSet.getInt(1);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return getAddressId;
    }
    
    //retrieve the max address id from databse to make the next one with the next in the series
    private static int getMaxAddressId() {
        int maxAddressId = 0;
        String maxAddressIdQuery = "SELECT MAX(addressId) FROM address";

        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(maxAddressIdQuery);

            if (resultSet.next()) {
                maxAddressId = resultSet.getInt(1);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return maxAddressId + 1;
    }
    
    //add a new address to the database
    @Override
    public int addAddress(Address address) {
        String addAddressQuery = String.join(" ",
                "INSERT INTO address (addressId, address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy)",
                "VALUES (?, ?, ?, ?, ?, ?, NOW(), ?, NOW(), ?)");

        int addressId = getMaxAddressId();
        try {
            PreparedStatement statement = conn.prepareStatement(addAddressQuery);
            statement.setInt(1, addressId);
            statement.setString(2, address.getAddress());
            statement.setString(3, address.getAddress2());
            statement.setInt(4, address.getCity().getCityId());
            statement.setString(5, address.getPostalCode());
            statement.setString(6, address.getPhone());
            statement.setString(7, loggedUser.getUserName());
            statement.setString(8, loggedUser.getUserName());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return addressId;
    }

    //update an existing address in the database
    @Override
    public void updateAddress(Address address) {
        String updateAddressQuery = String.join(" ",
                "UPDATE address",
                "SET address=?, address2=?, cityId=?, postalCode=?, phone=?, lastUpdate=NOW(), lastUpdateBy=?",
                "WHERE addressId=?");

        try{
            PreparedStatement statement = conn.prepareStatement(updateAddressQuery);
            statement.setString(1, address.getAddress());
            statement.setString(2, address.getAddress2());
            statement.setInt(3, address.getCity().getCityId());
            statement.setString(4, address.getPostalCode());
            statement.setString(5, address.getPhone());
            statement.setString(6, loggedUser.getUserName());
            statement.setInt(7, address.getAddressId());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //delete an address from the database
    @Override
    public void deleteAddress(Address address) {
        String deleteAddressQuery = "DELETE FROM address WHERE addressId = ?";

        try {
            PreparedStatement statement = conn.prepareStatement(deleteAddressQuery);
            statement.setInt(1, address.getAddressId());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ObservableList<Address> getAllAddresses() {
        ObservableList<Address> address = FXCollections.observableArrayList();
        String getAllAddressesQuery = "SELECT * FROM address";
        
        try {
            PreparedStatement statement = conn.prepareStatement(getAllAddressesQuery);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                
                Address getAddressFromId = new Address();
                getAddressFromId.setAddressId(resultSet.getInt("addressId"));
                getAddressFromId.setAddress(resultSet.getString("address"));
                getAddressFromId.setAddress2(resultSet.getString("address2"));
                getAddressFromId.setPostalCode(resultSet.getString("postalCode"));
                getAddressFromId.setPhone(resultSet.getString("phone"));

                CityDAOInterface city = (CityDAOInterface) new CityDAO();
                getAddressFromId.setCity(city.getCity(resultSet.getInt("cityId")));
                
                address.add(getAddressFromId);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return address;
    }
}