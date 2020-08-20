/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Address;
import javafx.collections.ObservableList;

/**
 *
 * @author daileyluthier
 */
public interface AddressDAOInterface {
    
    public int addAddress(Address address);
    
    public Address getAddress(int addressId);
    
    public int getAddressId(String address);
        
    public void deleteAddress(Address address);
    
    public void updateAddress(Address address);
    
    public ObservableList<Address> getAllAddresses();
    
}
