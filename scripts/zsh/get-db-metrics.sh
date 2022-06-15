
service="ConnectedCar"
environment="Dev"

start=$(date -u -v-1H +"%Y-%m-%dT%H:%M:%S")
end=$(date -u +"%Y-%m-%dT%H:%M:%S")

tables=("Dealer" \
        "Timeslot" \
        "Appointment" \
        "Customer" \
        "Vehicle" \
        "Registration" \
        "Event")

function output_item_metric {
  metrics=$1
  dimensions=$2
  startTime=$3
  endTime=$4
  item=$5

  sum=$(aws cloudwatch get-metric-statistics \
      --namespace AWS/DynamoDB \
      --metric-name ${metrics} \
      --dimensions ${dimensions} \
      --start-time ${startTime} \
      --end-time ${endTime} \
      --period 3600 \
      --statistics Sum \
      --query 'Datapoints[0].Sum' \
      --output text)

  echo "${item} ${sum}"
}

function output_service_metrics {
  metric=$1

  echo " "
  echo "Table metrics for ${metric} for the past hour"
  echo " "

  for table in ${tables[@]}; do
    dimensions="Name=TableName,Value=${service}_${table}_${environment}"
    item="${service}_${table}_${environment}"

    output_item_metric $metric $dimensions $start $end $item
  done

  echo " "
  echo "Index metrics for ${metric} for the past hour"
  echo " "

  output_item_metric $metric \
      "Name=TableName,Value=${service}_Appointment_${environment} Name=GlobalSecondaryIndexName,Value=TimeslotAppointmentIndex" \
      $start \
      $end \
      "TimeslotAppointmentIndex"

  output_item_metric $metric \
      "Name=TableName,Value=${service}_Appointment_${environment} Name=GlobalSecondaryIndexName,Value=RegistrationAppointmentIndex" \
      $start \
      $end \
      "RegistrationAppointmentIndex"

  output_item_metric $metric \
      "Name=TableName,Value=${service}_Registration_${environment} Name=GlobalSecondaryIndexName,Value=VehicleRegistrationIndex" \
      $start \
      $end \
      "VehicleRegistrationIndex"
}

output_service_metrics "ConsumedReadCapacityUnits"
output_service_metrics "ConsumedWriteCapacityUnits"




