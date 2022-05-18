package com.path2serverless.connectedcar.shared.services;

import java.util.List;

import com.path2serverless.connectedcar.shared.data.entities.Registration;
import com.path2serverless.connectedcar.shared.data.updates.RegistrationPatch;

public interface IRegistrationService {
  
  public void createRegistration(Registration registration);

  public void updateRegistration(RegistrationPatch patch);
  
  public void deleteRegistration(String username, String vin);

  public Registration getRegistration(String username, String vin);

  public List<Registration> getCustomerRegistrations(String username);

  public List<Registration> getVehicleRegistrations(String vin);

  public void batchUpdate(List<Registration> registrations);

}
