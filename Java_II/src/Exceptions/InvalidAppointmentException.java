/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

/**
 *
 * @author daileyluthier
 */
public class InvalidAppointmentException extends Exception{
    public InvalidAppointmentException(String e) {
        super(e);
}
}
