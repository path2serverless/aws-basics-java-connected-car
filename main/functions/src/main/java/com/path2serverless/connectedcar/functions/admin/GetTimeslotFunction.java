package com.path2serverless.connectedcar.functions.admin;

import org.apache.http.HttpStatus;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.path2serverless.connectedcar.functions.BaseRequestFunction;
import com.path2serverless.connectedcar.shared.data.entities.Timeslot;

public class GetTimeslotFunction extends BaseRequestFunction implements RequestHandler<APIGatewayProxyRequestEvent,APIGatewayProxyResponseEvent> {

  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
		return process(() -> {
      String dealerId = getPathParameter(request, PATH_DEALER_ID);
      String serviceDateHour = getPathParameter(request, PATH_SERVICE_DATE_HOUR);

      Timeslot timeslot = getTimeslotService().getTimeslot(dealerId, serviceDateHour);

      if (timeslot != null) {
        return new APIGatewayProxyResponseEvent()
          .withBody(serializeItem(timeslot))
          .withHeaders(RESPONSE_HEADER)
          .withStatusCode(HttpStatus.SC_OK);
      }   
      else {
        return new APIGatewayProxyResponseEvent().withStatusCode(HttpStatus.SC_NOT_FOUND);
      }   
    }, context);
  }
}