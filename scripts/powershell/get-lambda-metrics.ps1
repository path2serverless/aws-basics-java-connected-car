
Import-Module AWSPowerShell.NetCore

$service="ConnectedCar"
$environment="Dev"

$start=[System.DateTime]::UtcNow.AddMinutes(-30)
$end=[System.DateTime]::UtcNow

$dimensions=@{Name = "FunctionName"; Value = "${service}_Admin_GetDealers_${environment}"}

$stats=(Get-CWMetricStatistic `
    -Namespace AWS/Lambda `
    -MetricName Duration `
    -Dimension $dimensions `
    -Period 60 `
    -Statistic Average `
    -UtcStartTime $start `
    -UtcEndTime $end).DataPoints[0]

Write-Output $stats



