package com.path2serverless.connectedcar.shared.config;

public class SQSConfig {

  private String userQueueUrn;

  public SQSConfig(String userQueueUrn) {
    this.userQueueUrn = userQueueUrn;
  }

  public String getUserQueueUrn() {
    return userQueueUrn;
  }
}
