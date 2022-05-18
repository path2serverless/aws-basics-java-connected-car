package com.path2serverless.connectedcar.shared.data.entities;

import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Timeslot extends BaseEntity implements Comparable<Timeslot> {
	
  private String dealerId = null;
  private String serviceDateHour = null;
  private int serviceBayCount = 0;

  @JsonProperty("dealerId")
  public String getDealerId() {
    return dealerId;
  }
  
  public void setDealerId(String dealerId) {
    this.dealerId = dealerId;
  }
  
  @JsonProperty("serviceDateHour")
  public String getServiceDateHour() {
    return serviceDateHour;
  }
  
  public void setServiceDateHour(String serviceDateHour) {
    this.serviceDateHour = serviceDateHour;
  }
  
  @JsonProperty("serviceBayCount")
  public int getServiceBayCount() {
    return serviceBayCount;
  }

  public void setServiceBayCount(int serviceBayCount) {
    this.serviceBayCount = serviceBayCount;
  }

  public boolean validate() {
    return StringUtils.isNotBlank(dealerId) &&
           serviceDateHour != null;
  }

  @Override
  public int compareTo(Timeslot otherDate) {
    return serviceDateHour.compareTo(otherDate.getServiceDateHour());
  }
}
