package com.path2serverless.connectedcar.services.data.converters;

import com.path2serverless.connectedcar.shared.data.attributes.TimeslotKey;

import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class TimeslotKeyConverter implements AttributeConverter<TimeslotKey> {

  private static final String DELIMITER = "#";

  @Override
  public AttributeValue transformFrom(TimeslotKey input) {
    try {
      return AttributeValue.builder().s(input.getDealerId() + DELIMITER + input.getServiceDateHour()).build();
    } 
    catch (Exception e) {
      e.printStackTrace();
    }
    
    return null;
  }

  @Override
  public TimeslotKey transformTo(AttributeValue input) {
    try {
      String[] parts = input.s().split(DELIMITER);
      TimeslotKey key = new TimeslotKey();
      
      key.setDealerId(parts[0]);
      key.setServiceDateHour(parts[1]);
      
      return key;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    
    return null;
  }

  @Override
  public EnhancedType<TimeslotKey> type() {
    return EnhancedType.of(TimeslotKey.class);
  }

  @Override
  public AttributeValueType attributeValueType() {
    return AttributeValueType.S;
  }
}
