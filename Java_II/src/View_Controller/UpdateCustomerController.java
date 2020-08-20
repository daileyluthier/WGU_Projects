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
import Exceptions.InvalidCityException;
import Exceptions.InvalidCountryException;
import Exceptions.InvalidCustomerException;
import Model.Address;
import Model.City;
import Model.Country;
import Model.Customer;
import static View_Controller.CalendarViewController.selectedCustomer;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateCustomerController implements Initializable {

    @FXML
    private Label updateCustomerLabel;

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
    CustomerDAOInterface customerDBA = new CustomerDAO();
    AddressDAOInterface addressDBA = new AddressDAO();
    CityDAOInterface cityDBA = new CityDAO();
    CountryDAOInterface countryDBA = new CountryDAO();

    private Address customerAddress = addressDBA.getAddress(selectedCustomer.getAddress().getAddressId());
    private City customerCity = cityDBA.getCity(customerAddress.getCity().getCityId());
    private Country customerCountry = countryDBA.getCountry(customerCity.getCountry().getCountryId());

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
    void handleSaveCustomerButton(ActionEvent event) throws InvalidAddressException, InvalidCityException, InvalidCountryException, InvalidCustomerException, IOException {

        System.out.println("Starting to update customer in database");

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
            if (!"".equals(customerName)) {

                Address modifiedCustomerAddress = selectedCustomer.getAddress();
                City modifiedCustomerCity = modifiedCustomerAddress.getCity();
                Country modifiedCustomerCountry = modifiedCustomerCity.getCountry();

                Country country = handleUpdateCountry(modifiedCustomerCountry.getCountryId(), addressCountry);
                City city = handleUpdateCity(modifiedCustomerCity.getCityId(), addressCity, country);
                Address address = handleUpdateAddress(modifiedCustomerAddress.getAddressId(), address1, address2, addressPostalCode, addressPhone, city);

                if (address.validateInput() && city.validateInput() && country.validateInput()) {
                    Customer modifiedCustomer = handleUpdateCustomer(selectedCustomer.getCustomerId(), customerName, address);
                    if (modifiedCustomer.validateInput()) {

                        FXMLLoader appointmentCalendarLoader = new FXMLLoader(CalendarViewController.class.getResource("CalendarView.fxml"));
                        Parent appointmentCalendarScreen = appointmentCalendarLoader.load();
                        Scene appointmentCalendarScene = new Scene(appointmentCalendarScreen);
                        Stage appointmentCalendarStage = new Stage();
                        appointmentCalendarStage.setScene(appointmentCalendarScene);
                        appointmentCalendarStage.show();
                        Stage updateCustomerStage = (Stage) saveButton.getScene().getWindow();
                        updateCustomerStage.close();
                    }
                } else {
                    saveAlert.close();
                }
            } else {
                Alert exAlert = new Alert(Alert.AlertType.ERROR);
                exAlert.setHeaderText("Invalid Customer Name");
                exAlert.setContentText("Customer Name Field cannot be empty!");
                exAlert.showAndWait().filter(response -> response == ButtonType.OK);
            }
        }
    }

    //update address in database
    private Address handleUpdateAddress(int addressId, String address1, String address2, String postalCode, String phone, City city) throws InvalidCityException, InvalidCountryException {
        Address address = new Address();

        address.setAddressId(addressId);
        address.setAddress(address1);
        address.setAddress2(address2);
        address.setPostalCode(postalCode);
        address.setPhone(phone);
        address.setCity(city);

        AddressDAOInterface addressDBA = new AddressDAO();
        addressDBA.updateAddress(address);

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

    //update city in database
    private City handleUpdateCity(int cityId, String cityName, Country country) throws InvalidCountryException {
        City city = new City();
        city.setCityId(cityId);
        city.setCity(cityName);
        city.setCountry(country);

        CityDAOInterface cityDBA = new CityDAO();
        cityDBA.updateCity(city);

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

    //update COuntry in database
    private Country handleUpdateCountry(int countryId, String countryName) throws InvalidCountryException {
        Country country = new Country();
        country.setCountryId(countryId);
        country.setCountry(countryName);
        CountryDAOInterface countryDBA = new CountryDAO();
        countryDBA.updateCountry(country);

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

    //update customer in database
    private Customer handleUpdateCustomer(int customerId, String customerName, Address address) throws InvalidAddressException, InvalidCityException, InvalidCountryException {
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setCustomerName(customerName);
        customer.setAddress(address);

        CustomerDAOInterface customerDBA = new CustomerDAO();
        customerDBA.updateCustomer(customer);

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

    public void setSelectedCustomerInformation() {
        customerNameField.setText(selectedCustomer.getCustomerName());
        addressLine1Field.setText(selectedCustomer.getAddress().getAddress());
        addressLine2Field.setText(selectedCustomer.getAddress().getAddress2());
        customerPostalCodeField.setText(selectedCustomer.getAddress().getPostalCode());
        customerPhoneNumberField.setText(selectedCustomer.getAddress().getPhone());
        customerCityField.setText(selectedCustomer.getAddress().getCity().getCity());
        customerCountryField.setText(selectedCustomer.getAddress().getCity().getCountry().getCountry());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        setSelectedCustomerInformation();

    }
}
