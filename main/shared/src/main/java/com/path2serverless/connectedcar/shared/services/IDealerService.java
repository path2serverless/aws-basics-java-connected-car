package com.path2serverless.connectedcar.shared.services;

import java.util.List;

import com.path2serverless.connectedcar.shared.data.entities.Dealer;
import com.path2serverless.connectedcar.shared.data.enums.StateCodeEnum;

public interface IDealerService {
  
  public String createDealer(Dealer dealer);
  
  public void deleteDealer(String dealerId);
  
  public Dealer getDealer(String dealerId);
  
  public List<Dealer> getDealers(StateCodeEnum stateCode);
  
}
