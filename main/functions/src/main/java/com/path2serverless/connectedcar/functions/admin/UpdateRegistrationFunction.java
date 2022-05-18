package com.path2serverless.connectedcar.functions.admin;

import org.apache.http.HttpStatus;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.path2serverless.connectedcar.functions.BaseRequestFunction;
import com.path2serverless.connectedcar.shared.data.updates.RegistrationPatch;

public class UpdateRegistrationFunction extends BaseRequestFunction implements RequestHandler<APIGatewayProxyRequestEvent,APIGatewayProxyResponseEvent> {

  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
		return process(() -> {
      RegistrationPatch patch = deserializeItem(request, RegistrationPatch.class);
      getRegistrationService().updateRegistration(patch);
      
      return new APIGatewayProxyResponseEvent().withStatusCode(HttpStatus.SC_OK);
    }, context);
  }
}