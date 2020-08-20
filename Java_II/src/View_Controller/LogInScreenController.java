package View_Controller;

import Model.User;
import DAO.*;
import Model.Appointment;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LogInScreenController implements Initializable {

    ResourceBundle rb;
    Locale userLocale;
    Locale esLocale = new Locale("es", "ES");
    Locale frLocale = new Locale("fr", "FR");
    static final Logger userLog = Logger.getLogger("userlog.txt");
    public static User loggedUser = new User();

    @FXML
    private Label logInScreenLabel;

    @FXML
    private Button logInButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label languageLabel;

    @FXML
    private TextField usernameLogInField;

    @FXML
    private PasswordField passwordLogInField;

    @FXML
    private ComboBox<?> languageComboBox;

    @FXML
    void handleCancelButton(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle(this.rb.getString("cancel_title"));
        alert.setHeaderText(this.rb.getString("cancel_title"));
        alert.setContentText(this.rb.getString("cancel_text"));
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> System.exit(0));
    }

    @FXML
    void handleLogInButton(ActionEvent eLogin) throws IOException, Exception {
        FileHandler userLogFH = new FileHandler("userlog.txt", true);
        SimpleFormatter sf = new SimpleFormatter();
        userLogFH.setFormatter(sf);
        userLog.addHandler(userLogFH);
        userLog.setLevel(Level.INFO);

        loggedUser = null;

        String username = usernameLogInField.getText();
        String password = passwordLogInField.getText();

        userLog.log(Level.INFO, "Username:" + username + "Password:" + password);

        try {
            UserDAOInterface userDBA = new UserDAO();

            ObservableList<User> userLoginInfo = userDBA.getAllActiveUsers();
            //The use of the lambda is to iterate through the list of users

            userLoginInfo.forEach((u) -> {
                if (username.equals(u.getUserName()) && password.equals(u.getPassword())) {
                    loggedUser = u;
                    userLog.log(Level.INFO, "LUSERNAME:" + u.getUserName() + "LPASSWORD:" + u.getPassword());
                }
            });

            if (loggedUser != null) {
                try {
                    AppointmentDAOInterface appointmentDBA = new AppointmentDAO();

                    Appointment upcomingAppt = appointmentDBA.getUpcomingAppointments();
                    if (!(upcomingAppt.getAppointmentId() == 0)) {
                        Alert apptAlert = new Alert(Alert.AlertType.INFORMATION);
                        apptAlert.setTitle("Upcoming Appointment Reminder");
                        apptAlert.setHeaderText("You have an upcoming appointment!");
                        apptAlert.setContentText("You have an appointment scheduled"
                                + "\non " + upcomingAppt.getStart().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))
                                + "\nat " + upcomingAppt.getStart().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.FULL))
                                + " with client " + upcomingAppt.getCustomer().getCustomerName() + ".");
                        apptAlert.showAndWait();
                        if (apptAlert.getResult() == ButtonType.OK) {
                            userLog.log(Level.INFO, "User: {0} logged in.", loggedUser.getUserName());
                            Stage loginStage = (Stage) logInButton.getScene().getWindow();
                            loginStage.close();
                            FXMLLoader apptCalLoader = new FXMLLoader(CalendarViewController.class.getResource("CalendarView.fxml"));
                            Parent apptCalScreen = apptCalLoader.load();
                            Scene apptCalScene = new Scene(apptCalScreen);
                            Stage apptCalStage = new Stage();
                            apptCalStage.setTitle("Calendar");
                            apptCalStage.setScene(apptCalScene);
                            apptCalStage.show();
                        } else {
                            apptAlert.close();
                        }
                    } else {
                        userLog.log(Level.INFO, "User: {0} logged in.", loggedUser.getUserName());
                        FXMLLoader apptCalLoader = new FXMLLoader(CalendarViewController.class.getResource("CalendarView.fxml"));
                        Parent apptCalScreen = apptCalLoader.load();
                        Scene apptCalScene = new Scene(apptCalScreen);
                        Stage apptCalStage = new Stage();
                        apptCalStage.setTitle("Calendar");
                        apptCalStage.setScene(apptCalScene);
                        apptCalStage.show();
                        Stage loginStage = (Stage) logInButton.getScene().getWindow();
                        loginStage.close();
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                userLog.log(Level.WARNING, "Invalid credentials entered! User: {0}", username);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.initModality(Modality.NONE);
                alert.setTitle(this.rb.getString("invalidUser_title"));
                alert.setHeaderText(this.rb.getString("invalidUser_title"));
                alert.setContentText(this.rb.getString("invalidUser_text"));
                alert.showAndWait()
                        .filter(response -> response == ButtonType.OK);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.userLocale = Locale.getDefault();
        this.rb = ResourceBundle.getBundle("Languages/LogIn", this.userLocale);

        this.usernameLabel.setText(this.rb.getString("username"));
        this.usernameLogInField.setPromptText(this.rb.getString("usernameLogIn"));
        this.passwordLabel.setText(this.rb.getString("password"));
        this.passwordLogInField.setPromptText(this.rb.getString("passwordLogIn"));
        this.logInButton.setText(this.rb.getString("login"));
        this.logInScreenLabel.setText(this.rb.getString("loginScreenLabel"));

    }
}
