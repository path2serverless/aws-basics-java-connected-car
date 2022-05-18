package com.path2serverless.connectedcar.shared.orchestrators;

import com.path2serverless.connectedcar.shared.data.updates.CustomerProvision;

public interface IAdminOrchestrator {

  public void createCustomer(CustomerProvision provision);

  public void createCustomerUsingMessage(CustomerProvision provision);

}