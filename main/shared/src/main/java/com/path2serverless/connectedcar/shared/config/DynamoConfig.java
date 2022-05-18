package com.path2serverless.connectedcar.shared.config;

public class DynamoConfig {
  
  private String dealerTableName;
  private String timeslotTableName;
  private String appointmentTableName;
  private String customerTableName;
  private String registrationTableName;
  private String vehicleTableName;
  private String eventTableName;
  
  public DynamoConfig(
      String dealerTableName, String timeslotTableName, String appointmentTableName,
      String customerTableName, String registrationTableName,
      String vehicleTableName, String eventTableName) {

    this.dealerTableName = dealerTableName;
    this.timeslotTableName = timeslotTableName;
    this.appointmentTableName = appointmentTableName;
    this.customerTableName = customerTableName;
    this.registrationTableName = registrationTableName;
    this.vehicleTableName = vehicleTableName;
    this.eventTableName = eventTableName;
  }
  
  public String getTableName(TableEnum tableName) {
    if (tableName == TableEnum.DEALER)
      return dealerTableName;
    else if (tableName == TableEnum.TIMESLOT)
      return timeslotTableName;
    else if (tableName == TableEnum.APPOINTMENT)
      return appointmentTableName;
    else if (tableName == TableEnum.CUSTOMER)
      return customerTableName;
    else if (tableName == TableEnum.REGISTRATION)
        return registrationTableName;
    else if (tableName == TableEnum.VEHICLE)
      return vehicleTableName;
    else if (tableName == TableEnum.EVENT)
      return eventTableName;
    else
      throw new IllegalArgumentException();
  }
}
