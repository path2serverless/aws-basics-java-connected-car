package com.path2serverless.connectedcar.tools.commands;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.path2serverless.connectedcar.shared.data.entities.Timeslot;
import com.path2serverless.connectedcar.tools.data.DealerData;

import org.apache.commons.lang3.StringUtils;

public class SeedDealerDataCommand extends BaseCommand {

  private DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

  public void execute() {
    File folder = new File(DEALERS_FILE_PATH);
    File[] files = folder.listFiles();

    for (File file : files) {
      populateDealers(file);
    }
  }

  private void populateDealers(File file) {
    LocalDateTime today = LocalDateTime.now();

    List<DealerData> results = readDealerData(file);
    List<List<DealerData>> chunks = Lists.partition(results, 20);

    chunks.parallelStream().forEach(d -> {
      for (DealerData dealer : d) {
        String dealerId = getDealerService().createDealer(dealer.GetDealer());

        for (int dt = 0; dt < 7; dt++)
        {
          List<Timeslot> timeslots = new ArrayList<Timeslot>();

          for (int h = 8; h <= 16; h++)
          {
            LocalDateTime serviceDate = today.plusDays(dt);
            String serviceDateHour = formatter.format(serviceDate) + "-" + StringUtils.leftPad(Integer.toString(h), 2, "0");

            Timeslot timeslot = new Timeslot();

            timeslot.setDealerId(dealerId);
            timeslot.setServiceDateHour(serviceDateHour);
            timeslot.setServiceBayCount(10);

            timeslots.add(timeslot);
          }

          getTimeslotService().batchUpdate(timeslots);
        }
      }
    });
  }
}
