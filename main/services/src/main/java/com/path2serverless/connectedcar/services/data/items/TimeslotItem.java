package com.path2serverless.connectedcar.services.data.items;

import org.apache.commons.lang3.StringUtils;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbIgnore;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
public class TimeslotItem extends BaseItem implements Comparable<TimeslotItem> {
	
  private String dealerId = null;
  private String serviceDateHour = null;
  private int serviceBayCount = 0;

  @DynamoDbPartitionKey
  public String getDealerId() {
    return dealerId;
  }
  
  public void setDealerId(String dealerId) {
    this.dealerId = dealerId;
  }
  
  @DynamoDbSortKey
  public String getServiceDateHour() {
    return serviceDateHour;
  }
  
  public void setServiceDateHour(String serviceDateHour) {
    this.serviceDateHour = serviceDateHour;
  }
  
  @DynamoDbAttribute("serviceBayCount")
  public int getServiceBayCount() {
    return serviceBayCount;
  }

  public void setServiceBayCount(int serviceBayCount) {
    this.serviceBayCount = serviceBayCount;
  }

  @DynamoDbIgnore
  public boolean validate() {
    return StringUtils.isNotBlank(dealerId) &&
           serviceDateHour != null;
  }

  @Override
  @DynamoDbIgnore
  public int compareTo(TimeslotItem otherDate) {
    return serviceDateHour.compareTo(otherDate.getServiceDateHour());
  }
}
