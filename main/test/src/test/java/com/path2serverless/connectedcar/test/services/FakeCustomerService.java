package com.path2serverless.connectedcar.test.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.path2serverless.connectedcar.shared.data.entities.Customer;
import com.path2serverless.connectedcar.shared.data.updates.CustomerPatch;
import com.path2serverless.connectedcar.shared.services.ICustomerService;

public class FakeCustomerService implements ICustomerService {

  private Map<String,Customer> customers = new HashMap<String,Customer>();

  @Override
  public void createCustomer(Customer customer) {
    if (customer == null || !customer.validate())
      throw new IllegalArgumentException();

    if (customers.containsKey(customer.getUsername()))
      throw new IllegalArgumentException();

    LocalDateTime now = LocalDateTime.now();

    customer.setCreateDateTime(now);
    customer.setUpdateDateTime(now);

    customers.put(customer.getUsername(), customer);
  }
  
  @Override
  public void updateCustomer(CustomerPatch patch) {
    if (patch == null || !patch.validate())
      throw new IllegalArgumentException();
    
    LocalDateTime now = LocalDateTime.now();

    if (customers.containsKey(patch.getUsername())) {
      Customer customer = customers.get(patch.getUsername());

      customer.setPhoneNumber(patch.getPhoneNumber());
      customer.setUpdateDateTime(now);
    }
  }
  
  @Override
  public void deleteCustomer(String username) {
    if (StringUtils.isBlank(username))
      throw new IllegalArgumentException();
    
    if (customers.containsKey(username)) {
      customers.remove(username);
    }
  }
  
  @Override
  public Customer getCustomer(String username) {
    if (StringUtils.isBlank(username))
      throw new IllegalArgumentException();
    
    return customers.get(username);
  }

  @Override
  public List<Customer> getCustomers(String lastname) {
    if (StringUtils.isBlank(lastname))
      throw new IllegalArgumentException();
    
    return customers.values()
      .stream()
      .filter(p -> p.getLastname().toLowerCase().startsWith(lastname.toLowerCase()))
      .collect(Collectors.toList());
  }

  public void batchUpdate(List<Customer> customers) {
    if (customers == null)
      throw new IllegalArgumentException();

  }
}
