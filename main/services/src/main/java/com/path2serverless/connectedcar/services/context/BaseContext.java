package com.path2serverless.connectedcar.services.context;

import com.path2serverless.connectedcar.shared.config.ServiceConfig;

public abstract class BaseContext {
  
  protected static final String REGION = "AWS_REGION";
  
  protected static final String ACCESS_KEY = "AccessKey";
  protected static final String SECRET_KEY = "SecretKey";
  
  protected static final String DEALER_TABLE_NAME = "DealerTableName";
  protected static final String TIMESLOT_TABLE_NAME = "TimeslotTableName";
  protected static final String APPOINTMENT_TABLE_NAME = "AppointmentTableName";
  protected static final String CUSTOMER_TABLE_NAME = "CustomerTableName";
  protected static final String REGISTRATION_TABLE_NAME = "RegistrationTableName";
  protected static final String VEHICLE_TABLE_NAME = "VehicleTableName";
  protected static final String EVENT_TABLE_NAME = "EventTableName";
  
  protected static final String USER_POOL_ID = "UserPoolId";
  protected static final String CLIENT_ID = "ClientId";
  protected static final String CLIENT_SECRET = "ClientSecret";
  protected static final String USER_QUEUE_URN = "UserQueueUrn";
  
  public abstract ServiceConfig getConfig();
  
}
