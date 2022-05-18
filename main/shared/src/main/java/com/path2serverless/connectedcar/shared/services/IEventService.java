package com.path2serverless.connectedcar.shared.services;

import java.util.List;

import com.path2serverless.connectedcar.shared.data.entities.Event;

public interface IEventService {
  
  public void createEvent(Event event);
  
  public void deleteEvent(String vin, long timestamp);

  public Event getEvent(String vin, long timestamp);

  public List<Event> getEvents(String vin);

}
