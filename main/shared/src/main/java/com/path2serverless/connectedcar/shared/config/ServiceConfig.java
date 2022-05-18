package com.path2serverless.connectedcar.shared.config;

public class ServiceConfig {
  
  private String region;
  
  private AccessConfig accessConfig;
  private CognitoConfig cognitoConfig;
  private DynamoConfig dynamoConfig;
  private SQSConfig sqsConfig;
  
  public ServiceConfig(
      String region, 
      AccessConfig accessConfig, 
      CognitoConfig cognitoConfig, 
      DynamoConfig dynamoConfig,
      SQSConfig sqsConfig) {

    this.region = region;
    this.accessConfig = accessConfig;
    this.cognitoConfig = cognitoConfig;
    this.dynamoConfig = dynamoConfig;
    this.sqsConfig = sqsConfig;
  }

  public String getRegion() {
    return region;
  }

  public AccessConfig getAccessConfig() {
    return accessConfig;
  }

  public CognitoConfig getCognitoConfig() {
    return cognitoConfig;
  }

  public DynamoConfig getDynamoDbConfig() {
    return dynamoConfig;
  }

  public SQSConfig getSqsConfig() {
    return sqsConfig;
  }
}
