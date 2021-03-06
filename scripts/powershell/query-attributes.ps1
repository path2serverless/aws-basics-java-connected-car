
Import-Module AWSPowerShell.NetCore

$service="ConnectedCar"
$environment="Dev"
$stage="api"
$region="ca-central-1"

$domain = ((Get-CFNStack `
    -StackName "${service}${environment}").Outputs `
    | Where-Object {$_.OutputKey -EQ 'UserPoolDomainName'}).OutputValue

$userPoolId = ((Get-CFNStack `
    -StackName "${service}${environment}").Outputs `
    | Where-Object {$_.OutputKey -EQ 'UserPoolId'}).OutputValue

$clientId = ((Get-CFNStack `
    -StackName "${service}${environment}").Outputs `
    | Where-Object {$_.OutputKey -EQ 'UserClientId'}).OutputValue

$clientSecret = (Get-CGIPUserPoolClient `
    -UserPoolId $userPoolId `
    -ClientId $clientId).ClientSecret

$callbackUrl = (Get-CGIPUserPoolClient `
    -UserPoolId $userPoolId `
    -ClientId $clientId).CallbackURLs[0]

$adminApi = ((Get-CFNStack `
    -StackName "${service}${environment}").Outputs `
    | Where-Object {$_.OutputKey -EQ 'AdminAPI'}).OutputValue

$apiKey = (Get-AGApiKeyList `
    | Select-Object -ExpandProperty Items `
    | Where-Object { $_.StageKeys -EQ "${adminApi}/${stage}"}).Id

$apiKeyValue = (Get-AGApiKey -ApiKey $apiKey -IncludeValue 1).Value

Write-Host " "
Write-Host "Cognito Callback URL: ${callbackUrl}"
Write-Host " "

Write-Host "Cognito Auth URL: https://${domain}.auth.${region}.amazoncognito.com/login"
Write-Host " "

Write-Host "Cognito Token URL: https://${domain}.auth.${region}.amazoncognito.com/oauth2/token"
Write-Host " "

Write-Host "ClientId: ${clientId}"
Write-Host " "

Write-Host "ClientSecret: ${clientSecret}"
Write-Host " "

Write-Host "ApiKey: ${apiKeyValue}"
Write-Host " "
