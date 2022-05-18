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

public class Translator implements ITranslator {

  public Appointment translate(AppointmentItem item) {
    if (item != null) {
      Appointment appointment = new Appointment();

      appointment.setAppointmentId(item.getAppointmentId());
      appointment.setTimeslotKey(item.getTimeslotKey());
      appointment.setRegistrationKey(item.getRegistrationKey());
      appointment.setCreateDateTime(item.getCreateDateTime());
      appointment.setUpdateDateTime(item.getUpdateDateTime());

      return appointment;
    }

    return null;
  }

  public AppointmentItem translate(Appointment appointment) {
    if (appointment != null) {
      AppointmentItem item = new AppointmentItem();

      item.setAppointmentId(appointment.getAppointmentId());
      item.setTimeslotKey(appointment.getTimeslotKey());
      item.setRegistrationKey(appointment.getRegistrationKey());
      item.setCreateDateTime(appointment.getCreateDateTime());
      item.setUpdateDateTime(appointment.getUpdateDateTime());

      return item;
    }

    return null;
  }

  public Customer translate(CustomerItem item) {
    if (item != null) {
      Customer customer = new Customer();

      customer.setUsername(item.getUsername());
      customer.setFirstname(item.getFirstname());
      customer.setLastname(item.getLastname());
      customer.setPhoneNumber(item.getPhoneNumber());
      customer.setCreateDateTime(item.getCreateDateTime());
      customer.setUpdateDateTime(item.getCreateDateTime());

      return customer;
    }

    return null;
  }

  public CustomerItem translate(Customer customer) {
    if (customer != null) {
      CustomerItem item = new CustomerItem();

      item.setUsername(customer.getUsername());
      item.setFirstname(customer.getFirstname());
      item.setLastname(customer.getLastname());
      item.setPhoneNumber(customer.getPhoneNumber());
      item.setCreateDateTime(customer.getCreateDateTime());
      item.setUpdateDateTime(customer.getCreateDateTime());

      if (customer.getLastname() != null) {
        item.setLastnameLower(customer.getLastname().toLowerCase());
      }

      return item;
    }

    return null;
  }

  public Dealer translate(DealerItem item) {
    if (item != null) {
      Dealer dealer = new Dealer();

      dealer.setDealerId(item.getDealerId());
      dealer.setName(item.getName());
      dealer.setAddress(item.getAddress());
      dealer.setStateCode(item.getStateCode());
      dealer.setCreateDateTime(item.getCreateDateTime());
      dealer.setUpdateDateTime(item.getUpdateDateTime());

      return dealer;
    }

    return null;
  }

  public DealerItem translate(Dealer dealer) {
    if (dealer != null) {
      DealerItem item = new DealerItem();

      item.setDealerId(dealer.getDealerId());
      item.setName(dealer.getName());
      item.setAddress(dealer.getAddress());
      item.setStateCode(dealer.getStateCode());
      item.setCreateDateTime(dealer.getCreateDateTime());
      item.setUpdateDateTime(dealer.getUpdateDateTime());

      return item;
    }

    return null;
  }

  public Event translate(EventItem item) {
    if (item != null) {
      Event event = new Event();

      event.setVin(item.getVin());
      event.setTimestamp(item.getTimestamp());
      event.setEventCode(item.getEventCode());
      event.setCreateDateTime(item.getCreateDateTime());
      event.setUpdateDateTime(item.getUpdateDateTime());

      return event;
    }

    return null;
  }

  public EventItem translate(Event event) {
    if (event != null) {
      EventItem item = new EventItem();

      item.setVin(event.getVin());
      item.setTimestamp(event.getTimestamp());
      item.setEventCode(event.getEventCode());
      item.setCreateDateTime(event.getCreateDateTime());
      item.setUpdateDateTime(event.getUpdateDateTime());

      return item;
    }

    return null;
  }

  public Registration translate(RegistrationItem item) {
    if (item != null) {
      Registration registration = new Registration();

      registration.setUsername(item.getUsername());
      registration.setVin(item.getVin());
      registration.setStatusCode(item.getStatusCode());
      registration.setCreateDateTime(item.getCreateDateTime());
      registration.setUpdateDateTime(item.getUpdateDateTime());

      return registration;
    }

    return null;
  }

  public RegistrationItem translate(Registration registration) {
    if (registration != null) {
      RegistrationItem item = new RegistrationItem();

      item.setUsername(registration.getUsername());
      item.setVin(registration.getVin());
      item.setStatusCode(registration.getStatusCode());
      item.setCreateDateTime(registration.getCreateDateTime());
      item.setUpdateDateTime(registration.getUpdateDateTime());

      return item;
    }

    return null;
  }

  public Timeslot translate(TimeslotItem item) {
    if (item != null) {
      Timeslot timeslot = new Timeslot();

      timeslot.setDealerId(item.getDealerId());
      timeslot.setServiceDateHour(item.getServiceDateHour());
      timeslot.setServiceBayCount(item.getServiceBayCount());
      timeslot.setCreateDateTime(item.getCreateDateTime());
      timeslot.setUpdateDateTime(item.getUpdateDateTime());

      return timeslot;      
    }

    return null;
  }

  public TimeslotItem translate(Timeslot timeslot) {
    if (timeslot != null) {
      TimeslotItem item = new TimeslotItem();

      item.setDealerId(timeslot.getDealerId());
      item.setServiceDateHour(timeslot.getServiceDateHour());
      item.setServiceBayCount(timeslot.getServiceBayCount());
      item.setCreateDateTime(timeslot.getCreateDateTime());
      item.setUpdateDateTime(timeslot.getUpdateDateTime());

      return item;      
    }

    return null;
  }

  public Vehicle translate(VehicleItem item) {
    if (item != null) {
      Vehicle vehicle = new Vehicle();

      vehicle.setVin(item.getVin());
      vehicle.setColors(item.getColors());
      vehicle.setVehiclePin(item.getVehiclePin());
      vehicle.setVehicleCode(item.getVehicleCode());
      vehicle.setCreateDateTime(item.getCreateDateTime());
      vehicle.setUpdateDateTime(item.getUpdateDateTime());

      return vehicle;
    }

    return null;
  }

  public VehicleItem translate(Vehicle vehicle) {
    if (vehicle != null) {
      VehicleItem item = new VehicleItem();

      item.setVin(vehicle.getVin());
      item.setColors(vehicle.getColors());
      item.setVehiclePin(vehicle.getVehiclePin());
      item.setVehicleCode(vehicle.getVehicleCode());
      item.setCreateDateTime(vehicle.getCreateDateTime());
      item.setUpdateDateTime(vehicle.getUpdateDateTime());

      return item;
    }

    return null;
  }

}