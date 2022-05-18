package com.path2serverless.connectedcar.services.data.items;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class CustomerItem extends BaseItem {

  private String username = null;
  private String firstname = null;
  private String lastname = null;
  private String lastnameLower = null;
  private String phoneNumber = null;

  @DynamoDbPartitionKey
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
  
  @DynamoDbAttribute("lastnameLower")
  public String getLastnameLower() {
    return lastnameLower;
  }
  
  public void setLastnameLower(String lastnameLower) {
    this.lastnameLower = lastnameLower;
  }
  
  @DynamoDbAttribute("phoneNumber")
  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
}
