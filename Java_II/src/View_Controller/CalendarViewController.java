package View_Controller;

import DAO.AppointmentDAO;
import DAO.AppointmentDAOInterface;
import DAO.CustomerDAO;
import DAO.CustomerDAOInterface;

import DAO.DBConnection;
import Model.Customer;
import Model.Appointment;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CalendarViewController implements Initializable {
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        getWeeklyAppointments();
        getMonthlyAppointments();
        getAppointmentDetails();
        getCustomerDetails();

    }
    
    @FXML
    public static Appointment selectedAppointment;
    
    @FXML
    public static Customer selectedCustomer;
    
    @FXML
    public boolean isModification;
    
    @FXML
    private Tab monthlyCalendarViewTab;

    @FXML
    private TableView<Appointment> monthlyCalendarView;

    @FXML
    private TableColumn<Appointment, String> monthlyCalendarTypeCol;

    @FXML
    private TableColumn<Appointment, String> monthlyCalendarStartCol;

    @FXML
    private TableColumn<Appointment, String> monthlyCalendarEndCol;

    @FXML
    private TableColumn<Appointment, String> monthlyCalendarCustomerNameCol;

    @FXML
    private TableColumn<Appointment, String> monthlyCalendarContactCol;

    @FXML
    private TableColumn<Appointment, String> monthlyCalendarTitleCol;

    @FXML
    private TableColumn<Appointment, String> monthlyCalendarDescriptionCol;

    @FXML
    private Tab weeklyCalendarViewTab;

    @FXML
    private TableView<Appointment> weeklyCalendarView;

    @FXML
    private TableColumn<Appointment, String> weeklyCalendarTypeCol;

    @FXML
    private TableColumn<Appointment, String> weeklyCalendarStartCol;

    @FXML
    private TableColumn<Appointment, String> weeklyCalendarEndCol;

    @FXML
    private TableColumn<Appointment, String> weeklyCalendarCustomerNameCol;

    @FXML
    private TableColumn<Appointment, String> weeklyCalendarContactCol;

    @FXML
    private TableColumn<Appointment, String> weeklyCalendarTitleCol;

    @FXML
    private TableColumn<Appointment, String> weeklyCalendarDescriptionCol;
   
    @FXML
    private Tab appointmentDetailsTab;

    @FXML
    private TableView<Appointment> appointmentDetailsTable;

    @FXML
    private TableColumn<Appointment, String> appointmentCustomerNameCol;

    @FXML
    private TableColumn<Appointment, String> appointmentTitleCol;

    @FXML
    private TableColumn<Appointment, String> appointmentDescriptionCol;

    @FXML
    private TableColumn<Appointment, String> appointmentLocationCol;

    @FXML
    private TableColumn<Appointment, String> appointmentContactCol;

    @FXML
    private TableColumn<Appointment, String> appointmentTypeCol;

    @FXML
    private TableColumn<Appointment, String> appointmentURLCol;
    
    @FXML
    private TableColumn<Appointment, String> appointmentStartDateCol;

    @FXML
    private TableColumn<Appointment, String> appointmentEndDateCol;

    @FXML
    private Button searchAppointmentsButton;

    @FXML
    private TextField appointmentsSearchField;

    @FXML
    private Button addAppointmentsButton;

    @FXML
    private Button deleteAppointmentsButton;

    @FXML
    private Button updateAppointmentsButton;
    
    @FXML
    private Tab customerDetailsTab;

    @FXML
    private TableView<Customer> customerDetailsTable;

    @FXML
    private TableColumn<Customer, String> customerNameCol;

    @FXML
    private TableColumn<Customer, String> customerAddress1Col;

    @FXML
    private TableColumn<Customer, String> customerAddress2Col;

    @FXML
    private TableColumn<Customer, String> customerCityCol;

    @FXML
    private TableColumn<Customer, String> customerCountryCol;

    @FXML
    private TableColumn<Customer, String> customerPostalCodeCol;

    @FXML
    private TableColumn<Customer, String> customerPhoneNumberCol;

    @FXML
    private Button searchCustomerButton;

    @FXML
    private TextField customerSearchField;

    @FXML
    private Button addCustomerButton;

    @FXML
    private Button deleteCustomerButton;

    @FXML
    private Button updateCustomerButton;

    @FXML
    private Button exitButton;
    
    @FXML
    private Button reportsButton;
    
    @FXML
    private Button weeklyAppointmentDetailsButton;
    
    @FXML
    private Button monthlyAppointmentDetailsButton;
    
    @FXML
    private final DateTimeFormatter formatDT = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm z");
    
    @FXML
    private final DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    
    @FXML
    private final DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("hh:mm z");
    
    // List of appointments to display
    public ObservableList<Appointment> calendarAppointments = FXCollections.observableArrayList();

    // Appointment DAO
    public final AppointmentDAOInterface appointmentDBA;

    
    public CalendarViewController() {
        appointmentDBA = new AppointmentDAO();
    }
    
    public static Appointment getUpdatedAppointment() {
        return selectedAppointment;
    }
    
    public static Customer getUpdatedCustomer() {
        return selectedCustomer;
    }

    
    @FXML
    void handleAddCustomerButton(ActionEvent event) {
        Alert addAlert = new Alert(AlertType.CONFIRMATION);
        addAlert.setTitle("Add Customer");
        addAlert.setHeaderText("Are you sure you want to add a new customer?");
        addAlert.showAndWait();
        if (addAlert.getResult() == ButtonType.OK) {
            try {
                 Parent newCustomer = FXMLLoader.load(getClass().getResource("NewCustomer.fxml"));
                 Scene scene = new Scene(newCustomer);
                 Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                 window.setScene(scene);
                 window.show();
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    @FXML
    void handleUpdateCustomerButton(ActionEvent event) {
        selectedCustomer = customerDetailsTable.getSelectionModel().getSelectedItem();
        Alert modAlert = new Alert(AlertType.CONFIRMATION);
        modAlert.setTitle("Update Customer");
        modAlert.setHeaderText("Are you sure you want to update a customer?");
        modAlert.setContentText("Press OK to update a customer. \nPress Cancel to cancel the update.");
        modAlert.showAndWait();
        if (modAlert.getResult() == ButtonType.OK) {
            try {
                isModification=true;
                FXMLLoader selCustLoader = new FXMLLoader(UpdateCustomerController.class.getResource("UpdateCustomer.fxml"));
                Parent selCustScreen = selCustLoader.load();
                Scene selCustScene = new Scene(selCustScreen);
                Stage selCustStage = new Stage();
                selCustStage.setTitle("Customer Update");
                selCustStage.setScene(selCustScene);
                selCustStage.setResizable(false);
                selCustStage.show();
                Stage apptCalStage = (Stage) updateCustomerButton.getScene().getWindow();
                apptCalStage.close();
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        else {
            modAlert.close();
        }
    } 
    
    @FXML
    void handleDeleteCustomerButton(ActionEvent event) {
        CustomerDAOInterface deleteCustomer = new CustomerDAO();

        
            Alert deleteAlert = new Alert(AlertType.CONFIRMATION);
            deleteAlert.setTitle("Delete Customer");
            deleteAlert.setHeaderText("Are you sure you want to delete this Customer?");
            deleteAlert.showAndWait();
            if (deleteAlert.getResult() == ButtonType.OK) {
                try {
                    Customer cusotmer = customerDetailsTable.getSelectionModel().getSelectedItem();
                    deleteCustomer.inactiveCustomer(cusotmer);
                    getCustomerDetails();
                }
                catch (NullPointerException e) {
                    Alert nullAlert = new Alert(AlertType.ERROR);
                    nullAlert.setTitle("Appointment Modification Error");
                    nullAlert.setContentText("There was no appointment selected!");
                    nullAlert.showAndWait();
                }
            }
            else {
                deleteAlert.close();
            }
    }

    @FXML
    void handleAddAppointmentsButton(ActionEvent event) {
        Alert addAlert = new Alert(AlertType.CONFIRMATION);
        addAlert.setTitle("Add Appointment");
        addAlert.setHeaderText("Are you sure you want to add a new appointment?");
        addAlert.setContentText("Press OK to add the appointment. \nPress Cancel to cancel the addition.");
        addAlert.showAndWait();
        if (addAlert.getResult() == ButtonType.OK) {
            try {
                FXMLLoader addApptLoader = new FXMLLoader(NewAppointmentController.class.getResource("NewAppointment.fxml"));
                Parent addApptScreen = addApptLoader.load();
                Scene addApptScene = new Scene(addApptScreen);
                Stage addApptStage = new Stage();
                addApptStage.setTitle("Add Appointment");
                addApptStage.setScene(addApptScene);
                addApptStage.setResizable(false);
                addApptStage.show();
                Stage apptCalStage = (Stage) addAppointmentsButton.getScene().getWindow();
                apptCalStage.close();
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    @FXML
    void handleUpdateAppointmentsButton(ActionEvent event) {
            
            selectedAppointment = appointmentDetailsTable.getSelectionModel().getSelectedItem();
            if (selectedAppointment == null) {
                Alert nullAlert = new Alert(AlertType.ERROR);
                nullAlert.setTitle("Appointment Modification Error");
                nullAlert.setHeaderText("The appointment is not able to be modified!");
                nullAlert.setContentText("There was no appointment selected!");
                nullAlert.showAndWait();
            }
            else {
                    Alert modAlert = new Alert(AlertType.CONFIRMATION);
                    modAlert.setTitle("Modify Appointment");
                    modAlert.setHeaderText("Are you sure you want to modify this appointment?");
                    modAlert.setContentText("Press OK to modify the appointment. \nPress Cancel to cancel the modification.");
                    modAlert.showAndWait();
                    if (modAlert.getResult() == ButtonType.OK) {
                        try {
                            FXMLLoader modApptLoader = new FXMLLoader(UpdateAppointmentController.class.getResource("UpdateAppointment.fxml"));
                            Parent modApptScreen = modApptLoader.load();
                            Scene modApptScene = new Scene(modApptScreen);
                            Stage modApptStage = new Stage();
                            modApptStage.setTitle("Modify Appointment");
                            modApptStage.setScene(modApptScene);
                            modApptStage.setResizable(false);
                            modApptStage.show();
                            Stage apptCalStage = (Stage) updateAppointmentsButton.getScene().getWindow();
                            apptCalStage.close();
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
    }
    
    @FXML
    void handleDeleteAppointmentsButton(ActionEvent event) {
        
            AppointmentDAOInterface deleteAppointment = new AppointmentDAO();

        
            Alert deleteAlert = new Alert(AlertType.CONFIRMATION);
            deleteAlert.setTitle("Delete Appointment");
            deleteAlert.setHeaderText("Are you sure you want to delete this appointment?");
            deleteAlert.showAndWait();
            if (deleteAlert.getResult() == ButtonType.OK) {
                try {
                    Appointment appointment = appointmentDetailsTable.getSelectionModel().getSelectedItem();
                    deleteAppointment.deleteAppointment(appointment);
                    getAppointmentDetails();
                }
                catch (NullPointerException e) {
                    Alert nullAlert = new Alert(AlertType.ERROR);
                    nullAlert.setTitle("Appointment Modification Error");
                    nullAlert.setContentText("There was no appointment selected!");
                    nullAlert.showAndWait();
                }
            }
            else {
                deleteAlert.close();
            }
    }
    
    @FXML
    void handleExitButton(ActionEvent event) throws SQLException, Exception {
        Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirmation.initModality(Modality.WINDOW_MODAL);
        alertConfirmation.setContentText("Are you sure?");
        
        Optional<ButtonType> result = alertConfirmation.showAndWait();
            
        if (result.get() == ButtonType.OK) {
            DBConnection.closeConnection();
            System.exit(0);
        } else {
            //if an alternative thing is decided
        }
    }
    
    @FXML
    void handleReportsButton(ActionEvent event) throws SQLException, Exception {
        Alert addAlert = new Alert(AlertType.CONFIRMATION);
        addAlert.setTitle("Reports");
        addAlert.setHeaderText("Would you like to see reports?");
        addAlert.showAndWait();
        if (addAlert.getResult() == ButtonType.OK) {
            try {
                 Parent newCustomer = FXMLLoader.load(getClass().getResource("Reports.fxml"));
                 Scene scene = new Scene(newCustomer);
                 Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                 window.setScene(scene);
                 window.show();
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    public void getWeeklyAppointments() {
        
        AppointmentDAOInterface appointments = new AppointmentDAO();
        
        //getting appointments by week, lambdas make this more simplistic
        weeklyCalendarCustomerNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getCustomerName()));
        weeklyCalendarTitleCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        weeklyCalendarDescriptionCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        weeklyCalendarContactCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContact()));
        weeklyCalendarTypeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        weeklyCalendarStartCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStart().format(formatDT)));
        weeklyCalendarEndCol.setCellValueFactory(cellData ->  new SimpleStringProperty(cellData.getValue().getEnd().format(formatDT)));
        weeklyCalendarView.setItems(appointments.getAppointmentsByWeek());
        
        
    }
    
    public void getMonthlyAppointments() {
        AppointmentDAOInterface appointments = new AppointmentDAO();
        
        //gettin appointments by month, lambda expression
        monthlyCalendarCustomerNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getCustomerName()));
        monthlyCalendarTitleCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        monthlyCalendarDescriptionCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        monthlyCalendarContactCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContact()));
        monthlyCalendarTypeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        monthlyCalendarStartCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStart().format(formatDT)));
        monthlyCalendarEndCol.setCellValueFactory(cellData ->  new SimpleStringProperty(cellData.getValue().getEnd().format(formatDT)));
        monthlyCalendarView.setItems(appointments.getAppointmentsByMonth());
    }
    
    public void getCustomerDetails() {
        
        CustomerDAOInterface customerDetails = new CustomerDAO();
        
        customerNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomerName()));
        customerAddress1Col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getAddress()));
        customerAddress2Col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getAddress2()));
        customerCityCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getCity().getCity()));
        customerCountryCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getCity().getCountry().getCountry()));
        customerPostalCodeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getPostalCode()));
        customerPhoneNumberCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getPhone()));
        customerDetailsTable.setItems(customerDetails.getAllCustomers());
    }
    
    public void getAppointmentDetails() {
        
        AppointmentDAOInterface appointmentDetails = new AppointmentDAO();
        
        appointmentCustomerNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getCustomerName()));
        appointmentTitleCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        appointmentDescriptionCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        appointmentLocationCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocation()));
        appointmentContactCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContact()));
        appointmentTypeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        appointmentURLCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUrl()));
        appointmentStartDateCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStart().format(formatDT)));
        appointmentEndDateCol.setCellValueFactory(cellData ->  new SimpleStringProperty(cellData.getValue().getEnd().format(formatDT)));
        appointmentDetailsTable.setItems(appointmentDetails.getAllAppointments());
    }
    
}
