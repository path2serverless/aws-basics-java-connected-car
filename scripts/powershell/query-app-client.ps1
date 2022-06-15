
Import-Module AWSPowerShell.NetCore

$service="ConnectedCar"
$environment="Dev"

$userPoolId = ((Get-CFNStack `
    -StackName "${service}${environment}").Outputs `
    | Where-Object {$_.OutputKey -EQ 'UserPoolId'}).OutputValue

$clientId = ((Get-CFNStack `
    -StackName "${service}${environment}").Outputs `
    | Where-Object {$_.OutputKey -EQ 'UserClientId'}).OutputValue

$client = (Get-CGIPUserPoolClient `
    -UserPoolId $userPoolId `
    -ClientId $clientId)

Write-Output $client

Write-Host " "

