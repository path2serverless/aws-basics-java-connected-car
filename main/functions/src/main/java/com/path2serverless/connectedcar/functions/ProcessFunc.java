package com.path2serverless.connectedcar.functions;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

@FunctionalInterface
public interface ProcessFunc {
  public APIGatewayProxyResponseEvent execute() throws Exception;
}
