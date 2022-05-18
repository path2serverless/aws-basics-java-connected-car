package com.path2serverless.connectedcar.test.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import com.path2serverless.connectedcar.shared.data.entities.Dealer;
import com.path2serverless.connectedcar.shared.data.enums.StateCodeEnum;
import com.path2serverless.connectedcar.shared.services.IDealerService;

public class FakeDealerService implements IDealerService {

  private Map<String,Dealer> dealers = new HashMap<String,Dealer>();

  @Override
  public String createDealer(Dealer dealer) {
    if (dealer == null || !dealer.validate())
      throw new IllegalArgumentException();

    LocalDateTime now = LocalDateTime.now();

    dealer.setDealerId(UUID.randomUUID().toString());
    dealer.setCreateDateTime(now);
    dealer.setUpdateDateTime(now);

    dealers.put(dealer.getDealerId(), dealer);

    return dealer.getDealerId();
  }
  
  @Override
  public void deleteDealer(String dealerId) {
    if (StringUtils.isBlank(dealerId))
      throw new IllegalArgumentException();
    
    if (dealers.containsKey(dealerId)) {
      dealers.remove(dealerId);
    }
  }
  
  @Override
  public Dealer getDealer(String dealerId) {
    if (StringUtils.isBlank(dealerId))
      throw new IllegalArgumentException();

    return dealers.get(dealerId);
  }

  @Override
  public List<Dealer> getDealers(StateCodeEnum stateCode) {
    if (stateCode == null)
      throw new IllegalArgumentException();
    
    return dealers.values()
      .stream()
      .filter(p -> p.getStateCode() == stateCode)
      .collect(Collectors.toList());
  }
}
