package com.path2serverless.connectedcar.shared.services;

import java.util.List;
import com.path2serverless.connectedcar.shared.data.entities.Appointment;

public interface IAppointmentService {

  public String createAppointment(Appointment appointment);
  
  public void deleteAppointment(String appointmentId);
  
  public Appointment getAppointment(String appointmentId);
  
  public List<Appointment> getRegistrationAppointments(String username, String vin);
  
  public List<Appointment> getTimeslotAppointments(String dealerId, String serviceDateHour);

  public void batchUpdate(List<Appointment> appointments);
  
}
