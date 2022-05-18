package com.path2serverless.connectedcar.shared.services;

import java.util.List;

import com.path2serverless.connectedcar.shared.data.entities.Timeslot;

public interface ITimeslotService {
  
  public void createTimeslot(Timeslot timeslot);
  
  public void deleteTimeslot(String dealerId, String serviceDateHour);
  
  public Timeslot getTimeslot(String dealerId, String serviceDateHour);
  
  public List<Timeslot> getTimeslots(String dealerId, String startDate, String endDate);

  public void batchUpdate(List<Timeslot> timeslots);
  
}
