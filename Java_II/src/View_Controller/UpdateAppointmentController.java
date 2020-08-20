package View_Controller;

import DAO.AppointmentDAO;
import DAO.AppointmentDAOInterface;
import DAO.CustomerDAO;
import DAO.CustomerDAOInterface;
import Exceptions.InvalidAppointmentException;
import Model.Appointment;
import Model.Customer;
import static View_Controller.LogInScreenController.loggedUser;
import static View_Controller.CalendarViewController.selectedAppointment;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.LocalTimeStringConverter;

public class UpdateAppointmentController implements Initializable {

    @FXML
    private Label updateAppointmentLabel;

    @FXML
    private Label customerIdLabel;

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
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

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
    private String errEmptyInput = new String();
    
    @FXML
    private String errorValidation = new String();
    
    @FXML
    public static Appointment appointment = new Appointment();
    
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
    void handleSaveAppointmentsButton(ActionEvent event) throws IOException {
        
        Customer appointmentCustomer = customerComboBox.getValue();
        String appointmentTitle = appointmentTitleField.getText();
        String appointmentDescription = appointmentDescriptionField.getText();
        String appointmentLocation = appointmentLocationField.getText();
        String appointmentContact = appointmentContactField.getText();
        String appointmentType = appointmentTypeField.getText();
        String appointmentURL = appointmentURLField.getText();
        ZonedDateTime appointmentStart = (ZonedDateTime.of(LocalDate.parse(startDatePicker.getValue().toString(), formatDate), LocalTime.parse(startTimeSpinner.getValue().toString(), formatTime), zId));
        ZonedDateTime appointmentEnd = (ZonedDateTime.of(LocalDate.parse(endDatePicker.getValue().toString(), formatDate), LocalTime.parse(endTimeSpinner.getValue().toString(), formatTime), zId));
        
        try {
            
            if (appointmentStart == null || appointmentEnd == null) {
                throw new InvalidAppointmentException("Dates and times are required.");
            }
            
            
            Appointment appointment = new Appointment();
            appointment.setCustomer(appointmentCustomer);
            appointment.setTitle(appointmentTitle);
            appointment.setDescription(appointmentDescription);
            appointment.setLocation(appointmentLocation);
            appointment.setContact(appointmentContact);
            appointment.setType(appointmentType);
            appointment.setUrl(appointmentURL);
            appointment.setStart(appointmentStart);
            appointment.setEnd(appointmentEnd);
                    
            // Validate the appointment
            appointment.validateInput();
            
            // Establish DAOs
            AppointmentDAOInterface appointmentDBA = new AppointmentDAO();

            appointment.setAppointmentId(selectedAppointment.getAppointmentId());
            appointmentDBA.updateAppointment(appointment);           

            FXMLLoader appointmentCalendarLoader = new FXMLLoader(CalendarViewController.class.getResource("CalendarView.fxml"));
            Parent appointmentCalendarScreen = appointmentCalendarLoader.load();
            Scene appointmentCalendarScene = new Scene(appointmentCalendarScreen);
            Stage appointmentCalendarStage = new Stage();
            appointmentCalendarStage.setScene(appointmentCalendarScene);
            appointmentCalendarStage.show();
            Stage updateAppointmentStage = (Stage) saveButton.getScene().getWindow();
            updateAppointmentStage.close();
                
        }  catch (InvalidAppointmentException e) {
            // If the times or dates aren't set above, we throw. This catches those
            // errors.
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Invalid Appointment");
            alert.setHeaderText("Invalid Appointment");
            alert.setContentText(e.getMessage());
            alert.showAndWait()
                .filter(response -> response == ButtonType.OK);
        }
    } 

        // establish DAO customer object
    CustomerDAOInterface customerDBA = new CustomerDAO();
    
    private ObservableList<Customer> allCustomers = customerDBA.getAllCustomers();
    
    public void getAllCustomers() {
        customerComboBox.setItems(allCustomers);
    }
    
    public void convertCustomerString() {
        customerComboBox.setConverter(new StringConverter<Customer>() {
            @Override
            public String toString(Customer customer) {
                return customer.getCustomerName();
            }

            @Override
            public Customer fromString(String string) {
                return customerComboBox.getValue();
            }
        });
    }
    
    public void setSelectedAppointmentInformation() {
        customerComboBox.getSelectionModel().select(selectedAppointment.getCustomer());
        appointmentTitleField.setText(selectedAppointment.getTitle());
        appointmentDescriptionField.setText(selectedAppointment.getDescription());
        appointmentLocationField.setText(selectedAppointment.getLocation());
        appointmentContactField.setText(selectedAppointment.getContact());
        appointmentTypeField.setText(selectedAppointment.getType());
        appointmentURLField.setText(selectedAppointment.getUrl());
        startDatePicker.setValue(selectedAppointment.getStart().toLocalDate());
        startTimeSpinner.setValueFactory(svfStart);
        svfStart.setValue(selectedAppointment.getStart().toLocalTime());
        endDatePicker.setValue(selectedAppointment.getEnd().toLocalDate());
        endTimeSpinner.setValueFactory(svfEnd);
        svfEnd.setValue(selectedAppointment.getEnd().toLocalTime());
    }
    
    public void updateAppointmentInformation() {
        appointment.getCustomer().setCustomerId(customerComboBox.getValue().getCustomerId());
        appointment.getUser().setUserId(loggedUser.getUserId());
        appointment.setTitle(appointmentTitleField.getText());
        appointment.setDescription(appointmentDescriptionField.getText());
        appointment.setLocation(appointmentLocationField.getText());
        appointment.setContact(appointmentContactField.getText());
        appointment.setType(appointmentTypeField.getText());
        appointment.setUrl(appointmentURLField.getText());
        appointment.setStart(ZonedDateTime.of(LocalDate.parse(startDatePicker.getValue().toString(), formatDate), LocalTime.parse(startTimeSpinner.getValue().toString(), formatTime), zId));
        appointment.setEnd(ZonedDateTime.of(LocalDate.parse(endDatePicker.getValue().toString(), formatDate), LocalTime.parse(endTimeSpinner.getValue().toString(), formatTime), zId));
        appointment.setAppointmentId(selectedAppointment.getAppointmentId());
    }

    SpinnerValueFactory svfStart = new SpinnerValueFactory<LocalTime>() {
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getAllCustomers();
        setSelectedAppointmentInformation();
        convertCustomerString();
        
        
        }
}
