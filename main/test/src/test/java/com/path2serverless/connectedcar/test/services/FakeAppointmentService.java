package com.path2serverless.connectedcar.test.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import com.path2serverless.connectedcar.shared.data.entities.Appointment;
import com.path2serverless.connectedcar.shared.services.IAppointmentService;

public class FakeAppointmentService implements IAppointmentService {

  private Map<String,Appointment> appointments = new HashMap<String,Appointment>();

  @Override
  public String createAppointment(Appointment appointment) {
    if (appointment == null || !appointment.validate())
      throw new IllegalArgumentException();

    LocalDateTime now = LocalDateTime.now();
    
    appointment.setAppointmentId(UUID.randomUUID().toString());
    appointment.setCreateDateTime(now);
    appointment.setUpdateDateTime(now);

    appointments.put(appointment.getAppointmentId(), appointment);   

    return appointment.getAppointmentId();
  }

  @Override
  public void deleteAppointment(String appointmentId) {
    if (StringUtils.isBlank(appointmentId))
      throw new IllegalArgumentException();
    
    if (appointments.containsKey(appointmentId)) {
      appointments.remove(appointmentId);
    }
  }

  @Override
  public Appointment getAppointment(String appointmentId) {
    if (StringUtils.isBlank(appointmentId))
      throw new IllegalArgumentException();

    return appointments.get(appointmentId);
  }

  @Override
  public List<Appointment> getRegistrationAppointments(String username, String vin) {
    if (StringUtils.isBlank(username) || StringUtils.isBlank(vin))
      throw new IllegalArgumentException();

    return appointments.values()
      .stream()
      .filter(p -> p.getRegistrationKey().getUsername().equals(username) && p.getRegistrationKey().getVin().equals(vin))
      .collect(Collectors.toList());
  }

  @Override
  public List<Appointment> getTimeslotAppointments(String dealerId, String serviceDateHour) {
    if (StringUtils.isBlank(dealerId) || StringUtils.isBlank(serviceDateHour))
      throw new IllegalArgumentException();
    
    return appointments.values()
      .stream()
      .filter(p -> p.getTimeslotKey().getDealerId().equals(dealerId) && p.getTimeslotKey().getServiceDateHour().equals(serviceDateHour))
      .collect(Collectors.toList());

  }

  public void batchUpdate(List<Appointment> appointments) {
    if (appointments == null)
      throw new IllegalArgumentException();

  }
}
