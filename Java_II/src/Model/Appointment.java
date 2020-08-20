/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DAO.AppointmentDAO;
import DAO.AppointmentDAOInterface;
import Exceptions.InvalidAppointmentException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author daileyluthier
 */
public class Appointment {

    // primary key and unique identifier for class appointment
    public int appointmentId;

    // other attributes of class appointment
    private String contact;
    public StringProperty contactProperty = new SimpleStringProperty((String) contact);
    private String type;
    public StringProperty typeProperty = new SimpleStringProperty((String) type);
    private String description;
    public StringProperty descriptionProperty = new SimpleStringProperty((String) description);
    private ZonedDateTime end;
    private String location;
    public StringProperty locationProperty = new SimpleStringProperty((String) location);
    private ZonedDateTime start;
    public String title;
    public StringProperty titleProperty = new SimpleStringProperty((String) title);
    private String url;
    public StringProperty urlProperty = new SimpleStringProperty((String) url);
    private LocalDateTime midnightToday;

    private LocalDateTime reminderDate;

    private int customerId;
    private int userId;

    // reference to customer class
    public Customer customer;

    //reference to User Class
    public User user;

    public Appointment() {
        this.midnightToday = LocalDateTime.of(LocalDate.MAX, LocalTime.MIN);
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate today = LocalDate.now();
        midnightToday = LocalDateTime.of(today, midnight);

    }

    //getter and setter of customer id
    public int getCustomrId() {
        return this.customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    //getter and sette of user id
    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    //getter and setter of appointment ied
    public int getAppointmentId() {
        return this.appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    //getter and setter of title attribute
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public StringProperty titleProperty() {
        return titleProperty;
    }

    //getter and setter of description attribute
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StringProperty descriptionProperty() {
        return descriptionProperty;
    }

    //getter and setter of the location attribute
    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public StringProperty locationProperty() {
        return locationProperty;
    }

    //getter and setter of the contact attribute
    public String getContact() {
        return this.contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public StringProperty contactProperty() {
        return contactProperty;
    }

    //getter and setter of attribute type
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public StringProperty typeProperty() {
        return typeProperty;
    }

    //getter and setter of attribute url
    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public StringProperty urlProperty() {
        return urlProperty;
    }

    //reference to the customer class
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    //reference to the User class
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //getter and setter for appointment end time
    public ZonedDateTime getEnd() {
        return this.end;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }

    //getter and setter for appointment start time
    public ZonedDateTime getStart() {
        return this.start;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    //validates all the fields are inputted for an appointment
    public boolean validateInput() throws InvalidAppointmentException {
        if (this.customer == null) {
            throw new InvalidAppointmentException("Customer is required.");
        }

        if (this.title.equals("")) {
            throw new InvalidAppointmentException("Title is required.");
        }

        if (this.contact.equals("")) {
            throw new InvalidAppointmentException("Contact is required.");
        }

        if (this.url.equals("")) {
            throw new InvalidAppointmentException("URL is required.");
        }

        if (this.location.equals("")) {
            throw new InvalidAppointmentException("Location is required.");
        }

        if (this.description.equals("")) {
            throw new InvalidAppointmentException("Notes is required.");
        }

        validateTime();
        validateAvailability();
        
        return true;
    }

    // validates time of day and week of appointment
    public boolean validateTime() throws InvalidAppointmentException {
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate apptStartDate = this.start.toLocalDate();
        LocalTime apptStartTime = this.start.toLocalTime();
        LocalDate apptEndDate = this.end.toLocalDate();
        LocalTime apptEndTime = this.end.toLocalTime();
        int weekDay = apptStartDate.getDayOfWeek().getValue();

        if (!apptStartDate.isEqual(apptEndDate)) {
            throw new InvalidAppointmentException("An appoinment can only be a single day!");
        }
        if (weekDay == 6 || weekDay == 7) {
            throw new InvalidAppointmentException("An appointment can only be scheduled on weekdays!");
        }
        if (apptStartTime.isBefore(midnight.plusHours(8))) {
            throw new InvalidAppointmentException("An appointment cannot be scheduled before normal business hours!");
        }
        if (apptEndTime.isAfter(midnight.plusHours(17))) {
            throw new InvalidAppointmentException("An appointment cannot be scheduled after normal business hours!");
        }
        if (apptStartDate.isBefore(LocalDate.now()) || apptStartTime.isBefore(LocalTime.MIDNIGHT)) {
            throw new InvalidAppointmentException("An appointment cannot be scheduled in the past!");
        }
        return true;
    }

    //validates that no appointments overlap one another
    public boolean validateAvailability() throws InvalidAppointmentException {
        AppointmentDAOInterface appointmentDBA = (AppointmentDAOInterface) new AppointmentDAO();

        ObservableList<Appointment> overlappingAppt = appointmentDBA.getOverlappingAppointments(this.start.toLocalDateTime(), this.end.toLocalDateTime());

        if (overlappingAppt.size() > 1) {
            throw new InvalidAppointmentException("An appointment cannot be scheduled at the same time as another appointment!");
        }
        return true;
    }

}
