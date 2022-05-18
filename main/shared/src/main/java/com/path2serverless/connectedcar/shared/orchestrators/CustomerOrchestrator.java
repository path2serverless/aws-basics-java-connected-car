package com.path2serverless.connectedcar.shared.orchestrators;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.path2serverless.connectedcar.shared.data.entities.Appointment;
import com.path2serverless.connectedcar.shared.data.entities.Event;
import com.path2serverless.connectedcar.shared.data.entities.Registration;
import com.path2serverless.connectedcar.shared.data.entities.Vehicle;
import com.path2serverless.connectedcar.shared.services.IAppointmentService;
import com.path2serverless.connectedcar.shared.services.IEventService;
import com.path2serverless.connectedcar.shared.services.IRegistrationService;
import com.path2serverless.connectedcar.shared.services.IVehicleService;

public class CustomerOrchestrator implements ICustomerOrchestrator {

  private IRegistrationService registrationService;
  private IAppointmentService appointmentService;
  private IVehicleService vehicleService;
  private IEventService eventService;

  @Inject
  public CustomerOrchestrator(
      IRegistrationService registrationService,
      IAppointmentService appointmentService,
      IVehicleService vehicleService,
      IEventService eventService) {

    this.registrationService = registrationService;
    this.appointmentService = appointmentService;
    this.vehicleService = vehicleService;
    this.eventService = eventService;
  }

  public String createAppointment(String username, Appointment appointment) {
    Registration registration = registrationService.getRegistration(username, appointment.getRegistrationKey().getVin());

    if (registration != null) {
        appointment.getRegistrationKey().setUsername(username);
        return appointmentService.createAppointment(appointment);
    }

    return null;
  }

  public Vehicle getVehicle(String username, String vin) {
    Registration registration = registrationService.getRegistration(username, vin);

    if (registration != null) {
      return vehicleService.getVehicle(registration.getVin());
    }

    return null;
  }

  public List<Event> getEvents(String username, String vin) {
    Registration registration = registrationService.getRegistration(username, vin);
    List<Event> events = new ArrayList<Event>();

    if (registration != null) {
      events.addAll(eventService.getEvents(registration.getVin()));
    }

    return events;
  }
}
