package com.path2serverless.connectedcar.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import com.google.inject.Inject;
import com.path2serverless.connectedcar.services.context.IServiceContext;
import com.path2serverless.connectedcar.services.data.items.EventItem;
import com.path2serverless.connectedcar.services.translator.ITranslator;
import com.path2serverless.connectedcar.shared.data.entities.Event;
import com.path2serverless.connectedcar.shared.services.IEventService;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

public class EventService extends BaseService implements IEventService {

  @Inject
  public EventService(IServiceContext serviceContext, ITranslator translatorService) {
    super(serviceContext, translatorService);
  }
  
  @Override
  public void createEvent(Event event) {
    if (event == null || !event.validate())
      throw new IllegalArgumentException();

    EventItem item = getTranslator().translate(event);
    
    LocalDateTime now = LocalDateTime.now();

    DynamoDbTable<EventItem> table = getEventTable();
    
    item.setCreateDateTime(now);
    item.setUpdateDateTime(now);
    
    table.putItem(item);
  }

  @Override
  public void deleteEvent(String vin, long timestamp) {
    if (StringUtils.isBlank(vin) || timestamp == 0)
      throw new IllegalArgumentException();
    
    DynamoDbTable<EventItem> table = getEventTable();
    
    EventItem item = table.getItem(Key.builder().partitionValue(vin).sortValue(timestamp).build());
    
    if (item != null) {
      table.deleteItem(item);
    }
  }

  @Override
  public Event getEvent(String vin, long timestamp) {
    if (StringUtils.isBlank(vin) || timestamp == 0)
      throw new IllegalArgumentException();
    
    DynamoDbTable<EventItem> table = getEventTable();
    EventItem item = table.getItem(Key.builder().partitionValue(vin).sortValue(timestamp).build());

    return getTranslator().translate(item);
  }
  
  @Override
  public List<Event> getEvents(String vin) {
    if (StringUtils.isBlank(vin))
      throw new IllegalArgumentException();
    
    DynamoDbTable<EventItem> table = getEventTable();

    QueryConditional query = QueryConditional
        .keyEqualTo(Key.builder().partitionValue(vin)
        .build());

    PageIterable<EventItem> pages = table.query(query);
    List<EventItem> items = getItems(pages);

    return items
      .stream()
      .map(p -> getTranslator().translate(p))
      .collect(Collectors.toList());
  }
  
}
