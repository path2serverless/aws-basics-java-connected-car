package com.path2serverless.connectedcar.shared.orchestrators;

import java.util.List;

import com.path2serverless.connectedcar.shared.data.entities.Appointment;
import com.path2serverless.connectedcar.shared.data.entities.Event;
import com.path2serverless.connectedcar.shared.data.entities.Vehicle;

public interface ICustomerOrchestrator {

  public String createAppointment(String username, Appointment appointment);

  public Vehicle getVehicle(String username, String vin);

  public List<Event> getEvents(String username, String vin);

}