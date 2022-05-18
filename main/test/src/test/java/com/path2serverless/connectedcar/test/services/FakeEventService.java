package com.path2serverless.connectedcar.test.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import com.path2serverless.connectedcar.shared.data.entities.Event;
import com.path2serverless.connectedcar.shared.services.IEventService;

public class FakeEventService implements IEventService {

  private Map<String,Event> events = new HashMap<String,Event>();

  @Override
  public void createEvent(Event event) {
    if (event == null || !event.validate())
      throw new IllegalArgumentException();

    LocalDateTime now = LocalDateTime.now();

    event.setCreateDateTime(now);
    event.setUpdateDateTime(now);

    events.put(getKey(event.getVin(), event.getTimestamp()), event);
  }

  @Override
  public void deleteEvent(String vin, long timestamp) {
    if (StringUtils.isBlank(vin) || timestamp == 0)
      throw new IllegalArgumentException();

    String key = getKey(vin, timestamp);
    
    if (events.containsKey(key)) {
      events.remove(key);
    }
  }

  @Override
  public Event getEvent(String vin, long timestamp) {
    if (StringUtils.isBlank(vin) || timestamp == 0)
      throw new IllegalArgumentException();

    return events.get(getKey(vin, timestamp));    
  }
  
  @Override
  public List<Event> getEvents(String vin) {
    if (StringUtils.isBlank(vin))
      throw new IllegalArgumentException();
    
    return events.values()
      .stream()
      .filter(p -> p.getVin().equals(vin))
      .collect(Collectors.toList());
  }

  private static String getKey(String vin, long timestamp) {
    return vin + "/" + timestamp; 
  }
}
