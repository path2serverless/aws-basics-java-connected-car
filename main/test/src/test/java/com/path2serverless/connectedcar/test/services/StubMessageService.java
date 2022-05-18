package com.path2serverless.connectedcar.test.services;

import com.path2serverless.connectedcar.shared.data.User;
import com.path2serverless.connectedcar.shared.services.IMessageService;

public class StubMessageService implements IMessageService {

  @Override
  public void sendCreateUser(User user) {
    if (user == null || !user.validate())
      throw new IllegalArgumentException();

  }
}
