package com.path2serverless.connectedcar.shared.data.entities;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.path2serverless.connectedcar.shared.data.enums.EventCodeEnum;

public class Event extends BaseEntity {
	
  private String vin = null;
  private Long timestamp = null;
  private EventCodeEnum eventCode = null;

  
  @JsonProperty("vin")
  public String getVin() {
    return vin;
  }

  public void setVin(String vin) {
    this.vin = vin;
  }

  @JsonProperty("timestamp")
  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  @JsonProperty("eventCode")
  public EventCodeEnum getEventCode() {
    return eventCode;
  }

  public void setEventCode(EventCodeEnum eventCode) {
    this.eventCode = eventCode;
  }
  
  public boolean validate() {
    return StringUtils.isNotBlank(vin) &&
           timestamp != null &&
           eventCode != null;
  }
}
