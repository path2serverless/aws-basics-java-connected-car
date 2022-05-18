package com.path2serverless.connectedcar.services.context;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.path2serverless.connectedcar.shared.config.AccessConfig;
import com.path2serverless.connectedcar.shared.config.CognitoConfig;
import com.path2serverless.connectedcar.shared.config.DynamoConfig;
import com.path2serverless.connectedcar.shared.config.SQSConfig;
import com.path2serverless.connectedcar.shared.config.ServiceConfig;

public class LocalContext extends BaseContext implements IServiceContext {

  private ServiceConfig serviceConfig = null;
  private AWSCognitoIdentityProvider cognitoProvider = null;
  private DynamoDbEnhancedClient dynamoDbClient = null;
  private AmazonSQS sqsClient = null;

  public ServiceConfig getConfig() {
    if (serviceConfig == null) {
      Properties properties = getProperties();
      
      ServiceConfig config = new ServiceConfig(
          properties.getProperty(REGION),
          new AccessConfig(
              properties.getProperty(ACCESS_KEY),
              properties.getProperty(SECRET_KEY)),
          new CognitoConfig(
              properties.getProperty(USER_POOL_ID),
              properties.getProperty(CLIENT_ID),
              properties.getProperty(CLIENT_SECRET)),
          new DynamoConfig(
              properties.getProperty(DEALER_TABLE_NAME),
              properties.getProperty(TIMESLOT_TABLE_NAME),
              properties.getProperty(APPOINTMENT_TABLE_NAME),
              properties.getProperty(CUSTOMER_TABLE_NAME),
              properties.getProperty(REGISTRATION_TABLE_NAME),
              properties.getProperty(VEHICLE_TABLE_NAME),
              properties.getProperty(EVENT_TABLE_NAME)),
            new SQSConfig(
              properties.getProperty(USER_QUEUE_URN)));

      serviceConfig = config;
    }

    return serviceConfig;
  }

  public synchronized AWSCognitoIdentityProvider getCognitoProvider() {
    if (cognitoProvider == null) {
	    BasicAWSCredentials credentials = new BasicAWSCredentials(
	        getConfig().getAccessConfig().getAccessKey(), 
	        getConfig().getAccessConfig().getSecretKey());
	    
	    AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
	    
	    AWSCognitoIdentityProvider provider = AWSCognitoIdentityProviderClientBuilder.standard()
	        .withCredentials(credentialsProvider)
	        .withRegion(getConfig().getRegion())
	        .build();

      cognitoProvider = provider;
    }

    return cognitoProvider;
  }

  public synchronized DynamoDbEnhancedClient getDynamoDbClient() {
    if (dynamoDbClient == null) {
      StaticCredentialsProvider provider = StaticCredentialsProvider.create(
        AwsBasicCredentials.create(
          getConfig().getAccessConfig().getAccessKey(),
          getConfig().getAccessConfig().getSecretKey()));

      DynamoDbClient ddb = DynamoDbClient.builder()
        .credentialsProvider(provider)
        .region(Region.of(getConfig().getRegion()))
        .httpClient(UrlConnectionHttpClient.builder().build())
        .build();        
      
      DynamoDbEnhancedClient client = DynamoDbEnhancedClient
        .builder()
        .dynamoDbClient(ddb)
        .build();
        
      dynamoDbClient = client;
    }

    return dynamoDbClient;
  }

  public synchronized AmazonSQS getSQSClient() {
    if (sqsClient == null)
    {
	    BasicAWSCredentials credentials = new BasicAWSCredentials(
	        getConfig().getAccessConfig().getAccessKey(), 
	        getConfig().getAccessConfig().getSecretKey());
	    
	    AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);

      AmazonSQS client = AmazonSQSClientBuilder.standard()
        .withCredentials(credentialsProvider)
        .withRegion(getConfig().getRegion())
        .build(); 
        
      sqsClient = client;
    }

    return sqsClient;
  }
  
  private Properties getProperties() {
    final String FILE = "config.properties";
    
    Properties props = new Properties();
    InputStream stream = null;
    
    try {
      stream = getClass().getClassLoader().getResourceAsStream(FILE);
      
      if (stream != null) {
        props.load(stream);
      }
      else {
        throw new FileNotFoundException(FILE);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      if (stream != null) {
        try {
          stream.close();
        }
        catch (Exception ignore) {}
      }
    }

    return props;
  }
}
