package com.path2serverless.connectedcar.shared.config;

public class AccessConfig {

  private String accessKey;
  private String secretKey;
  
  public AccessConfig(String accessKey, String secretKey) {
    this.accessKey = accessKey;
    this.secretKey = secretKey;
  }

  public String getAccessKey() {
    return accessKey;
  }

  public String getSecretKey() {
    return secretKey;
  }
}
