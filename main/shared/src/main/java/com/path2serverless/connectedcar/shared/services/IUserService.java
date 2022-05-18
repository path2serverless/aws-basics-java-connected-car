package com.path2serverless.connectedcar.shared.services;

import com.path2serverless.connectedcar.shared.data.User;

public interface IUserService {
  
  public void createUser(User user);

  public void setPermanentPassword(User user);

}
