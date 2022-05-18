package com.path2serverless.connectedcar.shared.data.attributes;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.path2serverless.connectedcar.shared.data.Validatable;

public class Address extends Validatable {
  
  private String streetAddress = null;
  private String city = null;
  private String state = null;
  private String zipCode = null;
  
  
  @JsonProperty("streetAddress")
  public String getStreetAddress() {
    return streetAddress;
  }
  
  public void setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
  }
  
  @JsonProperty("city")
  public String getCity() {
    return city;
  }
  
  public void setCity(String city) {
    this.city = city;
  }
  
  @JsonProperty("state")
  public String getState() {
    return state;
  }
  
  public void setState(String state) {
    this.state = state;
  }
  
  @JsonProperty("zipCode")
  public String getZipCode() {
    return zipCode;
  }
  
  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  @Override
  public boolean validate() {
    return StringUtils.isNotBlank(streetAddress) &&
           StringUtils.isNotBlank(city) &&
           StringUtils.isNotBlank(state) &&
           StringUtils.isNotBlank(zipCode);
  }
}
