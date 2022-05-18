package com.path2serverless.connectedcar.services.context;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

import com.path2serverless.connectedcar.shared.config.CognitoConfig;
import com.path2serverless.connectedcar.shared.config.DynamoConfig;
import com.path2serverless.connectedcar.shared.config.SQSConfig;
import com.path2serverless.connectedcar.shared.config.ServiceConfig;

public abstract class CloudContext extends BaseContext {

  private ServiceConfig serviceConfig = null;
  private AWSCognitoIdentityProvider cognitoProvider = null;
  private AmazonSQS sqsClient = null;

  public ServiceConfig getConfig() {
    if (serviceConfig == null) {
      ServiceConfig config = new ServiceConfig(
          System.getenv(REGION),
          null,
          new CognitoConfig(
              System.getenv(USER_POOL_ID),
              System.getenv(CLIENT_ID),
              System.getenv(CLIENT_SECRET)),
          new DynamoConfig(
              System.getenv(DEALER_TABLE_NAME),
              System.getenv(TIMESLOT_TABLE_NAME),
              System.getenv(APPOINTMENT_TABLE_NAME),
              System.getenv(CUSTOMER_TABLE_NAME),
              System.getenv(REGISTRATION_TABLE_NAME),
              System.getenv(VEHICLE_TABLE_NAME),
              System.getenv(EVENT_TABLE_NAME)),
            new SQSConfig(
              System.getenv(USER_QUEUE_URN)));

      serviceConfig = config;
    }

    return serviceConfig;
  }

  public synchronized AWSCognitoIdentityProvider getCognitoProvider() {
    if (cognitoProvider == null) {
      AWSCognitoIdentityProvider provider = AWSCognitoIdentityProviderClientBuilder.standard().build();
      cognitoProvider = provider;
    }

    return cognitoProvider;
  }

  public synchronized AmazonSQS getSQSClient() {
    if (sqsClient == null)
    {
      AmazonSQS client = AmazonSQSClientBuilder.standard().build(); 
      sqsClient = client;
    }

    return sqsClient;
  }
}
