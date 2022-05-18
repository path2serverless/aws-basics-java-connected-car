package com.path2serverless.connectedcar.shared.data.attributes;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.path2serverless.connectedcar.shared.data.Validatable;

public class Colors extends Validatable {

  private String exterior = null;
  private String interior = null;
  
  
  @JsonProperty("exterior")
  public String getExterior() {
    return exterior;
  }
  
  public void setExterior(String exterior) {
    this.exterior = exterior;
  }
  
  @JsonProperty("interior")
  public String getInterior() {
    return interior;
  }
  
  public void setInterior(String interior) {
    this.interior = interior;
  }

  @Override
  public boolean validate() {
    return StringUtils.isNotBlank(exterior) &&
           StringUtils.isNotBlank(interior);
  }
}
