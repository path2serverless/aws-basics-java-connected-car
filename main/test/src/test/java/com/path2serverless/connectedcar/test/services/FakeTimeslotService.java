package com.path2serverless.connectedcar.test.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import com.path2serverless.connectedcar.shared.data.entities.Timeslot;
import com.path2serverless.connectedcar.shared.services.ITimeslotService;

public class FakeTimeslotService implements ITimeslotService {

  private Map<String,Timeslot> timeslots = new HashMap<String,Timeslot>();

  @Override
  public void createTimeslot(Timeslot timeslot) {
    if (timeslot == null || !timeslot.validate())
      throw new IllegalArgumentException();

    LocalDateTime now = LocalDateTime.now();

    timeslot.setCreateDateTime(now);
    timeslot.setUpdateDateTime(now);
    
    timeslots.put(getKey(timeslot.getDealerId(), timeslot.getServiceDateHour()), timeslot);
  }
  
  @Override
  public void deleteTimeslot(String dealerId, String serviceDateHour) {
    if (StringUtils.isBlank(dealerId) || StringUtils.isBlank(serviceDateHour))
      throw new IllegalArgumentException();

    String key = getKey(dealerId, serviceDateHour);
    
    if (timeslots.containsKey(key)) {
      timeslots.remove(key);
    }
  }
  
  @Override
  public Timeslot getTimeslot(String dealerId, String serviceDateHour) {
    if (StringUtils.isBlank(dealerId) || StringUtils.isBlank(serviceDateHour))
      throw new IllegalArgumentException();

    return timeslots.get(getKey(dealerId, serviceDateHour));
  }

  @Override
  public List<Timeslot> getTimeslots(String dealerId, String startDate, String endDate) {
    if (StringUtils.isBlank(dealerId) || StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate))
      throw new IllegalArgumentException();
    
    return timeslots.values()
      .stream()
      .filter(p -> p.getDealerId().equals(dealerId) && 
              p.getServiceDateHour().substring(0, 10).compareTo(startDate) >= 0 &&
              p.getServiceDateHour().substring(0, 10).compareTo(endDate) <= 0)
      .collect(Collectors.toList());

  }

  public void batchUpdate(List<Timeslot> timeslots) {
    if (timeslots == null)
      throw new IllegalArgumentException();

  }

  private static String getKey(String dealerId, String serviceDateHour) {
    return dealerId +"/" + serviceDateHour;
  }
}
