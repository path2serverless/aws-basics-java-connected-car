package com.path2serverless.connectedcar.apis.components;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.path2serverless.connectedcar.apis.ProcessFunc;
import com.path2serverless.connectedcar.shared.data.Validatable;
import com.path2serverless.connectedcar.shared.orchestrators.IAdminOrchestrator;
import com.path2serverless.connectedcar.shared.orchestrators.ICustomerOrchestrator;
import com.path2serverless.connectedcar.shared.services.IAppointmentService;
import com.path2serverless.connectedcar.shared.services.ICustomerService;
import com.path2serverless.connectedcar.shared.services.IDealerService;
import com.path2serverless.connectedcar.shared.services.IEventService;
import com.path2serverless.connectedcar.shared.services.IMessageService;
import com.path2serverless.connectedcar.shared.services.IRegistrationService;
import com.path2serverless.connectedcar.shared.services.ITimeslotService;
import com.path2serverless.connectedcar.shared.services.IVehicleService;
import com.path2serverless.connectedcar.services.exceptions.ItemValidationException;
import com.path2serverless.connectedcar.services.exceptions.RequestValidationException;
import com.path2serverless.connectedcar.services.modules.NonTracingModule;

public abstract class BaseResources extends ResourceConfig {

  private static final Logger logger = LogManager.getLogger(BaseResources.class);

  private Injector injector;

	protected BaseResources() {
	  injector = Guice.createInjector(new NonTracingModule());
  }

  protected BaseResources(Injector injector) {
    if (injector == null)
      throw new IllegalArgumentException();

    this.injector = injector;
  }

	protected Response process(ProcessFunc func) {
		try {
			return func.execute();
		}
		catch (Exception e) {
      e.printStackTrace();
			logger.error(e);
			return Response.status(500).build();
		}
	}

  protected void validate(Validatable item) throws Exception {
    if (item == null)
      throw new ItemValidationException("Item is null");

    if (!item.validate())
      throw new ItemValidationException("Item failed validation: " + item.toString());
  }

  protected void validate(String parameterName, String parameterValue) {
    if (parameterValue == null)
      throw new RequestValidationException("Missing parameter: " + parameterName);
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

  protected IMessageService getMessageService() {
    return injector.getInstance(IMessageService.class);
  }

  protected IAdminOrchestrator getAdminOrchestrator() {
    return injector.getInstance(IAdminOrchestrator.class);
  }

  protected ICustomerOrchestrator getCustomerOrchestrator() {
    return injector.getInstance(ICustomerOrchestrator.class);
  }
}
