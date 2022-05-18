package com.path2serverless.connectedcar.shared.data.entities;

import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Customer extends BaseEntity {

  private String username = null;
  private String firstname = null;
  private String lastname = null;
  private String phoneNumber = null;

  @JsonProperty("username")
  public String getUsername() {
    return username;
  }
  
  public void setUsername(String username) {
    this.username = username;
  }
  
  @JsonProperty("firstname")
  public String getFirstname() {
    return firstname;
  }
  
  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }
  
  @JsonProperty("lastname")
  public String getLastname() {
    return lastname;
  }
  
  public void setLastname(String lastname) {
    this.lastname = lastname;
  }
  
  @JsonProperty("phoneNumber")
  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  @Override
  public boolean validate() {
    return StringUtils.isNotBlank(username) &&
           StringUtils.isNotBlank(firstname) &&
           StringUtils.isNotBlank(lastname) &&
           StringUtils.isNotBlank(phoneNumber);
  }
}
