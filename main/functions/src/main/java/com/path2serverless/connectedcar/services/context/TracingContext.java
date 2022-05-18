package com.path2serverless.connectedcar.services.context;

import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import com.amazonaws.xray.interceptors.TracingInterceptor;

public class TracingContext extends CloudContext implements IServiceContext {

  private DynamoDbEnhancedClient dynamoDbClient = null;
  
  public synchronized DynamoDbEnhancedClient getDynamoDbClient() {
    if (dynamoDbClient == null) {
      DynamoDbClient ddb = DynamoDbClient.builder()
        .httpClient(UrlConnectionHttpClient.builder().build())
        .overrideConfiguration(
          ClientOverrideConfiguration.builder()
            .addExecutionInterceptor(new TracingInterceptor()).build())
        .build();        
      
      DynamoDbEnhancedClient client = DynamoDbEnhancedClient
        .builder()
        .dynamoDbClient(ddb)
        .build();
      
      dynamoDbClient = client;
    }

    return dynamoDbClient;
  }
}
