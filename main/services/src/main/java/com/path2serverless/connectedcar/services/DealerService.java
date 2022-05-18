package com.path2serverless.connectedcar.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import com.google.inject.Inject;
import com.path2serverless.connectedcar.services.context.IServiceContext;
import com.path2serverless.connectedcar.services.data.items.DealerItem;
import com.path2serverless.connectedcar.services.translator.ITranslator;
import com.path2serverless.connectedcar.shared.data.entities.Dealer;
import com.path2serverless.connectedcar.shared.data.enums.StateCodeEnum;
import com.path2serverless.connectedcar.shared.services.IDealerService;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class DealerService extends BaseService implements IDealerService {

  @Inject
  public DealerService(IServiceContext serviceContext, ITranslator translatorService) {
    super(serviceContext, translatorService);
  }
  
  @Override
  public String createDealer(Dealer dealer) {
    if (dealer == null || !dealer.validate())
      throw new IllegalArgumentException();

    DealerItem item = getTranslator().translate(dealer);
    
    LocalDateTime now = LocalDateTime.now();

    DynamoDbTable<DealerItem> table = getDealerTable();

    item.setDealerId(UUID.randomUUID().toString());
    item.setCreateDateTime(now);
    item.setUpdateDateTime(now);
    
    table.putItem(item);

    GetItemEnhancedRequest request = GetItemEnhancedRequest.builder()
      .consistentRead(true)
      .key(Key.builder().partitionValue(item.getDealerId()).build())
      .build();

    table.getItem(request);

    return item.getDealerId();
  }
  
  @Override
  public void deleteDealer(String dealerId) {
    if (StringUtils.isBlank(dealerId))
      throw new IllegalArgumentException();
    
    DynamoDbTable<DealerItem> table = getDealerTable();
    
    DealerItem item = table.getItem(Key.builder().partitionValue(dealerId).build());
    
    if (item != null) {
      table.deleteItem(item);
    }
  }
  
  @Override
  public Dealer getDealer(String dealerId) {
    if (StringUtils.isBlank(dealerId))
      throw new IllegalArgumentException();
    
    DynamoDbTable<DealerItem> table = getDealerTable();
    DealerItem item = table.getItem(Key.builder().partitionValue(dealerId).build());

    return getTranslator().translate(item);
  }

  @Override
  public List<Dealer> getDealers(StateCodeEnum stateCode) {
    if (stateCode == null)
      throw new IllegalArgumentException();
    
    DynamoDbTable<DealerItem> table = getDealerTable();
    
    AttributeValue value = AttributeValue.builder()
      .s(stateCode.name())
      .build();

    Map<String, AttributeValue> expressionValues = new HashMap<>();
    expressionValues.put(":value", value);
    
    Expression expression = Expression.builder()
      .expression("stateCode = :value")
      .expressionValues(expressionValues)
      .build();

    ScanEnhancedRequest request = ScanEnhancedRequest.builder()
      .filterExpression(expression)
      .build();

    PageIterable<DealerItem> pages = table.scan(request);
    List<DealerItem> items = getItems(pages);

    return items
      .stream()
      .map(p -> getTranslator().translate(p))
      .collect(Collectors.toList());
  }
}
