package com.path2serverless.connectedcar.services.context;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.sqs.AmazonSQS;
import com.path2serverless.connectedcar.shared.config.ServiceConfig;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;

public interface IServiceContext {
  
  public ServiceConfig getConfig();
  
  public AWSCognitoIdentityProvider getCognitoProvider();
  
  public DynamoDbEnhancedClient getDynamoDbClient();

  public AmazonSQS getSQSClient();

}
