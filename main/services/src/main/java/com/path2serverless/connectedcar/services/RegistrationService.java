package com.path2serverless.connectedcar.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import com.google.inject.Inject;
import com.path2serverless.connectedcar.services.context.IServiceContext;
import com.path2serverless.connectedcar.services.data.items.BaseItem;
import com.path2serverless.connectedcar.services.data.items.RegistrationItem;
import com.path2serverless.connectedcar.services.translator.ITranslator;
import com.path2serverless.connectedcar.shared.data.entities.Registration;
import com.path2serverless.connectedcar.shared.data.enums.StatusCodeEnum;
import com.path2serverless.connectedcar.shared.data.updates.RegistrationPatch;
import com.path2serverless.connectedcar.shared.services.IRegistrationService;

import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchWriteItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.WriteBatch;
import software.amazon.awssdk.enhanced.dynamodb.model.WriteBatch.Builder;

public class RegistrationService extends BaseService implements IRegistrationService {

  @Inject
  public RegistrationService(IServiceContext serviceContext, ITranslator translatorService) {
    super(serviceContext, translatorService);
  }
  
  @Override
  public void createRegistration(Registration registration) {
    if (registration == null || !registration.validate())
      throw new IllegalArgumentException();

    RegistrationItem item = getTranslator().translate(registration);
    
    DynamoDbTable<RegistrationItem> table = getRegistrationTable();

    GetItemEnhancedRequest request = GetItemEnhancedRequest.builder()
      .consistentRead(true)
      .key(Key.builder().partitionValue(item.getUsername()).sortValue(item.getVin()).build())
      .build();

    RegistrationItem existing = table.getItem(request);

    if (existing != null)
      throw new IllegalArgumentException();

    LocalDateTime now = LocalDateTime.now();

    item.setStatusCode(StatusCodeEnum.ACTIVE);
    item.setCreateDateTime(now);
    item.setUpdateDateTime(now);

    Builder<RegistrationItem> builder = WriteBatch.builder(RegistrationItem.class).mappedTableResource(table);

    DynamoDbIndex<RegistrationItem> index = table.index(BaseItem.VEHICLE_REGISTRATION_INDEX);

    QueryConditional previousQuery = QueryConditional
      .keyEqualTo(Key.builder().partitionValue(item.getVin())
      .build());

    QueryEnhancedRequest previousRequest = QueryEnhancedRequest.builder()
      .queryConditional(previousQuery)
      .build();

    SdkIterable<Page<RegistrationItem>> pages = index.query(previousRequest);
    List<RegistrationItem> previousItems = getItems(PageIterable.create(pages));

    for (RegistrationItem previousItem : previousItems) {
      previousItem.setStatusCode(StatusCodeEnum.INACTIVE);
      previousItem.setUpdateDateTime(now);
      builder.addPutItem(previousItem);
    }

    builder.addPutItem(item);

    BatchWriteItemEnhancedRequest batchRequest = BatchWriteItemEnhancedRequest.builder()
      .writeBatches(builder.build())
      .build();

    getServiceContext().getDynamoDbClient().batchWriteItem(batchRequest);

    table.getItem(request);
  }

  @Override
  public void updateRegistration(RegistrationPatch patch) {
    if (patch == null || !patch.validate())
      throw new IllegalArgumentException();
    
    LocalDateTime now = LocalDateTime.now();

    DynamoDbTable<RegistrationItem> table = getRegistrationTable();

    RegistrationItem item = table.getItem(Key.builder().partitionValue(patch.getUsername()).sortValue(patch.getVin()).build());
    
    if (item != null) {
      item.setStatusCode(patch.getStatusCode());
      item.setUpdateDateTime(now);

      table.putItem(item);
    }
  }

  
  @Override
  public void deleteRegistration(String username, String vin) {
    if (StringUtils.isBlank(username) || StringUtils.isBlank(vin))
      throw new IllegalArgumentException();
    
    DynamoDbTable<RegistrationItem> table = getRegistrationTable();
    
    RegistrationItem item = table.getItem(Key.builder().partitionValue(username).sortValue(vin).build());
    
    if (item != null) {
      table.deleteItem(item);
    }
  }
  
  @Override
  public Registration getRegistration(String username, String vin) {
    if (StringUtils.isBlank(username) || StringUtils.isBlank(vin))
      throw new IllegalArgumentException();
    
    DynamoDbTable<RegistrationItem> table = getRegistrationTable();
    RegistrationItem item = table.getItem(Key.builder().partitionValue(username).sortValue(vin).build());

    return getTranslator().translate(item);
  }

  @Override
  public List<Registration> getCustomerRegistrations(String username) {
    if (StringUtils.isBlank(username))
      throw new IllegalArgumentException();

    DynamoDbTable<RegistrationItem> table = getRegistrationTable();

    QueryConditional query = QueryConditional
        .keyEqualTo(Key.builder().partitionValue(username)
        .build());

    PageIterable<RegistrationItem> pages = table.query(query);
    List<RegistrationItem> items = getItems(PageIterable.create(pages));

    return items
      .stream()
      .filter(p -> p.getStatusCode() == StatusCodeEnum.ACTIVE)
      .map(p -> getTranslator().translate(p))
      .collect(Collectors.toList());
    
  }
  
  public List<Registration> getVehicleRegistrations(String vin) {
    if (StringUtils.isBlank(vin))
      throw new IllegalArgumentException();

    DynamoDbTable<RegistrationItem> table = getRegistrationTable();
    DynamoDbIndex<RegistrationItem> index = table.index(BaseItem.VEHICLE_REGISTRATION_INDEX);

    QueryConditional query = QueryConditional
      .keyEqualTo(Key.builder().partitionValue(vin)
      .build());

    QueryEnhancedRequest request = QueryEnhancedRequest.builder()
      .queryConditional(query)
      .build();

    SdkIterable<Page<RegistrationItem>> pages = index.query(request);
    List<RegistrationItem> items = getItems(PageIterable.create(pages));

    return items
      .stream()
      .filter(p -> p.getStatusCode() == StatusCodeEnum.ACTIVE)
      .map(p -> getTranslator().translate(p))
      .collect(Collectors.toList());
  }

  public void batchUpdate(List<Registration> registrations) {
    if (registrations == null)
      throw new IllegalArgumentException();

    DynamoDbTable<RegistrationItem> table = getRegistrationTable();

    Builder<RegistrationItem> batchBuilder = WriteBatch.builder(RegistrationItem.class).mappedTableResource(table);

    for (Registration registration : registrations) {
      batchBuilder.addPutItem(p -> p.item(getTranslator().translate(registration)));
    }

    BatchWriteItemEnhancedRequest request = 
      BatchWriteItemEnhancedRequest
        .builder()
        .writeBatches(batchBuilder.build())
        .build();

    getServiceContext().getDynamoDbClient().batchWriteItem(request);
  }
}
