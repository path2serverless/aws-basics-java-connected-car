package com.path2serverless.connectedcar.services.data.items;

import java.time.LocalDateTime;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@DynamoDbBean
public abstract class BaseItem {

  public final static String VEHICLE_REGISTRATION_INDEX = "VehicleRegistrationIndex";
  public final static String REGISTRATION_APPOINTMENT_INDEX = "RegistrationAppointmentIndex";
  public final static String TIMESLOT_APPOINTMENT_INDEX = "TimeslotAppointmentIndex";

  public final static String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

  protected LocalDateTime createDateTime = null;
  protected LocalDateTime updateDateTime = null;

  @DynamoDbAttribute("createDateTime")
  public LocalDateTime getCreateDateTime() {
    return createDateTime;
  }
  
  public void setCreateDateTime(LocalDateTime createDateTime) {
    this.createDateTime = createDateTime;
  }
  
  @DynamoDbAttribute("updateDateTime")
  public LocalDateTime getUpdateDateTime() {
    return updateDateTime;
  }
  
  public void setUpdateDateTime(LocalDateTime updateDateTime) {
    this.updateDateTime = updateDateTime;
  }
}
