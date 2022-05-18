package com.path2serverless.connectedcar.shared.services;

import com.path2serverless.connectedcar.shared.data.User;

public interface IMessageService {
  
  public void sendCreateUser(User user);
  
}
