package com.path2serverless.connectedcar.test.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import com.path2serverless.connectedcar.shared.data.entities.Registration;
import com.path2serverless.connectedcar.shared.data.enums.StatusCodeEnum;
import com.path2serverless.connectedcar.shared.data.updates.RegistrationPatch;
import com.path2serverless.connectedcar.shared.services.IRegistrationService;

public class FakeRegistrationService implements IRegistrationService {

  private Map<String,Registration> registrations = new HashMap<String,Registration>();

  @Override
  public void createRegistration(Registration registration) {
    if (registration == null || !registration.validate())
      throw new IllegalArgumentException();

    String key = getKey(registration.getUsername(), registration.getVin());

    if (registrations.containsKey(key))
      throw new IllegalArgumentException();

    LocalDateTime now = LocalDateTime.now();

    registration.setStatusCode(StatusCodeEnum.ACTIVE);
    registration.setCreateDateTime(now);
    registration.setUpdateDateTime(now);

    registrations.put(key, registration);
  }

  @Override
  public void updateRegistration(RegistrationPatch patch) {
    if (patch == null || !patch.validate())
      throw new IllegalArgumentException();
    
    LocalDateTime now = LocalDateTime.now();

    String key = getKey(patch.getUsername(), patch.getVin());

    if (registrations.containsKey(key)) {
      Registration registration = registrations.get(key);

      registration.setStatusCode(patch.getStatusCode());
      registration.setUpdateDateTime(now);
    }
  }

  
  @Override
  public void deleteRegistration(String username, String vin) {
    if (StringUtils.isBlank(username) || StringUtils.isBlank(vin))
      throw new IllegalArgumentException();
    
    String key = getKey(username, vin);

    if (registrations.containsKey(key)) {
      registrations.remove(key);
    }
  }
  
  @Override
  public Registration getRegistration(String username, String vin) {
    if (StringUtils.isBlank(username) || StringUtils.isBlank(vin))
      throw new IllegalArgumentException();

    return registrations.get(getKey(username, vin));    
  }

  @Override
  public List<Registration> getCustomerRegistrations(String username) {
    if (StringUtils.isBlank(username))
      throw new IllegalArgumentException();

    return registrations.values()
      .stream()
      .filter(p -> p.getUsername().equals(username))
      .collect(Collectors.toList());
  }
  
  public List<Registration> getVehicleRegistrations(String vin) {
    if (StringUtils.isBlank(vin))
      throw new IllegalArgumentException();

    return registrations.values()
      .stream()
      .filter(p -> p.getVin().equals(vin))
      .collect(Collectors.toList());
 }

  public void batchUpdate(List<Registration> registrations) {
    if (registrations == null)
      throw new IllegalArgumentException();

  }

  private static String getKey(String username, String vin) {
    return username + "/" + vin;
  }
}
