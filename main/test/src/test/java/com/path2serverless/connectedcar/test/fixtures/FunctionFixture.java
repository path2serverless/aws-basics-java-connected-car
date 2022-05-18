package com.path2serverless.connectedcar.test.fixtures;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import org.mockito.Mock;
import org.mockito.Mockito;

public class FunctionFixture extends BaseFixture {

  @Mock
  private Context dummyLambdaContext = null;

  public Context getDummyLambdaContext() {
    if (dummyLambdaContext == null) {
      Context context = Mockito.mock(Context.class);
      LambdaLogger logger = Mockito.mock(LambdaLogger.class);
      Mockito.when(context.getLogger()).thenReturn(logger);
      
      dummyLambdaContext = context;
    }

    return dummyLambdaContext;
  }
}
