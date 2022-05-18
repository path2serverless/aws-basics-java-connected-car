package com.path2serverless.connectedcar.shared.config;

public class CognitoConfig {

  private String userPoolId;
  private String clientId;
  private String clientSecret;

  public CognitoConfig(String userPoolId, String clientId, String clientSecret) {
    this.userPoolId = userPoolId;
    this.clientId = clientId;
    this.clientSecret = clientSecret;
  }

  public String getUserPoolId() {
    return userPoolId;
  }

  public String getClientId() {
    return clientId;
  }

  public String getClientSecret() {
    return clientSecret;
  }
}
