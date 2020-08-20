/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import static View_Controller.LogInScreenController.loggedUser;

import static DAO.DBConnection.conn;
import Model.Appointment;
import Model.Customer;
import Model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author daileyluthier
 */
public class AppointmentDAO implements AppointmentDAOInterface {
    
    private static ZoneId zId = ZoneId.systemDefault();
    
    //constructor
    public AppointmentDAO() {
        
    }

    //get max id in table to evaluate next number in the series
    private int getAppointmentMaxId() {
        int appointmentMaxId = 0;
        
        String maxIdQuery = "SELECT MAX(appointmentId) FROM appointment";
        try {
            Statement stmt = conn.createStatement(); 
            ResultSet result = stmt.executeQuery(maxIdQuery);
            
            if(result.next()) {
                appointmentMaxId = result.getInt(1);
            }
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return appointmentMaxId + 1;
    }
    
    //to add an appointment to the database
    @Override
    public int addAppointment(Appointment appointment) {
        
        String addAppointmentQuery = String.join(" ",
            "INSERT INTO appointment (appointmentId, customerId, userId, title, description, location, contact, type, url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy)",
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, NOW(), ?)"
        );
    
        int appointmentId = getAppointmentMaxId();
        try {
            PreparedStatement statement = conn.prepareStatement(addAppointmentQuery);
            statement.setInt(1, appointmentId);
            statement.setInt(2, appointment.getCustomer().getCustomerId());
            statement.setInt(3, loggedUser.getUserId());
            statement.setString(4, appointment.getTitle());
            statement.setString(5, appointment.getDescription());
            statement.setString(6, appointment.getLocation());
            statement.setString(7, appointment.getContact());
            statement.setString(8, appointment.getType());
            statement.setString(9, appointment.getUrl());

            ZonedDateTime startZDT = appointment.getStart().withZoneSameInstant(ZoneId.of("UTC"));
            ZonedDateTime endZDT = appointment.getEnd().withZoneSameInstant(ZoneId.of("UTC"));
            statement.setTimestamp(10, Timestamp.valueOf(startZDT.toLocalDateTime()));
            statement.setTimestamp(11, Timestamp.valueOf(endZDT.toLocalDateTime()));
            
            statement.setString(13, loggedUser.userName);
            statement.setString(12, loggedUser.userName);
            
            statement.executeUpdate();
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return appointmentId;
            }

    //to get all the appointments and populate the table
    @Override
    public ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String getAppointmentQuery = String.join(" ",
            "SELECT * FROM appointment",
            "JOIN customer",
            "ON appointment.customerId = customer.customerId"   
        ); 

        try{
            PreparedStatement statement = conn.prepareStatement(getAppointmentQuery);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Appointment inquiredAppointment = new Appointment();
                inquiredAppointment.setAppointmentId(resultSet.getInt("appointmentId"));
                inquiredAppointment.setTitle(resultSet.getString("title"));
                inquiredAppointment.setDescription(resultSet.getString("description"));
                inquiredAppointment.setLocation(resultSet.getString("location"));
                inquiredAppointment.setContact(resultSet.getString("contact"));
                inquiredAppointment.setUrl(resultSet.getString("url"));
                inquiredAppointment.setType(resultSet.getString("type"));
                
                LocalDateTime startUTC = resultSet.getTimestamp("start").toLocalDateTime();
                LocalDateTime endUTC = resultSet.getTimestamp("end").toLocalDateTime();
                ZonedDateTime startLocal = ZonedDateTime.ofInstant(startUTC.toInstant(ZoneOffset.UTC), zId);
                ZonedDateTime endLocal = ZonedDateTime.ofInstant(endUTC.toInstant(ZoneOffset.UTC), zId);
                
                inquiredAppointment.setStart(startLocal);
                inquiredAppointment.setEnd(endLocal);
                
                CustomerDAOInterface customer = (CustomerDAOInterface) new CustomerDAO();
                inquiredAppointment.setCustomer(customer.getCustomer(resultSet.getInt("customerId")));
                
                appointments.add(inquiredAppointment);
            }
        } catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        
        return appointments;
    }
    
    //getAppointments by week

    /**
     *
     * @return
     */
    @Override
    public ObservableList<Appointment> getAppointmentsByWeek() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String getAppointmentQuery = String.join(" ",
            "SELECT * FROM appointment",
            "JOIN customer",
            "ON appointment.customerId = customer.customerId",
            "WHERE start BETWEEN NOW() AND (SELECT ADDDATE(NOW(), INTERVAL 7 DAY)) AND customer.active = 1"
        ); 

        try{
            PreparedStatement statement = conn.prepareStatement(getAppointmentQuery);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Appointment inquiredAppointment = new Appointment();
                inquiredAppointment.setAppointmentId(resultSet.getInt("appointmentId"));
                inquiredAppointment.setTitle(resultSet.getString("title"));
                inquiredAppointment.setDescription(resultSet.getString("description"));
                inquiredAppointment.setLocation(resultSet.getString("location"));
                inquiredAppointment.setContact(resultSet.getString("contact"));
                inquiredAppointment.setUrl(resultSet.getString("url"));
                inquiredAppointment.setType(resultSet.getString("type"));
                
                LocalDateTime startUTC = resultSet.getTimestamp("start").toLocalDateTime();
                LocalDateTime endUTC = resultSet.getTimestamp("end").toLocalDateTime();
                ZonedDateTime startLocal = ZonedDateTime.ofInstant(startUTC.toInstant(ZoneOffset.UTC), zId);
                ZonedDateTime endLocal = ZonedDateTime.ofInstant(endUTC.toInstant(ZoneOffset.UTC), zId);
                
                inquiredAppointment.setStart(startLocal);
                inquiredAppointment.setEnd(endLocal);
                
                CustomerDAOInterface customer = (CustomerDAOInterface) new CustomerDAO();
                inquiredAppointment.setCustomer(customer.getCustomer(resultSet.getInt("customerId")));
                
                appointments.add(inquiredAppointment);
            }
        } catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        
        return appointments;
    }

    //getAppointments by month
    @Override
    public ObservableList<Appointment> getAppointmentsByMonth() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String getMonthlyAppointmentQuery = String.join(" ",
            "SELECT * FROM appointment",
            "JOIN customer "
                    + "ON appointment.customerId = customer.customerId",
            "WHERE MONTH(start) = MONTH(NOW()) and YEAR(start) = YEAR(NOW()) "
                    + "AND customer.active = 1"
        ); 

        try{
            PreparedStatement statement = conn.prepareStatement(getMonthlyAppointmentQuery);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Appointment inquiredAppointment = new Appointment();
                inquiredAppointment.setAppointmentId(resultSet.getInt("appointmentId"));
                inquiredAppointment.setTitle(resultSet.getString("title"));
                inquiredAppointment.setDescription(resultSet.getString("description"));
                inquiredAppointment.setLocation(resultSet.getString("location"));
                inquiredAppointment.setContact(resultSet.getString("contact"));
                inquiredAppointment.setType(resultSet.getString("type"));
                inquiredAppointment.setUrl(resultSet.getString("url"));
                
                LocalDateTime startUTC = resultSet.getTimestamp("start").toLocalDateTime();
                LocalDateTime endUTC = resultSet.getTimestamp("end").toLocalDateTime();
                ZonedDateTime startLocal = ZonedDateTime.ofInstant(startUTC.toInstant(ZoneOffset.UTC), zId);
                ZonedDateTime endLocal = ZonedDateTime.ofInstant(endUTC.toInstant(ZoneOffset.UTC), zId);
                
                inquiredAppointment.setStart(startLocal);
                inquiredAppointment.setEnd(endLocal);
                
                CustomerDAOInterface customer = (CustomerDAOInterface) new CustomerDAO();
                inquiredAppointment.setCustomer(customer.getCustomer(resultSet.getInt("customerId")));
                
                appointments.add(inquiredAppointment);
            }
        } catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        
        return appointments;
    }
    
    //to get a specific appointment
    @Override
    public Appointment getAppointment(int appointmentId) {
        String getAppointmentQuery = String.join(" ",
            "SELECT * FROM appointment",
            "JOIN customer",
            "ON appointment.customerId = customer.customerId",
            "WHERE appointment.appointmentId = ? AND customer.active = 1"
        ); 
        
        Appointment inquiredAppointment = new Appointment();

        try{
            PreparedStatement statement = conn.prepareStatement(getAppointmentQuery);
            statement.setInt(1, appointmentId);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()){
                
                inquiredAppointment.setAppointmentId(resultSet.getInt("appointmentId"));
                inquiredAppointment.setTitle(resultSet.getString("title"));
                inquiredAppointment.setDescription(resultSet.getString("description"));
                inquiredAppointment.setLocation(resultSet.getString("location"));
                inquiredAppointment.setContact(resultSet.getString("contact"));
                inquiredAppointment.setType(resultSet.getString("type"));
                inquiredAppointment.setUrl(resultSet.getString("url"));
                
                 LocalDateTime startUTC = resultSet.getTimestamp("start").toLocalDateTime();
                LocalDateTime endUTC = resultSet.getTimestamp("end").toLocalDateTime();
                ZonedDateTime startLocal = ZonedDateTime.ofInstant(startUTC.toInstant(ZoneOffset.UTC), zId);
                ZonedDateTime endLocal = ZonedDateTime.ofInstant(endUTC.toInstant(ZoneOffset.UTC), zId);
                
                inquiredAppointment.setStart(startLocal);
                inquiredAppointment.setEnd(endLocal);
                
                CustomerDAOInterface customer = (CustomerDAOInterface) new CustomerDAO();
                inquiredAppointment.setCustomer(customer.getCustomer(resultSet.getInt("customerId")));
                
            } else {
                return null;
            }
        } catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        
        return inquiredAppointment;
    }

    //get appointments from a specific user
    @Override
    public ObservableList<Appointment> getAppointmentsByUser() {
        
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        
        String getAppointmentByUserQuery = String.join(" ",
            "SELECT * FROM appointment",
            "JOIN customer",
            "ON appointment.customerId = customer.customerId",
            "WHERE customer.active = 1",
            "AND appointment.userId = ?"
        ); 

        try {
            PreparedStatement statement = conn.prepareStatement(getAppointmentByUserQuery);
            statement.setInt(1, loggedUser.getUserId());
            
            ResultSet resultSet = statement.executeQuery();
            
            ZoneId zone = ZoneId.systemDefault();
            
            while (resultSet.next()) {
                Appointment inquiredAppointment = new Appointment();
                inquiredAppointment.setAppointmentId(resultSet.getInt("appointmentId"));
                inquiredAppointment.setTitle(resultSet.getString("title"));
                inquiredAppointment.setDescription(resultSet.getString("description"));
                inquiredAppointment.setLocation(resultSet.getString("location"));
                inquiredAppointment.setContact(resultSet.getString("contact"));
                inquiredAppointment.setType(resultSet.getString("type"));
                inquiredAppointment.setUrl(resultSet.getString("url"));
                
                 LocalDateTime startUTC = resultSet.getTimestamp("start").toLocalDateTime();
                LocalDateTime endUTC = resultSet.getTimestamp("end").toLocalDateTime();
                ZonedDateTime startLocal = ZonedDateTime.ofInstant(startUTC.toInstant(ZoneOffset.UTC), zId);
                ZonedDateTime endLocal = ZonedDateTime.ofInstant(endUTC.toInstant(ZoneOffset.UTC), zId);
                
                inquiredAppointment.setStart(startLocal);
                inquiredAppointment.setEnd(endLocal);
                
                CustomerDAOInterface customer = (CustomerDAOInterface) new CustomerDAO();
                inquiredAppointment.setCustomer(customer.getCustomer(resultSet.getInt("customerId")));
                
                appointments.add(inquiredAppointment);
            }
        } catch(SQLException ex){
            System.out.println(ex.getMessage());
        }

        return appointments;
    }

    //get appointments within the desired hours
    @Override
    public ObservableList<Appointment> getAppointmentsWithinWorkDay(LocalDateTime start, LocalDateTime end) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        
        String getAppointmentWithinWorkDayQuery = String.join(" ",
            "SELECT * FROM appointment",
            "JOIN customer",
            "ON appointment.customerId = customer.customerId",
            "WHERE appointment.start >= ? AND appointment.end <= ?",
            "AND customer.active = 1"
        );

        try {
            PreparedStatement statement = conn.prepareStatement(getAppointmentWithinWorkDayQuery);
            
            ZoneId zone = ZoneId.systemDefault();
            LocalDateTime startDatetimeParam = start.atZone(zone).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
            LocalDateTime endDatetimeParam = end.atZone(zone).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
            statement.setTimestamp(1, Timestamp.valueOf(startDatetimeParam));
            statement.setTimestamp(2, Timestamp.valueOf(endDatetimeParam));
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Appointment inquiredAppointment = new Appointment();
                inquiredAppointment.setAppointmentId(resultSet.getInt("appointmentId"));
                inquiredAppointment.setTitle(resultSet.getString("title"));
                inquiredAppointment.setDescription(resultSet.getString("description"));
                inquiredAppointment.setLocation(resultSet.getString("location"));
                inquiredAppointment.setContact(resultSet.getString("contact"));
                inquiredAppointment.setType(resultSet.getString("type"));
                inquiredAppointment.setUrl(resultSet.getString("url"));
                
                 LocalDateTime startUTC = resultSet.getTimestamp("start").toLocalDateTime();
                LocalDateTime endUTC = resultSet.getTimestamp("end").toLocalDateTime();
                ZonedDateTime startLocal = ZonedDateTime.ofInstant(startUTC.toInstant(ZoneOffset.UTC), zId);
                ZonedDateTime endLocal = ZonedDateTime.ofInstant(endUTC.toInstant(ZoneOffset.UTC), zId);
                
                inquiredAppointment.setStart(startLocal);
                inquiredAppointment.setEnd(endLocal);
                
                CustomerDAOInterface customer = (CustomerDAOInterface) new CustomerDAO();
                inquiredAppointment.setCustomer(customer.getCustomer(resultSet.getInt("customerId")));
                
                appointments.add(inquiredAppointment);
            }
        } catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        
        return appointments;
    }

    //check if appointments are overlapping
    @Override
    public ObservableList<Appointment> getOverlappingAppointments(LocalDateTime start, LocalDateTime end) {
        
    ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        
        String getOverlappingAppointmentQuery = String.join(" ",
            "SELECT * FROM appointment",
            "JOIN customer",
            "ON appointment.customerId = customer.customerId",
            "WHERE (appointment.start >= ? AND appointment.end <= ?)",
            "OR (appointment.start <= ? AND appointment.end >= ?)",
            "OR (appointment.start BETWEEN ? AND ? OR appointment.end BETWEEN ? AND ?)",
            "AND customer.active = 1"
        ); 

        try{
            PreparedStatement statement = conn.prepareStatement(getOverlappingAppointmentQuery);
            
            ZoneId zone = ZoneId.systemDefault();
            LocalDateTime startDatetimeParam = start.atZone(zone).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
            LocalDateTime endDatetimeParam = end.atZone(zone).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
            statement.setTimestamp(1, Timestamp.valueOf(startDatetimeParam));
            statement.setTimestamp(2, Timestamp.valueOf(endDatetimeParam));
            statement.setTimestamp(3, Timestamp.valueOf(startDatetimeParam));
            statement.setTimestamp(4, Timestamp.valueOf(endDatetimeParam));
            statement.setTimestamp(5, Timestamp.valueOf(startDatetimeParam));
            statement.setTimestamp(6, Timestamp.valueOf(endDatetimeParam));
            statement.setTimestamp(7, Timestamp.valueOf(startDatetimeParam));
            statement.setTimestamp(8, Timestamp.valueOf(endDatetimeParam));
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Appointment inquiredAppointment = new Appointment();
                inquiredAppointment.setAppointmentId(resultSet.getInt("appointmentId"));
                inquiredAppointment.setTitle(resultSet.getString("title"));
                inquiredAppointment.setDescription(resultSet.getString("description"));
                inquiredAppointment.setLocation(resultSet.getString("location"));
                inquiredAppointment.setContact(resultSet.getString("contact"));
                inquiredAppointment.setType(resultSet.getString("type"));
                inquiredAppointment.setUrl(resultSet.getString("url"));
                
                 LocalDateTime startUTC = resultSet.getTimestamp("start").toLocalDateTime();
                LocalDateTime endUTC = resultSet.getTimestamp("end").toLocalDateTime();
                ZonedDateTime startLocal = ZonedDateTime.ofInstant(startUTC.toInstant(ZoneOffset.UTC), zId);
                ZonedDateTime endLocal = ZonedDateTime.ofInstant(endUTC.toInstant(ZoneOffset.UTC), zId);
                
                inquiredAppointment.setStart(startLocal);
                inquiredAppointment.setEnd(endLocal);
                
                CustomerDAOInterface customer = (CustomerDAOInterface) new CustomerDAO();
                inquiredAppointment.setCustomer(customer.getCustomer(resultSet.getInt("customerId")));
                
                appointments.add(inquiredAppointment);
            }
        } catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        
        return appointments;
    }

    //delete an appointment from the database
    @Override
    public void deleteAppointment(Appointment appointment) {
      
    String deleteAppointmentQuery = "DELETE FROM appointment WHERE appointmentId = ?";
    
        try {
            PreparedStatement statement = conn.prepareStatement(deleteAppointmentQuery);
            statement.setInt(1, appointment.getAppointmentId());
            statement.executeUpdate();
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }
    
    }

    //update the appointment in the database with correct information
    @Override
    public void updateAppointment(Appointment appointment) {
        
        String updateAppointmentQuery = String.join(" ",
            "UPDATE appointment",
            "SET customerId=?, title=?, description=?, location=?, contact=?, type=?, url=?, start=?, end=?, lastUpdate=NOW(), lastUpdateBy=?",
            "WHERE appointmentId = ?"
        );
    
        try {
            PreparedStatement statement = conn.prepareStatement(updateAppointmentQuery);
            statement.setInt(1, appointment.getCustomer().getCustomerId());
            statement.setString(2, appointment.getTitle());
            statement.setString(3, appointment.getDescription());
            statement.setString(4, appointment.getLocation());
            statement.setString(5, appointment.getContact());
            statement.setString(6, appointment.getType());
            statement.setString(7, appointment.getUrl());
            
            ZonedDateTime startZDT = appointment.getStart().withZoneSameInstant(ZoneId.of("UTC"));
            ZonedDateTime endZDT = appointment.getEnd().withZoneSameInstant(ZoneId.of("UTC"));
            statement.setTimestamp(8, Timestamp.valueOf(startZDT.toLocalDateTime()));
            statement.setTimestamp(9, Timestamp.valueOf(endZDT.toLocalDateTime()));
            
            statement.setString(10, loggedUser.userName);
            statement.setInt(11, appointment.getAppointmentId());
            
            statement.executeUpdate();
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public Appointment getUpcomingAppointments() {
        String getUpcomingApptSQL = "SELECT customer.customerName, appointment.* FROM appointment "
                + "JOIN customer ON appointment.customerId = customer.customerId "
                + "WHERE start BETWEEN ? AND (NOW() + INTERVAL 15 MINUTE)";
        
        Appointment upcomingAppt = new Appointment();
        try {
            PreparedStatement stmt = conn.prepareStatement(getUpcomingApptSQL);
            ZonedDateTime localZT = ZonedDateTime.now(zId);
            ZonedDateTime zdtUTC = localZT.withZoneSameInstant(ZoneId.of("UTC"));
            LocalDateTime localUTC = zdtUTC.toLocalDateTime();
            stmt.setTimestamp(1, Timestamp.valueOf(localUTC));
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Customer cust = new Customer();
                cust.setCustomerName(rs.getString("customerName"));
                upcomingAppt.setCustomer(cust);
                upcomingAppt.setAppointmentId(rs.getInt("appointmentId"));
                upcomingAppt.setCustomerId(rs.getInt("customerId"));
                upcomingAppt.setUserId(rs.getInt("userId"));
                upcomingAppt.setTitle(rs.getString("title"));
                LocalDateTime startUTC = rs.getTimestamp("start").toLocalDateTime();
                ZonedDateTime startZDT = ZonedDateTime.ofInstant(startUTC.toInstant(ZoneOffset.UTC), zId);
                upcomingAppt.setStart(startZDT);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return upcomingAppt;
    }
    
}