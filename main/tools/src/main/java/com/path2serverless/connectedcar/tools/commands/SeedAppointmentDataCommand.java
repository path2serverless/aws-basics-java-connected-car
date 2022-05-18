package com.path2serverless.connectedcar.tools.commands;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.path2serverless.connectedcar.shared.data.attributes.RegistrationKey;
import com.path2serverless.connectedcar.shared.data.attributes.TimeslotKey;
import com.path2serverless.connectedcar.shared.data.entities.Appointment;
import com.path2serverless.connectedcar.shared.data.entities.Dealer;
import com.path2serverless.connectedcar.shared.data.entities.Timeslot;
import com.path2serverless.connectedcar.shared.data.enums.StateCodeEnum;
import com.path2serverless.connectedcar.tools.data.CustomerData;

public class SeedAppointmentDataCommand extends BaseCommand {

  private DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

  public void execute() {
    File folder = new File(CUSTOMERS_FILE_PATH);
    File[] files = folder.listFiles();

    for (File file : files) {
      populateAppointments(file);
    }
  }

  private void populateAppointments(File file) {
    List<CustomerData> results = readCustomerData(file);
    List<CustomerData> filtered = results.subList(0, results.size() / 10);
    List<List<CustomerData>> batches = Lists.partition(filtered, 20);

    LocalDateTime today = LocalDateTime.now();
    LocalDateTime weekFromToday = today.plusDays(7);

    String startDate = formatter.format(today);
    String endDate = formatter.format(weekFromToday);

    batches.parallelStream().forEach(batch -> {
      StateCodeEnum stateCode = getRandomStateCode();

      List<Dealer> dealers = getDealerService().getDealers(stateCode);
      List<Appointment> appointments = new ArrayList<Appointment>();

      for (CustomerData data : batch) {
        Dealer dealer = getRandomItem(dealers);

        if (dealer != null) {
          List<Timeslot> timeslots = getTimeslotService().getTimeslots(dealer.getDealerId(), startDate, endDate);
          Timeslot timeslot = getRandomItem(timeslots);

          if (timeslot != null) {
            Appointment appointment = new Appointment();

            appointment.setTimeslotKey(new TimeslotKey());
            appointment.getTimeslotKey().setDealerId(dealer.getDealerId());
            appointment.getTimeslotKey().setServiceDateHour(timeslot.getServiceDateHour());
            appointment.setRegistrationKey(new RegistrationKey());
            appointment.getRegistrationKey().setUsername(data.getUsername());
            appointment.getRegistrationKey().setVin(data.getVin());

            appointments.add(appointment);
          }
        }
      }

      getAppointmentService().batchUpdate(appointments);
    });
  }
}
