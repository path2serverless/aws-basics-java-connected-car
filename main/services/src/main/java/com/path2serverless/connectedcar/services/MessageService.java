package com.path2serverless.connectedcar.services;

import com.google.inject.Inject;
import com.path2serverless.connectedcar.services.context.IServiceContext;
import com.path2serverless.connectedcar.services.translator.ITranslator;
import com.path2serverless.connectedcar.shared.data.User;
import com.path2serverless.connectedcar.shared.services.IMessageService;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageService extends BaseService implements IMessageService {

  @Inject
  public MessageService(IServiceContext serviceContext, ITranslator translatorService) {
    super(serviceContext, translatorService);
  }
  
  @Override
  public void sendCreateUser(User user) {
    if (user == null || !user.validate())
      throw new IllegalArgumentException();

    try {
      String urn = getServiceContext().getConfig().getSqsConfig().getUserQueueUrn();

      String message = new ObjectMapper().writeValueAsString(user);

      SendMessageRequest request = new SendMessageRequest()
        .withQueueUrl(urn)
        .withMessageBody(message);

      getServiceContext().getSQSClient().sendMessage(request);
    }
    catch (Exception e) {
      throw new IllegalArgumentException(e);
    }
  }
}
