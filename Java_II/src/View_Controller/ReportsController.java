package View_Controller;

import DAO.AddressDAO;
import DAO.AddressDAOInterface;
import DAO.AppointmentDAO;
import DAO.AppointmentDAOInterface;
import DAO.CustomerDAO;
import DAO.CustomerDAOInterface;
import Model.Address;
import Model.Appointment;
import Model.Customer;
import Model.User;
import java.io.IOException;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ReportsController {

    @FXML
    private TextArea reportField;

    @FXML
    private Button cancelButton;

    @FXML
    private Button appointmentTypesButton;

    @FXML
    private Button customersPerCityButton;

    @FXML
    private Button consultantScheduleButton;

    @FXML
    private final DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    @FXML
    private final DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("hh:mm a z");

    @FXML
    void handleAppointmentTypesButton(ActionEvent event) {

        try {
            reportField.clear();

            AppointmentDAOInterface appointmentDBA = new AppointmentDAO();
            ObservableList<Appointment> appointmentTypes = appointmentDBA.getAppointmentsByMonth();
            Integer value = 1;
            Map<String, Integer> map = new HashMap<>();
            for (Appointment a : appointmentTypes) {
                if (map.containsKey(a.getType())) {
                    map.put(a.getType(), map.get(a.getType()) + 1);
                } else {
                    map.put(a.getType(), value);
                }
            }
            for (String s : map.keySet()) {
                reportField.appendText("There are: " + map.get(s) + " appointment type(s) of: " + s + " this month.\n\n");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void handleCancelButton(ActionEvent event) throws SQLException, Exception {
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
    void handleConsultantScheduleButton(ActionEvent event) {

        try {
            reportField.clear();
            
            AppointmentDAOInterface appointmentDBA = new AppointmentDAO();
            ObservableList<Appointment> contactSchedule = appointmentDBA.getAllAppointments();
            String contactName = "";
            String customerName = "";
            ZonedDateTime startZDT;

            for (Appointment a : contactSchedule) {
                String contact = a.getContact();
                int customer = a.getCustomer().getCustomerId();
                startZDT = a.getStart();
                
                
                CustomerDAOInterface customerDBA = new CustomerDAO();
                Customer customerById = customerDBA.getCustomer(customer);
                customerName = customerById.getCustomerName();
                reportField.appendText("Consultant: " + contact
                        + " has an appointment with " + customerName
                        + " on " + startZDT.format(formatDate)
                        + " at " + startZDT.format(formatTime) + ".\n");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void handleCustomersPerCityButton(ActionEvent event) {

        try {
            reportField.clear();

            AddressDAOInterface addressDBA = new AddressDAO();
            ObservableList<Address> addressCities = addressDBA.getAllAddresses();
            Integer value = 1;
            Map<String, Integer> map = new HashMap<>();
            for (Address a : addressCities) {
                if (map.containsKey(a.getCity().getCity())) {
                    map.put((a.getCity().getCity()), map.get(a.getCity().getCity()) + 1);
                } else {
                    map.put(a.getCity().getCity(), value);
                }
            }
            for (String s : map.keySet()) {
                reportField.appendText("There are: " + map.get(s) + " addresses on file in this city: " + s + "\n");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
