package com.path2serverless.connectedcar.test.fixtures;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.path2serverless.connectedcar.test.modules.TestModule;

public abstract class BaseFixture {
  
  private Injector injector;

  public BaseFixture() {
	  injector = Guice.createInjector(new TestModule());
  }

  public Injector getInjector() {
    return injector;
  }
}
