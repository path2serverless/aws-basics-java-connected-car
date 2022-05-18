package com.path2serverless.connectedcar.shared.data.entities;

import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.path2serverless.connectedcar.shared.data.attributes.Address;
import com.path2serverless.connectedcar.shared.data.enums.StateCodeEnum;

public class Dealer extends BaseEntity {
	
  private String dealerId = null;
  private String name = null;
  private Address address = null;
  private StateCodeEnum stateCode = null;
  
  @JsonProperty("dealerId")
  public String getDealerId() {
    return dealerId;
  }
  
  public void setDealerId(String dealerId) {
    this.dealerId = dealerId;
  }
  
  @JsonProperty("name")
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  @JsonProperty("address")
  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }
  
  @JsonProperty("stateCode")
  public StateCodeEnum getStateCode() {
    return stateCode;
  }

  public void setStateCode(StateCodeEnum stateCode) {
    this.stateCode = stateCode;
  }
  
  @Override
  public boolean validate() {
    return StringUtils.isNotBlank(name) &&
           address != null && address.validate() &&
           stateCode != null;

  }
}
