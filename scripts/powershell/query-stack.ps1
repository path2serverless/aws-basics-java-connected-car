
Import-Module AWSPowerShell.NetCore

$service="ConnectedCar"
$environment="Dev"
$stage="api"

$outputs = (Get-CFNStack -StackName "${service}${environment}").Outputs

Write-Output $outputs

Write-Host " "

$adminApi = ((Get-CFNStack `
    -StackName "${service}${environment}").Outputs `
    | Where-Object {$_.OutputKey -EQ 'AdminAPI'}).OutputValue

$apiKey = (Get-AGApiKeyList `
    | Select-Object -ExpandProperty Items `
    | Where-Object { $_.StageKeys -EQ "${adminApi}/${stage}"}).Id

$apiKeyValue = (Get-AGApiKey -ApiKey $apiKey -IncludeValue 1).Value

Write-Host "API Key: ${apiKeyValue}"

$userPoolId = ((Get-CFNStack `
    -StackName "${service}${environment}").Outputs `
    | Where-Object {$_.OutputKey -EQ 'UserPoolId'}).OutputValue

$clientId = ((Get-CFNStack `
    -StackName "${service}${environment}").Outputs `
    | Where-Object {$_.OutputKey -EQ 'UserClientId'}).OutputValue

$clientSecret = (Get-CGIPUserPoolClient `
    -UserPoolId $userPoolId `
    -ClientId $clientId).ClientSecret

Write-Host "Client Secret: ${clientSecret}"

Write-Host " "

