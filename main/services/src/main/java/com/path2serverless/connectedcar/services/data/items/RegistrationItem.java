package com.path2serverless.connectedcar.services.data.items;

import com.path2serverless.connectedcar.shared.data.enums.StatusCodeEnum;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondarySortKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
public class RegistrationItem extends BaseItem {

  private String username = null;
  private String vin = null;
  private StatusCodeEnum statusCode = null;

  @DynamoDbPartitionKey
  @DynamoDbSecondarySortKey(indexNames = VEHICLE_REGISTRATION_INDEX)
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @DynamoDbSortKey
  @DynamoDbSecondaryPartitionKey(indexNames = VEHICLE_REGISTRATION_INDEX)
  public String getVin() {
    return vin;
  }

  public void setVin(String vin) {
    this.vin = vin;
  }
  
  @DynamoDbAttribute("statusCode")
  public StatusCodeEnum getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(StatusCodeEnum statusCode) {
    this.statusCode = statusCode;
  }
}
