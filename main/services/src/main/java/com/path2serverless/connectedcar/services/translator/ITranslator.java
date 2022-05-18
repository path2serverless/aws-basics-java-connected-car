package com.path2serverless.connectedcar.services.translator;

import com.path2serverless.connectedcar.services.data.items.AppointmentItem;
import com.path2serverless.connectedcar.services.data.items.CustomerItem;
import com.path2serverless.connectedcar.services.data.items.DealerItem;
import com.path2serverless.connectedcar.services.data.items.EventItem;
import com.path2serverless.connectedcar.services.data.items.RegistrationItem;
import com.path2serverless.connectedcar.services.data.items.TimeslotItem;
import com.path2serverless.connectedcar.services.data.items.VehicleItem;
import com.path2serverless.connectedcar.shared.data.entities.Appointment;
import com.path2serverless.connectedcar.shared.data.entities.Customer;
import com.path2serverless.connectedcar.shared.data.entities.Dealer;
import com.path2serverless.connectedcar.shared.data.entities.Event;
import com.path2serverless.connectedcar.shared.data.entities.Registration;
import com.path2serverless.connectedcar.shared.data.entities.Timeslot;
import com.path2serverless.connectedcar.shared.data.entities.Vehicle;

public interface ITranslator {
  
  public Appointment translate(AppointmentItem item);
  public AppointmentItem translate(Appointment appointment);

  public Customer translate(CustomerItem item);
  public CustomerItem translate(Customer customer);

  public Dealer translate(DealerItem item);
  public DealerItem translate(Dealer dealer);

  public Event translate(EventItem item);
  public EventItem translate(Event event);

  public Registration translate(RegistrationItem item);
  public RegistrationItem translate(Registration registration);

  public Timeslot translate(TimeslotItem item);
  public TimeslotItem translate(Timeslot timeslot);

  public Vehicle translate(VehicleItem item);
  public VehicleItem translate(Vehicle vehicle);

}
