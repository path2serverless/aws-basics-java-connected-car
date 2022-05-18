package com.path2serverless.connectedcar.functions.admin;

import org.apache.http.HttpStatus;

import java.util.HashMap;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.inject.Injector;
import com.path2serverless.connectedcar.functions.BaseRequestFunction;
import com.path2serverless.connectedcar.shared.data.entities.Dealer;

public class CreateDealerFunction extends BaseRequestFunction implements RequestHandler<APIGatewayProxyRequestEvent,APIGatewayProxyResponseEvent> {
	
  public CreateDealerFunction() {}

  public CreateDealerFunction(Injector injector) {
    super(injector);
  }

  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
		return process(() -> {
      Dealer dealer = deserializeItem(request, Dealer.class);
      String dealerId = getDealerService().createDealer(dealer);

      return new APIGatewayProxyResponseEvent()
        .withHeaders(new HashMap<String, String>() {{put("Location", "/admin/dealers/" + dealerId);}})
        .withStatusCode(HttpStatus.SC_CREATED);
    }, context);
  }
}