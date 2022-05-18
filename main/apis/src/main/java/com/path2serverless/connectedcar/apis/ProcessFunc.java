package com.path2serverless.connectedcar.apis;

import javax.ws.rs.core.Response;

@FunctionalInterface
public interface ProcessFunc {
  public Response execute() throws Exception;
}
