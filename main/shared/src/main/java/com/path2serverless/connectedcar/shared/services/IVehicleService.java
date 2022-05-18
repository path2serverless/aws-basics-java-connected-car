package com.path2serverless.connectedcar.shared.services;

import java.util.List;

import com.path2serverless.connectedcar.shared.data.entities.Vehicle;

public interface IVehicleService {
  
  public void createVehicle(Vehicle vehicle);
  
  public void deleteVehicle(String vin);
  
  public Vehicle getVehicle(String vin);
  
  public boolean validatePin(String vin, String vehiclePin);

  public void batchUpdate(List<Vehicle> timeslots);

}
