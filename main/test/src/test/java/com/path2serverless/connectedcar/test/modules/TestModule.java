package com.path2serverless.connectedcar.test.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
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
import com.path2serverless.connectedcar.test.services.FakeAppointmentService;
import com.path2serverless.connectedcar.test.services.FakeCustomerService;
import com.path2serverless.connectedcar.test.services.FakeDealerService;
import com.path2serverless.connectedcar.test.services.FakeEventService;
import com.path2serverless.connectedcar.test.services.FakeRegistrationService;
import com.path2serverless.connectedcar.test.services.FakeTimeslotService;
import com.path2serverless.connectedcar.test.services.FakeVehicleService;
import com.path2serverless.connectedcar.test.services.StubMessageService;
import com.path2serverless.connectedcar.test.services.StubUserService;

public class TestModule extends AbstractModule {

  @Override 
  protected void configure() {
    bind(IDealerService.class).to(FakeDealerService.class).in(Singleton.class);
    bind(ITimeslotService.class).to(FakeTimeslotService.class).in(Singleton.class);
    bind(IAppointmentService.class).to(FakeAppointmentService.class).in(Singleton.class);
    bind(ICustomerService.class).to(FakeCustomerService.class).in(Singleton.class);
    bind(IRegistrationService.class).to(FakeRegistrationService.class).in(Singleton.class);
    bind(IVehicleService.class).to(FakeVehicleService.class).in(Singleton.class);
    bind(IEventService.class).to(FakeEventService.class).in(Singleton.class);
    bind(IUserService.class).to(StubUserService.class).in(Singleton.class);
    bind(IMessageService.class).to(StubMessageService.class).in(Singleton.class);
    bind(IAdminOrchestrator.class).to(AdminOrchestrator.class).in(Singleton.class);
    bind(ICustomerOrchestrator.class).to(CustomerOrchestrator.class).in(Singleton.class);
  }
}
