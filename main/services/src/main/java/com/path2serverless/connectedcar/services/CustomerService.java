package com.path2serverless.connectedcar.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.google.inject.Inject;
import com.path2serverless.connectedcar.services.context.IServiceContext;
import com.path2serverless.connectedcar.services.data.items.CustomerItem;
import com.path2serverless.connectedcar.services.translator.ITranslator;
import com.path2serverless.connectedcar.shared.data.entities.Customer;
import com.path2serverless.connectedcar.shared.data.updates.CustomerPatch;
import com.path2serverless.connectedcar.shared.services.ICustomerService;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchWriteItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.WriteBatch;
import software.amazon.awssdk.enhanced.dynamodb.model.WriteBatch.Builder;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class CustomerService extends BaseService implements ICustomerService {

  @Inject
  public CustomerService(IServiceContext serviceContext, ITranslator translatorService) {
    super(serviceContext, translatorService);
  }
  
  @Override
  public void createCustomer(Customer customer) {
    if (customer == null || !customer.validate())
      throw new IllegalArgumentException();

    CustomerItem item = getTranslator().translate(customer);

    DynamoDbTable<CustomerItem> table = getCustomerTable();

    GetItemEnhancedRequest request = GetItemEnhancedRequest.builder()
      .consistentRead(true)
      .key(Key.builder().partitionValue(item.getUsername()).build())
      .build();

    CustomerItem existing = table.getItem(request);

    if (existing != null)
      throw new IllegalArgumentException();

    LocalDateTime now = LocalDateTime.now();

    item.setCreateDateTime(now);
    item.setUpdateDateTime(now);
    
    table.putItem(item);
    table.getItem(request);
  }
  
  @Override
  public void updateCustomer(CustomerPatch patch) {
    if (patch == null || !patch.validate())
      throw new IllegalArgumentException();
    
    LocalDateTime now = LocalDateTime.now();

    DynamoDbTable<CustomerItem> table = getCustomerTable();
    
    CustomerItem currentItem = table.getItem(Key.builder().partitionValue(patch.getUsername()).build());

    if (currentItem != null) {
      currentItem.setPhoneNumber(patch.getPhoneNumber());
      currentItem.setUpdateDateTime(now);
      
      table.putItem(currentItem);
    }
  }
  
  @Override
  public void deleteCustomer(String username) {
    if (StringUtils.isBlank(username))
      throw new IllegalArgumentException();
    
    DynamoDbTable<CustomerItem> table = getCustomerTable();
    
    CustomerItem item = table.getItem(Key.builder().partitionValue(username).build());
    
    if (item != null) {
      table.deleteItem(item);
    }
  }
  
  @Override
  public Customer getCustomer(String username) {
    if (StringUtils.isBlank(username))
      throw new IllegalArgumentException();
    
    DynamoDbTable<CustomerItem> table = getCustomerTable();
    CustomerItem item = table.getItem(Key.builder().partitionValue(username).build());

    return getTranslator().translate(item);
  }

  @Override
  public List<Customer> getCustomers(String lastname) {
    if (StringUtils.isBlank(lastname))
      throw new IllegalArgumentException();
    
    DynamoDbTable<CustomerItem> table = getCustomerTable();
    
    AttributeValue value = AttributeValue.builder()
      .s(lastname.toLowerCase())
      .build();

    Map<String, AttributeValue> expressionValues = new HashMap<>();
    expressionValues.put(":value", value);
    
    Expression expression = Expression.builder()
      .expression("begins_with(lastnameLower, :value)")
      .expressionValues(expressionValues)
      .build();

    ScanEnhancedRequest request = ScanEnhancedRequest.builder()
      .filterExpression(expression)
      .build();
    
    PageIterable<CustomerItem> pages = table.scan(request);
    List<CustomerItem> items = getItems(pages);

    return items
      .stream()
      .map(p -> getTranslator().translate(p))
      .collect(Collectors.toList());
  }

  public void batchUpdate(List<Customer> customers) {
    if (customers == null)
      throw new IllegalArgumentException();

    DynamoDbTable<CustomerItem> table = getCustomerTable();

    Builder<CustomerItem> batchBuilder = WriteBatch.builder(CustomerItem.class).mappedTableResource(table);

    for (Customer customer : customers) {
      batchBuilder.addPutItem(p -> p.item(getTranslator().translate(customer)));
    }

    BatchWriteItemEnhancedRequest request = 
        BatchWriteItemEnhancedRequest
            .builder()
            .writeBatches(batchBuilder.build())
            .build();

    getServiceContext().getDynamoDbClient().batchWriteItem(request);
  }
}
