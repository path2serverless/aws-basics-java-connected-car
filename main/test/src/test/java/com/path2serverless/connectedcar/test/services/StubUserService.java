package com.path2serverless.connectedcar.test.services;

import com.path2serverless.connectedcar.shared.data.User;
import com.path2serverless.connectedcar.shared.services.IUserService;

public class StubUserService implements IUserService {

  @Override
  public void createUser(User user) {
    if (user == null || !user.validate())
      throw new IllegalArgumentException();

  }

  public void setPermanentPassword(User user) {
    if (user == null || !user.validate())
      throw new IllegalArgumentException();

  }

  public String authenticate(User user) {
    if (user == null || !user.validate())
      throw new IllegalArgumentException();

    return null;
  }
}
