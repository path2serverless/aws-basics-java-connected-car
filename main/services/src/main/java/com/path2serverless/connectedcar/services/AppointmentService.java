package com.path2serverless.connectedcar.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import com.google.inject.Inject;
import com.path2serverless.connectedcar.services.context.IServiceContext;
import com.path2serverless.connectedcar.services.data.converters.RegistrationKeyConverter;
import com.path2serverless.connectedcar.services.data.converters.TimeslotKeyConverter;
import com.path2serverless.connectedcar.services.data.items.AppointmentItem;
import com.path2serverless.connectedcar.services.data.items.BaseItem;
import com.path2serverless.connectedcar.services.translator.ITranslator;
import com.path2serverless.connectedcar.shared.data.attributes.RegistrationKey;
import com.path2serverless.connectedcar.shared.data.attributes.TimeslotKey;
import com.path2serverless.connectedcar.shared.data.entities.Appointment;
import com.path2serverless.connectedcar.shared.services.IAppointmentService;

import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchWriteItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.WriteBatch;
import software.amazon.awssdk.enhanced.dynamodb.model.WriteBatch.Builder;

public class AppointmentService extends BaseService implements IAppointmentService {

  @Inject
  public AppointmentService(IServiceContext serviceContext, ITranslator translatorService) {
    super(serviceContext, translatorService);
  }
  
  @Override
  public String createAppointment(Appointment appointment) {
    if (appointment == null || !appointment.validate())
      throw new IllegalArgumentException();

    AppointmentItem item = getTranslator().translate(appointment);
    
    LocalDateTime now = LocalDateTime.now();
    
    DynamoDbTable<AppointmentItem> table = getAppointmentTable();
    
    item.setAppointmentId(UUID.randomUUID().toString());
    item.setCreateDateTime(now);
    item.setUpdateDateTime(now);
    
    table.putItem(item);

    GetItemEnhancedRequest request = GetItemEnhancedRequest.builder()
      .consistentRead(true)
      .key(Key.builder().partitionValue(item.getAppointmentId()).build())
      .build();

    table.getItem(request);

    return item.getAppointmentId();
  }

  @Override
  public void deleteAppointment(String appointmentId) {
    if (StringUtils.isBlank(appointmentId))
      throw new IllegalArgumentException();
    
    DynamoDbTable<AppointmentItem> table = getAppointmentTable();
    AppointmentItem item = table.getItem(Key.builder().partitionValue(appointmentId).build());
    
    if (item != null) {
      table.deleteItem(item);
    }
  }

  @Override
  public Appointment getAppointment(String appointmentId) {
    if (StringUtils.isBlank(appointmentId))
      throw new IllegalArgumentException();
    
    DynamoDbTable<AppointmentItem> table = getAppointmentTable();
    AppointmentItem item = table.getItem(Key.builder().partitionValue(appointmentId).build());
    
    return getTranslator().translate(item);
  }

  @Override
  public List<Appointment> getRegistrationAppointments(String username, String vin) {
    if (StringUtils.isBlank(username) || StringUtils.isBlank(vin))
      throw new IllegalArgumentException();
    
    RegistrationKey registrationKey = new RegistrationKey();
    
    registrationKey.setUsername(username);
    registrationKey.setVin(vin);
    
    DynamoDbTable<AppointmentItem> table = getAppointmentTable();
    DynamoDbIndex<AppointmentItem> index = table.index(BaseItem.REGISTRATION_APPOINTMENT_INDEX);

    RegistrationKeyConverter converter = new RegistrationKeyConverter();

    QueryConditional query = QueryConditional
      .keyEqualTo(Key.builder().partitionValue(converter.transformFrom(registrationKey))
      .build());

    SdkIterable<Page<AppointmentItem>> pages = index.query(
      QueryEnhancedRequest.builder()
       .queryConditional(query)
       .build());
    
    List<AppointmentItem> items = getItems(PageIterable.create(pages));

    return items
      .stream()
      .map(p -> getTranslator().translate(p))
      .collect(Collectors.toList());
  }

  @Override
  public List<Appointment> getTimeslotAppointments(String dealerId, String serviceDateHour) {
    if (StringUtils.isBlank(dealerId) || StringUtils.isBlank(serviceDateHour))
      throw new IllegalArgumentException();
    
    TimeslotKey timeslotKey = new TimeslotKey();
    
    timeslotKey.setDealerId(dealerId);
    timeslotKey.setServiceDateHour(serviceDateHour);
        
    DynamoDbTable<AppointmentItem> table = getAppointmentTable();
    DynamoDbIndex<AppointmentItem> index = table.index(BaseItem.TIMESLOT_APPOINTMENT_INDEX);

    TimeslotKeyConverter converter = new TimeslotKeyConverter();

    QueryConditional query = QueryConditional
      .keyEqualTo(Key.builder().partitionValue(converter.transformFrom(timeslotKey))
      .build());

    QueryEnhancedRequest request = QueryEnhancedRequest.builder()
      .queryConditional(query)
      .build();

    SdkIterable<Page<AppointmentItem>> pages = index.query(request);
    List<AppointmentItem> items = getItems(PageIterable.create(pages));

    return items
      .stream()
      .map(p -> getTranslator().translate(p))
      .collect(Collectors.toList());
  }

  public void batchUpdate(List<Appointment> appointments) {
    if (appointments == null)
      throw new IllegalArgumentException();

    DynamoDbTable<AppointmentItem> table = getAppointmentTable();

    Builder<AppointmentItem> batchBuilder = WriteBatch.builder(AppointmentItem.class).mappedTableResource(table);

    for (Appointment appointment : appointments) {
      batchBuilder.addPutItem(p -> p.item(getTranslator().translate(appointment)));
    }

    BatchWriteItemEnhancedRequest request = 
        BatchWriteItemEnhancedRequest
            .builder()
            .writeBatches(batchBuilder.build())
            .build();

    getServiceContext().getDynamoDbClient().batchWriteItem(request);
  }
}
