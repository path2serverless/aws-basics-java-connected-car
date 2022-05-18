package com.path2serverless.connectedcar.services.data.converters;

import com.path2serverless.connectedcar.shared.data.attributes.RegistrationKey;

import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class RegistrationKeyConverter implements AttributeConverter<RegistrationKey> {

  private static final String DELIMITER = "#";
  
  @Override
  public AttributeValue transformFrom(RegistrationKey input) {
    try {
      return AttributeValue.builder().s(input.getUsername() + DELIMITER + input.getVin()).build();
    } 
    catch (Exception e) {
      e.printStackTrace();
    }
    
    return null;
  }

  @Override
  public RegistrationKey transformTo(AttributeValue input) {
    try {
      String[] parts = input.s().split(DELIMITER);
      RegistrationKey key = new RegistrationKey();

      key.setUsername(parts[0]);
      key.setVin(parts[1]);
      
      return key;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    
    return null;
  }

  @Override
  public EnhancedType<RegistrationKey> type() {
    return EnhancedType.of(RegistrationKey.class);
  }

  @Override
  public AttributeValueType attributeValueType() {
    return AttributeValueType.S;
  }
}
