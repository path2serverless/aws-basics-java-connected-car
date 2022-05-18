package com.path2serverless.connectedcar.shared.services;

import java.util.List;

import com.path2serverless.connectedcar.shared.data.entities.Customer;
import com.path2serverless.connectedcar.shared.data.updates.CustomerPatch;

public interface ICustomerService {
  
  public void createCustomer(Customer customer);
  
  public void updateCustomer(CustomerPatch patch);
  
  public void deleteCustomer(String username);
  
  public Customer getCustomer(String username);
  
  public List<Customer> getCustomers(String lastname);

  public void batchUpdate(List<Customer> customers);
  
}
