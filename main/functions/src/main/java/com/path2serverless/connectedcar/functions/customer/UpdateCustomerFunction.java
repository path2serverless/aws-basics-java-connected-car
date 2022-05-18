package com.path2serverless.connectedcar.functions.customer;

import org.apache.http.HttpStatus;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.path2serverless.connectedcar.functions.BaseRequestFunction;
import com.path2serverless.connectedcar.shared.data.updates.CustomerPatch;

public class UpdateCustomerFunction extends BaseRequestFunction implements RequestHandler<APIGatewayProxyRequestEvent,APIGatewayProxyResponseEvent> {

  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
		return process(() -> {
      String username = getCognitoUsername(request);
      CustomerPatch patch = deserializeItem(request, CustomerPatch.class);
      patch.setUsername(username);
      getCustomerService().updateCustomer(patch);
      
      return new APIGatewayProxyResponseEvent().withStatusCode(HttpStatus.SC_OK);
    }, context);
  }
}