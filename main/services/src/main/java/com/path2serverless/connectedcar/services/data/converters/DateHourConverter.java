package com.path2serverless.connectedcar.services.data.converters;

import java.text.SimpleDateFormat;
import java.util.Date;

import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class DateHourConverter implements AttributeConverter<Date> {

  public final static String DATE_HOUR_FORMAT = "yyyy-MM-dd-HH";

  private SimpleDateFormat formatter = new SimpleDateFormat(DATE_HOUR_FORMAT); 
  
  @Override
  public AttributeValue transformFrom(Date input) {
    try {
      return AttributeValue.builder().s(formatter.format(input)).build();
    } 
    catch (Exception e) {
      e.printStackTrace();
    }
    
    return null;
  }

  @Override
  public Date transformTo(AttributeValue input) {
    try {
      return formatter.parse(input.s());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    
    return null;
  }

  @Override
  public EnhancedType<Date> type() {
    return EnhancedType.of(Date.class);
  }

  @Override
  public AttributeValueType attributeValueType() {
    return AttributeValueType.S;
  }
}
