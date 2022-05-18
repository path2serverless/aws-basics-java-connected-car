
Import-Module AWSPowerShell.NetCore

$service="ConnectedCar"
$environment="Dev"

$date = (Get-Date).AddDays(-4)
$epoch = ([datetime] '1970-01-01Z').ToUniversalTime()
$start = [math]::Round((New-TimeSpan -Start $epoch -End $date).TotalSeconds)

$api = ((Get-CFNStack `
    -StackName "${service}${environment}").Outputs `
    | Where-Object {$_.OutputKey -EQ 'AdminAPI'}).OutputValue

$group="API-Gateway-Execution-Logs_${api}/api"
$pattern="request body before transformations"

$events = (Get-CWLFilteredLogEvent `
    -LogGroupName "${group}" `
    -FilterPattern "${pattern}" `
    -StartTime "${start}").Events

Write-Output $events