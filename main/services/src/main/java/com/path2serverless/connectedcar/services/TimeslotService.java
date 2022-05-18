package com.path2serverless.connectedcar.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import com.google.inject.Inject;
import com.path2serverless.connectedcar.services.context.IServiceContext;
import com.path2serverless.connectedcar.services.data.items.TimeslotItem;
import com.path2serverless.connectedcar.services.translator.ITranslator;
import com.path2serverless.connectedcar.shared.data.entities.Timeslot;
import com.path2serverless.connectedcar.shared.services.ITimeslotService;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchWriteItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.WriteBatch;
import software.amazon.awssdk.enhanced.dynamodb.model.WriteBatch.Builder;

public class TimeslotService extends BaseService implements ITimeslotService {

  @Inject
  public TimeslotService(IServiceContext serviceContext, ITranslator translatorService) {
    super(serviceContext, translatorService);
  }
  
  @Override
  public void createTimeslot(Timeslot timeslot) {
    if (timeslot == null || !timeslot.validate())
      throw new IllegalArgumentException();

    TimeslotItem item = getTranslator().translate(timeslot);
    
    LocalDateTime now = LocalDateTime.now();

    DynamoDbTable<TimeslotItem> table = getTimeslotTable();

    item.setCreateDateTime(now);
    item.setUpdateDateTime(now);
    
    table.putItem(item);
  }
  
  @Override
  public void deleteTimeslot(String dealerId, String serviceDateHour) {
    if (StringUtils.isBlank(dealerId) || StringUtils.isBlank(serviceDateHour))
      throw new IllegalArgumentException();
    
    DynamoDbTable<TimeslotItem> table = getTimeslotTable();
    
    TimeslotItem item = table.getItem(Key.builder().partitionValue(dealerId).sortValue(serviceDateHour).build());
    
    if (item != null) {
      table.deleteItem(item);
    }
  }
  
  @Override
  public Timeslot getTimeslot(String dealerId, String serviceDateHour) {
    if (StringUtils.isBlank(dealerId) || StringUtils.isBlank(serviceDateHour))
      throw new IllegalArgumentException();
    
    DynamoDbTable<TimeslotItem> table = getTimeslotTable();
    TimeslotItem item = table.getItem(Key.builder().partitionValue(dealerId).sortValue(serviceDateHour).build());

    return getTranslator().translate(item);
  }

  @Override
  public List<Timeslot> getTimeslots(String dealerId, String startDate, String endDate) {
    if (StringUtils.isBlank(dealerId) || StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate))
      throw new IllegalArgumentException();
    
    DynamoDbTable<TimeslotItem> table = getTimeslotTable();
    
    QueryConditional query = QueryConditional
      .keyEqualTo(Key.builder().partitionValue(dealerId)
      .build());

    QueryEnhancedRequest request = QueryEnhancedRequest.builder()
      .queryConditional(query)
      .build();
    
    // have to filter in code because filter expression for request can't be used for range key
    PageIterable<TimeslotItem> pages = table.query(request);
    List<TimeslotItem> items = getItems(pages);

    return items
      .stream()
      .filter(p -> p.getServiceDateHour().compareTo(startDate) >= 0 && p.getServiceDateHour().compareTo(endDate) <= 0)
      .map(p -> getTranslator().translate(p))
      .collect(Collectors.toList());
  }

  public void batchUpdate(List<Timeslot> timeslots) {
    if (timeslots == null)
      throw new IllegalArgumentException();

    DynamoDbTable<TimeslotItem> table = getTimeslotTable();

    Builder<TimeslotItem> batchBuilder = WriteBatch.builder(TimeslotItem.class).mappedTableResource(table);

    for (Timeslot timeslot : timeslots) {
      batchBuilder.addPutItem(p -> p.item(getTranslator().translate(timeslot)));
    }

    BatchWriteItemEnhancedRequest request = 
      BatchWriteItemEnhancedRequest
        .builder()
        .writeBatches(batchBuilder.build())
        .build();

    getServiceContext().getDynamoDbClient().batchWriteItem(request);
  }
}
