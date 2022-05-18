package com.path2serverless.connectedcar.shared.orchestrators;

import com.google.inject.Inject;
import com.path2serverless.connectedcar.shared.data.User;
import com.path2serverless.connectedcar.shared.data.entities.Customer;
import com.path2serverless.connectedcar.shared.data.updates.CustomerProvision;
import com.path2serverless.connectedcar.shared.services.ICustomerService;
import com.path2serverless.connectedcar.shared.services.IMessageService;
import com.path2serverless.connectedcar.shared.services.IUserService;

public class AdminOrchestrator implements IAdminOrchestrator {

  private ICustomerService customerService;
  private IMessageService messageService;
  private IUserService userService;

  @Inject
  public AdminOrchestrator(
      ICustomerService customerService,
      IMessageService messageService,
      IUserService userService) {

    this.customerService = customerService;
    this.userService = userService;
    this.messageService = messageService;
  }

  public void createCustomer(CustomerProvision provision) {   
      Customer customer = new Customer();

      customer.setUsername(provision.getUsername());
      customer.setFirstname(provision.getFirstname());
      customer.setLastname(provision.getLastname());
      customer.setPhoneNumber(provision.getPhoneNumber());

      customerService.createCustomer(customer);

      User user = new User();

      user.setUsername(provision.getUsername());
      user.setPassword(provision.getPassword());
      
      userService.createUser(user);
  }

  public void createCustomerUsingMessage(CustomerProvision provision) {
      Customer customer = new Customer();

      customer.setUsername(provision.getUsername());
      customer.setFirstname(provision.getFirstname());
      customer.setLastname(provision.getLastname());
      customer.setPhoneNumber(provision.getPhoneNumber());

      customerService.createCustomer(customer);

      User user = new User();

      user.setUsername(provision.getUsername());
      user.setPassword(provision.getPassword());

      messageService.sendCreateUser(user);
  }
  
}
