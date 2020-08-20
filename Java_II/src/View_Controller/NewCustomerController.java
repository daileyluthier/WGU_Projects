package View_Controller;

import DAO.AddressDAO;
import DAO.AddressDAOInterface;
import DAO.CityDAO;
import DAO.CityDAOInterface;
import DAO.CountryDAO;
import DAO.CountryDAOInterface;
import DAO.CustomerDAO;
import DAO.CustomerDAOInterface;
import Exceptions.InvalidAddressException;
import Exceptions.InvalidAppointmentException;
import Exceptions.InvalidCityException;
import Exceptions.InvalidCountryException;
import Exceptions.InvalidCustomerException;
import Model.Address;
import Model.City;
import Model.Country;
import Model.Customer;
import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewCustomerController {

    @FXML
    private Label newCustomerLabel;

    @FXML
    private Label customerIdLabel;

    @FXML
    private Label customerNameLabel;

    @FXML
    private Label customerAddress1Label;

    @FXML
    private Label customerAddress2Label;

    @FXML
    private Label customerCityLabel;

    @FXML
    private Label customerCountryLabel;

    @FXML
    private TextField customerIdField;

    @FXML
    private TextField customerNameField;

    @FXML
    private TextField addressLine1Field;

    @FXML
    private TextField addressLine2Field;

    @FXML
    private TextField customerCityField;

    @FXML
    private TextField customerCountryField;

    @FXML
    private Label customerPostalCodeLabel;

    @FXML
    private TextField customerPostalCodeField;

    @FXML
    private Label customerPhoneNumberLabel;

    @FXML
    private TextField customerPhoneNumberField;

    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;

    @FXML
    private Customer newCustomer = new Customer();
    private Address customerAddress = new Address();
    private City customerCity = new City();
    private Country customerCountry = new Country();

    @FXML
    void handleCancelCustomerButton(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to exit?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            try {
                FXMLLoader apptCalLoader = new FXMLLoader(CalendarViewController.class.getResource("CalendarView.fxml"));
                Parent apptCalScreen = apptCalLoader.load();
                Scene apptCalScene = new Scene(apptCalScreen);
                Stage apptCalStage = new Stage();
                apptCalStage.setScene(apptCalScene);
                apptCalStage.show();
                Stage modCustStage = (Stage) cancelButton.getScene().getWindow();
                modCustStage.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            alert.close();
        }
    }

    @FXML
    void handleSaveCustomerButton(ActionEvent event) throws Exception {

        System.out.println("Starting to save customer to database");

        // Get values from the GUI
        String customerName = customerNameField.getText();
        String address1 = addressLine1Field.getText();
        String address2 = addressLine2Field.getText();
        String addressPhone = customerPhoneNumberField.getText();
        String addressPostalCode = customerPostalCodeField.getText();
        String addressCity = customerCityField.getText();
        String addressCountry = customerCountryField.getText();

        Alert saveAlert = new Alert(Alert.AlertType.CONFIRMATION);
        saveAlert.setTitle("Save Customer Details");
        saveAlert.setHeaderText("Are you sure these details are correct?");
        saveAlert.setContentText("Press OK to save the addition. \nPress Cancel to stay on this screen.");
        saveAlert.showAndWait();
        if (saveAlert.getResult() == ButtonType.OK) {
            if(!"".equals(customerName)) {
                Country country = handleAddCountry(addressCountry);
                City city = handleAddCity(addressCity, country);
                Address address = handleAddAddress(address1, address2, addressPostalCode, addressPhone, city);
                if (address.validateInput() && city.validateInput() && country.validateInput()) { 
                    
                    Customer newCustomer = handleAddCustomer(customerName, address);
                    if (newCustomer.validateInput()) {
                        
                        FXMLLoader apptCalLoader = new FXMLLoader(CalendarViewController.class.getResource("CalendarView.fxml"));
                        Parent apptCalScreen = apptCalLoader.load();
                        Scene apptCalScene = new Scene(apptCalScreen);
                        Stage apptCalStage = new Stage();
                        apptCalStage.setScene(apptCalScene);
                        apptCalStage.show();
                        Stage addCustStage = (Stage) saveButton.getScene().getWindow();
                        addCustStage.close();
                    } else {
                        saveAlert.close();
                    }
                }
            } else {
                Alert exAlert = new Alert(Alert.AlertType.ERROR);
                exAlert.setHeaderText("Invalid Customer Name");
                exAlert.setContentText("Customer Name Field cannot be empty!");
                exAlert.showAndWait().filter(response -> response == ButtonType.OK);
            }
            
        }
    }

    //address to database
    private Address handleAddAddress(String address1, String address2, String postalCode, String phone, City city) throws InvalidAddressException, InvalidCityException, InvalidCountryException {
        Address address = new Address();

        address.setAddress(address1);
        address.setAddress2(address2);
        address.setPostalCode(postalCode);
        address.setPhone(phone);
        address.setCity(city);

        AddressDAOInterface addressDBA = new AddressDAO();
        int newId = addressDBA.addAddress(address);

        address.setAddressId(newId);

        try {
            address.validateInput();
        } catch (InvalidAddressException e) {
            Alert exAlert = new Alert(Alert.AlertType.ERROR);
            exAlert.setHeaderText("There was an exception!");
            exAlert.setContentText(e.getMessage());
            exAlert.showAndWait().filter(response -> response == ButtonType.OK);
        }

        return address;
    }

    //add city to the database
    private City handleAddCity(String cityName, Country country) throws InvalidCountryException {
        City city = new City();
        city.setCity(cityName);
        city.setCountry(country);

        CityDAOInterface cityDBA = new CityDAO();
        int newId = cityDBA.addCity(city);

        city.setCityId(newId);

        try {
            city.validateInput();
        } catch (InvalidCityException e) {
            Alert exAlert = new Alert(Alert.AlertType.ERROR);
            exAlert.setHeaderText("There was an exception!");
            exAlert.setContentText(e.getMessage());
            exAlert.showAndWait().filter(response -> response == ButtonType.OK);
        }
        return city;
    }

    //add country to the database
    private Country handleAddCountry(String countryName) throws InvalidCountryException {
        Country country = new Country();
        country.setCountry(countryName);

        CountryDAOInterface countryDBA = new CountryDAO();
        int newId = countryDBA.addCountry(country);

        country.setCountryId(newId);

        try {
            country.validateInput();
        } catch (InvalidCountryException e) {
            Alert exAlert = new Alert(Alert.AlertType.ERROR);
            exAlert.setHeaderText("There was an exception!");
            exAlert.setContentText(e.getMessage());
            exAlert.showAndWait().filter(response -> response == ButtonType.OK);
        }

        return country;
    }

    //add customer to database
    private Customer handleAddCustomer(String customerName, Address address) throws InvalidCustomerException, InvalidAddressException, InvalidCityException, InvalidCountryException {
        Customer customer = new Customer();
        customer.setCustomerName(customerName);
        customer.setAddress(address);

        
        
        CustomerDAOInterface customerDBA = new CustomerDAO();
        int newId = customerDBA.addCustomer(customer);
        
        customer.setCustomerId(newId); 
        
        try {
            customer.validateInput();
        } catch (InvalidCustomerException e) {
            Alert exAlert = new Alert(Alert.AlertType.ERROR);
            exAlert.setHeaderText("There was an exception!");
            exAlert.setContentText(e.getMessage());
            exAlert.showAndWait().filter(response -> response == ButtonType.OK);
        }
        

        return customer;
    }

}
