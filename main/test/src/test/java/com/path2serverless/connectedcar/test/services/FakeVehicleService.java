package com.path2serverless.connectedcar.test.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import com.path2serverless.connectedcar.shared.data.entities.Vehicle;
import com.path2serverless.connectedcar.shared.services.IVehicleService;

public class FakeVehicleService implements IVehicleService {

  private Map<String,Vehicle> vehicles = new HashMap<String,Vehicle>();

  @Override
  public void createVehicle(Vehicle vehicle) {
    if (vehicle == null || !vehicle.validate())
      throw new IllegalArgumentException();

    if (vehicles.containsKey(vehicle.getVin()))
      throw new IllegalArgumentException();

    LocalDateTime now = LocalDateTime.now();

    vehicle.setCreateDateTime(now);
    vehicle.setUpdateDateTime(now);

    vehicles.put(vehicle.getVin(), vehicle);    
  }
  
  @Override
  public void deleteVehicle(String vin) {
    if (StringUtils.isBlank(vin))
      throw new IllegalArgumentException();
    
    if (vehicles.containsKey(vin)) {
      vehicles.remove(vin);
    }
  }
  
  @Override
  public Vehicle getVehicle(String vin) {
    if (StringUtils.isBlank(vin))
      throw new IllegalArgumentException();

    return vehicles.get(vin);    
  }

  @Override
  public boolean validatePin(String vin, String vehiclePin) {
    if (StringUtils.isBlank(vin) || StringUtils.isBlank(vehiclePin))
      throw new IllegalArgumentException();

    if (vehicles.containsKey(vin)) {
      return vehicles.get(vin).getVehiclePin().equals(vehiclePin);
    }    

    return false;
  }

  public void batchUpdate(List<Vehicle> vehicles) {
    if (vehicles == null)
      throw new IllegalArgumentException();

  }
}
