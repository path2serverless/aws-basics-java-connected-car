package com.path2serverless.connectedcar.services;

import com.google.inject.Inject;
import com.path2serverless.connectedcar.services.context.IServiceContext;
import com.path2serverless.connectedcar.services.translator.ITranslator;
import com.path2serverless.connectedcar.shared.data.User;
import com.path2serverless.connectedcar.shared.services.IUserService;

import com.amazonaws.services.cognitoidp.model.AdminCreateUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminSetUserPasswordRequest;
import com.amazonaws.services.cognitoidp.model.DeliveryMediumType;

public class UserService extends BaseService implements IUserService {

  @Inject
  public UserService(IServiceContext serviceContext, ITranslator translatorService) {
    super(serviceContext, translatorService);
  }
  
  @Override
  public void createUser(User user) {
    if (user == null || !user.validate())
      throw new IllegalArgumentException();

		AdminCreateUserRequest request = new AdminCreateUserRequest()
      .withUserPoolId(getServiceContext().getConfig().getCognitoConfig().getUserPoolId())
      .withUsername(user.getUsername())
      .withTemporaryPassword(user.getPassword())
      .withMessageAction("SUPPRESS")
      .withDesiredDeliveryMediums(DeliveryMediumType.EMAIL)
      .withForceAliasCreation(Boolean.FALSE);
		
		getServiceContext().getCognitoProvider().adminCreateUser(request);
  }

  public void setPermanentPassword(User user) {
    if (user == null || !user.validate())
      throw new IllegalArgumentException();

    AdminSetUserPasswordRequest request = new AdminSetUserPasswordRequest()
      .withUserPoolId(getServiceContext().getConfig().getCognitoConfig().getUserPoolId())
      .withUsername(user.getUsername())
      .withPassword(user.getPassword())
      .withPermanent(true);

		getServiceContext().getCognitoProvider().adminSetUserPassword(request);
  }
}
