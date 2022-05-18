package com.path2serverless.connectedcar.functions;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.inject.Injector;
import com.path2serverless.connectedcar.shared.data.Validatable;
import com.path2serverless.connectedcar.shared.data.enums.StateCodeEnum;
import com.path2serverless.connectedcar.services.exceptions.ItemValidationException;
import com.path2serverless.connectedcar.services.exceptions.RequestValidationException;

public abstract class BaseRequestFunction extends BaseFunction {
	
  protected static final Map<String,String> RESPONSE_HEADER = Collections.singletonMap(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());

  private ObjectMapper mapper;

  protected BaseRequestFunction() {
    mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
  }

  protected BaseRequestFunction(Injector injector) {
    super(injector);

    mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
  }
  
	protected APIGatewayProxyResponseEvent process(ProcessFunc func, Context context) {
		try {
			return func.execute();
		}
		catch (Exception e) {
      StringWriter sw = new StringWriter();
      e.printStackTrace(new PrintWriter(sw));
      context.getLogger().log(sw.toString());

      return new APIGatewayProxyResponseEvent().withStatusCode(HttpStatus.SC_BAD_REQUEST);
		}
	}

  protected <T extends Validatable> T deserializeItem(APIGatewayProxyRequestEvent request, Class<T> classType) throws Exception {
    if (StringUtils.isBlank(request.getBody()))
      throw new ItemValidationException("Item is null");
    
    T item = mapper.readValue(request.getBody(), classType);
    
    if (!item.validate()) 
      throw new ItemValidationException("Item failed validation: " + item.toString());
    
    return item;
  }
  
  protected String serializeItem(Object obj) throws Exception {
    return mapper.writeValueAsString(obj);
  }
  
  protected String getHeaderValue(APIGatewayProxyRequestEvent request, String headerName) throws Exception {
    if (!request.getHeaders().containsKey(headerName)) {
      throw new RequestValidationException("Missing header value: " + headerName);
    }
    
    return request.getHeaders().get(headerName);
  }
  
  protected String getPathParameter(APIGatewayProxyRequestEvent request, String parameterName) throws Exception {
    if (!request.getPathParameters().containsKey(parameterName)) {
      throw new RequestValidationException("Missing path parameter: " + parameterName);
    }
    
    return request.getPathParameters().get(parameterName);
  }
  
  protected String getQueryStringParameter(APIGatewayProxyRequestEvent request, String parameterName) throws Exception {
    if (!request.getQueryStringParameters().containsKey(parameterName)) {
      throw new RequestValidationException("Missing query string parameter: " + parameterName);
    }
    
    return request.getQueryStringParameters().get(parameterName);
  }
  
  protected StateCodeEnum getStateCode(APIGatewayProxyRequestEvent request) throws Exception {
    String code = getQueryStringParameter(request, QUERY_STATE_CODE);
    return StateCodeEnum.valueOf(code);
  }
  
  protected String getCognitoUsername(APIGatewayProxyRequestEvent request) throws Exception {
    @SuppressWarnings("unchecked")
    Map<Object,Object> claims = (Map<Object,Object>)request.getRequestContext().getAuthorizer().get("claims");
    return claims.get("username").toString();
  }
}