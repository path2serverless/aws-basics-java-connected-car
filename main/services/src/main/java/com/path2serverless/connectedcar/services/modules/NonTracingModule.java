package com.path2serverless.connectedcar.services.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.path2serverless.connectedcar.services.context.NonTracingContext;
import com.path2serverless.connectedcar.services.translator.ITranslator;
import com.path2serverless.connectedcar.services.translator.Translator;
import com.path2serverless.connectedcar.services.context.IServiceContext;
import com.path2serverless.connectedcar.services.AppointmentService;
import com.path2serverless.connectedcar.services.CustomerService;
import com.path2serverless.connectedcar.services.DealerService;
import com.path2serverless.connectedcar.services.EventService;
import com.path2serverless.connectedcar.services.RegistrationService;
import com.path2serverless.connectedcar.services.TimeslotService;
import com.path2serverless.connectedcar.services.UserService;
import com.path2serverless.connectedcar.services.VehicleService;
import com.path2serverless.connectedcar.services.MessageService;
import com.path2serverless.connectedcar.shared.orchestrators.AdminOrchestrator;
import com.path2serverless.connectedcar.shared.orchestrators.IAdminOrchestrator;
import com.path2serverless.connectedcar.shared.orchestrators.ICustomerOrchestrator;
import com.path2serverless.connectedcar.shared.orchestrators.CustomerOrchestrator;
import com.path2serverless.connectedcar.shared.services.IAppointmentService;
import com.path2serverless.connectedcar.shared.services.ICustomerService;
import com.path2serverless.connectedcar.shared.services.IDealerService;
import com.path2serverless.connectedcar.shared.services.IEventService;
import com.path2serverless.connectedcar.shared.services.IMessageService;
import com.path2serverless.connectedcar.shared.services.IRegistrationService;
import com.path2serverless.connectedcar.shared.services.ITimeslotService;
import com.path2serverless.connectedcar.shared.services.IUserService;
import com.path2serverless.connectedcar.shared.services.IVehicleService;

public class NonTracingModule extends AbstractModule {

  @Override 
  protected void configure() {
    bind(IServiceContext.class).to(NonTracingContext.class).in(Singleton.class);
    bind(ITranslator.class).to(Translator.class).in(Singleton.class);
    
    bind(IDealerService.class).to(DealerService.class).in(Singleton.class);
    bind(ITimeslotService.class).to(TimeslotService.class).in(Singleton.class);
    bind(IAppointmentService.class).to(AppointmentService.class).in(Singleton.class);
    bind(ICustomerService.class).to(CustomerService.class).in(Singleton.class);
    bind(IRegistrationService.class).to(RegistrationService.class).in(Singleton.class);
    bind(IVehicleService.class).to(VehicleService.class).in(Singleton.class);
    bind(IEventService.class).to(EventService.class).in(Singleton.class);
    bind(IUserService.class).to(UserService.class).in(Singleton.class);
    bind(IMessageService.class).to(MessageService.class).in(Singleton.class);
    bind(IAdminOrchestrator.class).to(AdminOrchestrator.class).in(Singleton.class);
    bind(ICustomerOrchestrator.class).to(CustomerOrchestrator.class).in(Singleton.class);
  }
}
