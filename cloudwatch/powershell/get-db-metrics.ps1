
Import-Module AWSPowerShell.NetCore

$service="ConnectedCar"
$environment="Dev"

$start=[System.DateTime]::UtcNow.AddHours(-1)
$end=[System.DateTime]::UtcNow

$tables = @(
    "Dealer", 
    "Timeslot", 
    "Appointment",
    "Customer",
    "Vehicle",
    "Registration",
    "Event")


function OutputItemMetric(
  $metrics,
  $dimensions,
  $startTime,
  $endTime,
  $item) 
{

$sum=(Get-CWMetricStatistic `
    -Namespace AWS/DynamoDB `
    -MetricName $metrics `
    -Dimension $dimensions `
    -Period 3600 `
    -Statistic Sum `
    -UtcStartTime $startTime `
    -UtcEndTime $endTime).Datapoints[0].Sum

    Write-Output "${item} ${sum}"
}

function OutputServiceMetrics($metric) {
  Write-Host " "
  Write-Host "Table metrics for ${metric} for the past hour"
  Write-Host " "

  Foreach ($table in $tables)
  {
    $dimensions=@{Name = "TableName"; Value = "${service}_${table}_${environment}"}
    $item="${service}_${table}_${environment}"

    OutputItemMetric $metric $dimensions $start $end $item
  }  

  Write-Host " "
  Write-Host "Index metrics for ${metric} for the past hour"
  Write-Host " "

  OutputItemMetric $metric `
      @(@{Name = "TableName"; Value = "${service}_Appointment_${environment}"},@{Name = "GlobalSecondaryIndexName"; Value = "TimeslotAppointmentIndex"}) `
      $start `
      $end `
      "TimeslotAppointmentIndex"

  OutputItemMetric $metric `
      @(@{Name = "TableName"; Value = "${service}_Appointment_${environment}"},@{Name = "GlobalSecondaryIndexName"; Value = "RegistrationAppointmentIndex"}) `
      $start `
      $end `
      "RegistrationAppointmentIndex"

  OutputItemMetric $metric `
      @(@{Name = "TableName"; Value = "${service}_Registration_${environment}"},@{Name = "GlobalSecondaryIndexName"; Value = "VehicleRegistrationIndex"}) `
      $start `
      $end `
      "VehicleRegistrationIndex"
}

OutputServiceMetrics "ConsumedReadCapacityUnits"
OutputServiceMetrics "ConsumedWriteCapacityUnits"


