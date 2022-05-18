package com.path2serverless.connectedcar.services.exceptions;

public class RequestValidationException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public RequestValidationException(String message) {
    super(message);
  }
}
