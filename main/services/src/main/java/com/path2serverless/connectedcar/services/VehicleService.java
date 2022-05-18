package com.path2serverless.connectedcar.services;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import com.google.inject.Inject;
import com.path2serverless.connectedcar.services.context.IServiceContext;
import com.path2serverless.connectedcar.services.data.items.VehicleItem;
import com.path2serverless.connectedcar.services.translator.ITranslator;
import com.path2serverless.connectedcar.shared.data.entities.Vehicle;
import com.path2serverless.connectedcar.shared.services.IVehicleService;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchWriteItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.WriteBatch;
import software.amazon.awssdk.enhanced.dynamodb.model.WriteBatch.Builder;

public class VehicleService extends BaseService implements IVehicleService {

  @Inject
  public VehicleService(IServiceContext serviceContext, ITranslator translatorService) {
    super(serviceContext, translatorService);
  }
  
  @Override
  public void createVehicle(Vehicle vehicle) {
    if (vehicle == null || !vehicle.validate())
      throw new IllegalArgumentException();

    VehicleItem item = getTranslator().translate(vehicle);
    
    DynamoDbTable<VehicleItem> table = getVehicleTable();

    GetItemEnhancedRequest request = GetItemEnhancedRequest.builder()
      .consistentRead(true)
      .key(Key.builder().partitionValue(vehicle.getVin()).build())
      .build();

    VehicleItem existing = table.getItem(request);

    if (existing != null)
      throw new IllegalArgumentException();

    LocalDateTime now = LocalDateTime.now();

    item.setCreateDateTime(now);
    item.setUpdateDateTime(now);
    
    table.putItem(item);
    table.getItem(request);
  }
  
  @Override
  public void deleteVehicle(String vin) {
    if (StringUtils.isBlank(vin))
      throw new IllegalArgumentException();
    
    DynamoDbTable<VehicleItem> table = getVehicleTable();
    
    VehicleItem item = table.getItem(Key.builder().partitionValue(vin).build());
    
    if (item != null) {
      table.deleteItem(item);
    }
  }
  
  @Override
  public Vehicle getVehicle(String vin) {
    if (StringUtils.isBlank(vin))
      throw new IllegalArgumentException();
    
    DynamoDbTable<VehicleItem> table = getVehicleTable();
    VehicleItem item = table.getItem(Key.builder().partitionValue(vin).build());

    return getTranslator().translate(item);
  }

  @Override
  public boolean validatePin(String vin, String vehiclePin) {
    if (StringUtils.isBlank(vin) || StringUtils.isBlank(vehiclePin))
      throw new IllegalArgumentException();
    
    DynamoDbTable<VehicleItem> table = getVehicleTable();

    VehicleItem item = table.getItem(Key.builder().partitionValue(vin).build());
    
    return item != null && item.getVehiclePin().equals(vehiclePin);
  }

  public void batchUpdate(List<Vehicle> vehicles) {
    if (vehicles == null)
      throw new IllegalArgumentException();

    DynamoDbTable<VehicleItem> table = getVehicleTable();

    Builder<VehicleItem> batchBuilder = WriteBatch.builder(VehicleItem.class).mappedTableResource(table);

    for (Vehicle vehicle : vehicles) {
      batchBuilder.addPutItem(p -> p.item(getTranslator().translate(vehicle)));
    }

    BatchWriteItemEnhancedRequest request = 
      BatchWriteItemEnhancedRequest
        .builder()
        .writeBatches(batchBuilder.build())
        .build();

    getServiceContext().getDynamoDbClient().batchWriteItem(request);
  }
}
