package View_Controller;

import DAO.AppointmentDAO;
import DAO.AppointmentDAOInterface;
import DAO.CustomerDAO;
import DAO.CustomerDAOInterface;
import Exceptions.InvalidAppointmentException;
import Model.Appointment;
import Model.Customer;
import static View_Controller.LogInScreenController.loggedUser;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.LocalTimeStringConverter;

public class NewAppointmentController implements Initializable {

    @FXML
    private Label newAppointmentLabel;

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
    private TextField appointmentTitleField;

    @FXML
    private TextField appointmentDescriptionField;

    @FXML
    private TextField appointmentLocationField;

    @FXML
    private TextField appointmentContactField;

    @FXML
    private TextField appointmentTypeField;

    @FXML
    private Label customerPostalCodeLabel;

    @FXML
    private TextField appointmentURLField;

    @FXML
    private Label customerPhoneNumberLabel;

    @FXML
    private TextField appointmentPhoneNumberField;

    @FXML
    private ComboBox<Customer> customerComboBox;

    @FXML
    private Label customerPhoneNumberLabel1;

    @FXML
    private Label customerPhoneNumberLabel11;

    @FXML
    private Label customerPhoneNumberLabel12;

    @FXML
    private Label customerPhoneNumberLabel13;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;
    
    @FXML
    public static Appointment newAppointment = new Appointment();

    @FXML
    private Spinner<LocalTime> startTimeSpinner = new Spinner(new SpinnerValueFactory() {
        
        {
            setConverter(new LocalTimeStringConverter(DateTimeFormatter.ofPattern("HH:mm"), DateTimeFormatter.ofPattern("HH:mm")));
        }
        
        @Override
        public void decrement(int steps) {
            if (getValue() == null)
                setValue(LocalTime.now());
            else {
                LocalTime time = (LocalTime) getValue();
                setValue(time.minusMinutes(steps));
            }
        }

        @Override
        public void increment(int steps) {
            if (this.getValue() == null)
                setValue(LocalTime.now());
            else {
                LocalTime time = (LocalTime) getValue();
                setValue(time.plusMinutes(steps));
            }
            }
    });

    @FXML
    private Spinner<LocalTime> endTimeSpinner = new Spinner(new SpinnerValueFactory() {
        
        {
            setConverter(new LocalTimeStringConverter(DateTimeFormatter.ofPattern("HH:mm"), DateTimeFormatter.ofPattern("HH:mm")));
        }
        
        @Override
        public void decrement(int steps) {
            if (getValue() == null)
                setValue(LocalTime.now());
            else {
                LocalTime time = (LocalTime) getValue();
                setValue(time.minusMinutes(steps));
            }
        }

        @Override
        public void increment(int steps) {
            if (this.getValue() == null)
                setValue(LocalTime.now());
            else {
                LocalTime time = (LocalTime) getValue();
                setValue(time.plusMinutes(steps));
            }
            }
    });

    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;
        
    @FXML
    private final DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    @FXML
    private final DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm");
    
    @FXML
    private static ZoneId zId = ZoneId.systemDefault();
    
    //format time
    private final DateTimeFormatter timeFormat = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
    
    

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
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        else {
            alert.close();
        }
    }

    @FXML
    void handleSaveCustomerButton(ActionEvent event) throws IOException {

        Alert saveAlert = new Alert(Alert.AlertType.CONFIRMATION);
        saveAlert.setTitle("Save Appointment Modifications");
        saveAlert.setHeaderText("Are you sure you want to save?");
        saveAlert.setContentText("Press OK to save the addition. \nPress Cancel to stay on this screen.");
        saveAlert.showAndWait();
        if (saveAlert.getResult() == ButtonType.OK) {
            try {
                getAppointmentInfo();
                newAppointment.validateInput();
                
                if (newAppointment.validateInput() && newAppointment.validateAvailability()) {
                    
                    AppointmentDAOInterface appointmentDBA = new AppointmentDAO();
                    
                    appointmentDBA.addAppointment(newAppointment);
                    FXMLLoader appointmentCalendarLoader = new FXMLLoader(CalendarViewController.class.getResource("CalendarView.fxml"));
                    Parent appointmentCalendarScreen = appointmentCalendarLoader.load();
                    Scene appointmentCalendarScene = new Scene(appointmentCalendarScreen);
                    Stage appointmentCalendarStage = new Stage();
                    appointmentCalendarStage.setScene(appointmentCalendarScene);
                    appointmentCalendarStage.show();
                    Stage addAppointmentStage = (Stage) saveButton.getScene().getWindow();
                    addAppointmentStage.close();
                }
            }
            catch (InvalidAppointmentException e) {
                Alert exAlert = new Alert(Alert.AlertType.ERROR);
                exAlert.setHeaderText("There was an exception!");
                exAlert.setContentText(e.getMessage());
                exAlert.showAndWait().filter(response -> response == ButtonType.OK);
            }
        }
        else {
            saveAlert.close();
        }
    }

    //where I left off
    public void getAppointmentInfo() throws InvalidAppointmentException {
        try {
            loggedUser = View_Controller.LogInScreenController.loggedUser;

            newAppointment.setCustomer(customerComboBox.getValue());
            newAppointment.setCustomerId(customerComboBox.getValue().getCustomerId());
            newAppointment.setUserId(loggedUser.getUserId());
            newAppointment.setTitle(appointmentTitleField.getText());
            newAppointment.setDescription(appointmentDescriptionField.getText());
            newAppointment.setLocation(appointmentLocationField.getText());
            newAppointment.setContact(appointmentContactField.getText());
            newAppointment.setType(appointmentTypeField.getText());
            newAppointment.setUrl(appointmentURLField.getText());
            newAppointment.setStart(ZonedDateTime.of(LocalDate.parse(startDatePicker.getValue().toString(), formatDate), LocalTime.parse(startTimeSpinner.getValue().toString(), formatTime), zId));
            newAppointment.setEnd(ZonedDateTime.of(LocalDate.parse(endDatePicker.getValue().toString(), formatDate), LocalTime.parse(endTimeSpinner.getValue().toString(), formatTime), zId));
        }
        catch (NullPointerException e) {
            Alert nullAlert = new Alert(Alert.AlertType.ERROR);
            nullAlert.setHeaderText("The appointment is not able to be added!");
            nullAlert.showAndWait();
        }
    }
    
    public void getAllCustomers() {
        
        CustomerDAOInterface allCustomersDBA = new CustomerDAO();
        
        ObservableList<Customer> allCustomers = allCustomersDBA.getAllCustomers();
        customerComboBox.setItems(allCustomers);
        customerComboBox.setPromptText("Select a customer:");
    }
    
    public void convertCustomerString() {
        customerComboBox.setConverter(new StringConverter<Customer>() {
            @Override
            public String toString(Customer cust) {
                return cust.getCustomerName();
            }

            @Override
            public Customer fromString(String string) {
                return customerComboBox.getValue();
            }
        });
    }
    
    public void setDefaultDateTime() {
        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now());
        startTimeSpinner.setValueFactory(svfStart);
        svfStart.setValue(LocalTime.of(8, 00));
        endTimeSpinner.setValueFactory(svfEnd);
        svfEnd.setValue(LocalTime.of(17, 00));
    }
    
    SpinnerValueFactory svfStart = new SpinnerValueFactory<LocalTime>() {
        {
            setConverter(new LocalTimeStringConverter(formatTime,null));
        }
        @Override public void decrement(int steps) {
            LocalTime time = (LocalTime) getValue();
            setValue(time.minusHours(steps));
            setValue(time.minusMinutes(16 - steps));
        }
        @Override public void increment(int steps) {
            LocalTime time = (LocalTime) getValue();
            setValue(time.plusHours(steps));
            setValue(time.plusMinutes(steps + 14));
        }
    };
    
    SpinnerValueFactory svfEnd = new SpinnerValueFactory<LocalTime>() {
        {
            setConverter(new LocalTimeStringConverter(formatTime,null));
        }
        @Override
        public void decrement(int steps) {
            LocalTime time = (LocalTime) getValue();
            setValue(time.minusHours(steps));
            setValue(time.minusMinutes(16 - steps));
        }
        @Override
        public void increment(int steps) {
            LocalTime time = (LocalTime) getValue();
            setValue(time.plusHours(steps));
            setValue(time.plusMinutes(steps + 14));
        }
    };

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getAllCustomers();
        convertCustomerString();
        setDefaultDateTime();
    }
}    