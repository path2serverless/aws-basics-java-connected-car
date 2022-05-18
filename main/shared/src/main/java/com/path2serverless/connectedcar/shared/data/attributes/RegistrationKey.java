package com.path2serverless.connectedcar.shared.data.attributes;

import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.path2serverless.connectedcar.shared.data.Validatable;

public class RegistrationKey extends Validatable {

  private String username = null;
  private String vin = null;
  
  
  @JsonProperty("username")
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @JsonProperty("vin")
  public String getVin() {
    return vin;
  }

  public void setVin(String vin) {
    this.vin = vin;
  }

	@Override
  public boolean validate() {
    return StringUtils.isNotBlank(username) && 
           StringUtils.isNotBlank(vin);
  }
}
