package com.path2serverless.connectedcar.services.data.items;

import com.path2serverless.connectedcar.services.data.converters.ColorsConverter;
import com.path2serverless.connectedcar.shared.data.attributes.Colors;
import com.path2serverless.connectedcar.shared.data.enums.VehicleCodeEnum;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbConvertedBy;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class VehicleItem extends BaseItem {

  private String vin = null;
  private Colors colors = null;
  private String vehiclePin = null;
  private VehicleCodeEnum vehicleCode = null;

  @DynamoDbPartitionKey
  public String getVin() {
    return vin;
  }

  public void setVin(String vin) {
    this.vin = vin;
  }
  
  @DynamoDbAttribute("colors")
  @DynamoDbConvertedBy(ColorsConverter.class)
  public Colors getColors() {
    return colors;
  }

  public void setColors(Colors colors) {
    this.colors = colors;
  }

  @DynamoDbAttribute("vehiclePin")
  public String getVehiclePin() {
    return vehiclePin;
  }

  public void setVehiclePin(String vehiclePin) {
    this.vehiclePin = vehiclePin;
  }
    
  @DynamoDbAttribute("vehicleCode")
  public VehicleCodeEnum getVehicleCode() {
    return vehicleCode;
  }

  public void setVehicleCode(VehicleCodeEnum vehicleCode) {
    this.vehicleCode = vehicleCode;
  }
}
