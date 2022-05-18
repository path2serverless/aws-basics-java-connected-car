package com.path2serverless.connectedcar.functions;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.path2serverless.connectedcar.services.context.IServiceContext;
import com.path2serverless.connectedcar.services.modules.TracingModule;
import com.path2serverless.connectedcar.shared.orchestrators.IAdminOrchestrator;
import com.path2serverless.connectedcar.shared.orchestrators.ICustomerOrchestrator;
import com.path2serverless.connectedcar.shared.services.IAppointmentService;
import com.path2serverless.connectedcar.shared.services.ICustomerService;
import com.path2serverless.connectedcar.shared.services.IDealerService;
import com.path2serverless.connectedcar.shared.services.IEventService;
import com.path2serverless.connectedcar.shared.services.IRegistrationService;
import com.path2serverless.connectedcar.shared.services.ITimeslotService;
import com.path2serverless.connectedcar.shared.services.IUserService;
import com.path2serverless.connectedcar.shared.services.IVehicleService;

public abstract class BaseFunction {
	
  protected static final String HEADER_X_VIN           = "X-Vin";
  protected static final String HEADER_X_PIN           = "X-Pin";

  protected static final String PATH_DEALER_ID         = "dealerId";
  protected static final String PATH_USERNAME          = "username";
  protected static final String PATH_SERVICE_DATE_HOUR = "serviceDateHour";
  protected static final String PATH_APPOINTMENT_ID    = "appointmentId";
  protected static final String PATH_VIN               = "vin";
  protected static final String PATH_TIMESTAMP         = "timestamp";
  
  protected static final String QUERY_STATE_CODE       = "stateCode";
  protected static final String QUERY_START_DATE       = "startDate";
  protected static final String QUERY_END_DATE         = "endDate";
  protected static final String QUERY_LASTNAME         = "lastname";
  protected static final String QUERY_PARTIAL_VIN      = "partialVin";

  private Injector injector;
  
  protected BaseFunction() {
    injector = Guice.createInjector(new TracingModule());
  }

  protected BaseFunction(Injector injector) {
    if (injector == null)
      throw new IllegalArgumentException();

    this.injector = injector;
  }
  
  protected IServiceContext getServiceContext() {
    return injector.getInstance(IServiceContext.class);
  }

  protected IDealerService getDealerService() {
    return injector.getInstance(IDealerService.class);
  }

  protected ITimeslotService getTimeslotService() {
    return injector.getInstance(ITimeslotService.class);
  }

  protected IAppointmentService getAppointmentService() {
    return injector.getInstance(IAppointmentService.class);
  }

  protected ICustomerService getCustomerService() {
    return injector.getInstance(ICustomerService.class);
  }

  protected IRegistrationService getRegistrationService() {
    return injector.getInstance(IRegistrationService.class);
  }

  protected IVehicleService getVehicleService() {
    return injector.getInstance(IVehicleService.class);
  }
  
  protected IEventService getEventService() {
    return injector.getInstance(IEventService.class);
  }

  protected IUserService getUserService() {
    return injector.getInstance(IUserService.class);
  }

  protected IAdminOrchestrator getAdminOrchestrator() {
    return injector.getInstance(IAdminOrchestrator.class);
  }

  protected ICustomerOrchestrator getCustomerOrchestrator() {
    return injector.getInstance(ICustomerOrchestrator.class);
  }
}