
Import-Module AWSPowerShell.NetCore

$service="ConnectedCar"
$environment="Dev"

$outputs = (Get-CFNStack -StackName "${service}${environment}").Outputs

Write-Output $outputs

Write-Host " "
