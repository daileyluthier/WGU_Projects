/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Appointment;
import Model.User;
import java.time.LocalDateTime;
import javafx.collections.ObservableList;

/**
 *
 * @author daileyluthier
 */
public interface AppointmentDAOInterface {
    
    public int addAppointment(Appointment appointment);
    
    public ObservableList<Appointment> getAllAppointments();

    public ObservableList<Appointment> getAppointmentsByWeek();

    public ObservableList<Appointment> getAppointmentsByMonth();

    public Appointment getAppointment(int appointmentId);
    
    public ObservableList<Appointment> getAppointmentsByUser();
    
    public ObservableList<Appointment> getAppointmentsWithinWorkDay(LocalDateTime start, LocalDateTime end);
    
    public ObservableList<Appointment> getOverlappingAppointments(LocalDateTime start, LocalDateTime end);
    
    public void deleteAppointment(Appointment appointment);
    
    public void updateAppointment(Appointment appointment);
    
    public Appointment getUpcomingAppointments();
}

