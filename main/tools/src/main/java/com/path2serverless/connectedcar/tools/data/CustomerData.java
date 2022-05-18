package com.path2serverless.connectedcar.tools.data;

import com.opencsv.bean.CsvBindByPosition;
import com.path2serverless.connectedcar.shared.data.attributes.Colors;
import com.path2serverless.connectedcar.shared.data.entities.Customer;
import com.path2serverless.connectedcar.shared.data.entities.Registration;
import com.path2serverless.connectedcar.shared.data.entities.Vehicle;
import com.path2serverless.connectedcar.shared.data.enums.StatusCodeEnum;
import com.path2serverless.connectedcar.shared.data.enums.VehicleCodeEnum;

public class CustomerData {
  
  @CsvBindByPosition(position = 0)
  private String username = null;

  @CsvBindByPosition(position = 1)
  private String firstname = null;

  @CsvBindByPosition(position = 2)
  private String lastname = null;

  @CsvBindByPosition(position = 3)
  private String phoneNumber = null;

  @CsvBindByPosition(position = 4)
  private String vin = null;

  @CsvBindByPosition(position = 5)
  private String vehiclePin = null;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getVin() {
    return vin;
  }

  public void setVin(String vin) {
    this.vin = vin;
  }

  public String getVehiclePin() {
    return vehiclePin;
  }

  public void setVehiclePin(String vehiclePin) {
    this.vehiclePin = vehiclePin;
  }

  public Customer GetCustomer()
  {
    Customer customer = new Customer();

    customer.setUsername(username);
    customer.setFirstname(firstname);
    customer.setLastname(lastname);
    customer.setPhoneNumber(phoneNumber);

    return customer;
  }

  public Vehicle GetVehicle()
  {
    Vehicle vehicle = new Vehicle();

    vehicle.setVin(vin);
    vehicle.setColors(new Colors());
    vehicle.getColors().setInterior("Black");
    vehicle.getColors().setExterior("Silver");
    vehicle.setVehicleCode(VehicleCodeEnum.TURBO);
    vehicle.setVehiclePin(vehiclePin);

    return vehicle;
  }

  public Registration GetRegistration()
  {
    Registration registration = new Registration();

    registration.setUsername(username);
    registration.setVin(vin);
    registration.setStatusCode(StatusCodeEnum.ACTIVE);

    return registration;
  }
}