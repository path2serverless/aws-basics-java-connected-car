
Import-Module AWSPowerShell.NetCore

$ErrorActionPreference = "Stop"

$workspacePath="[enter base path]/ref-aws-java-connected-car"
$bucket="[enter bucket name]"
$service="ConnectedCar"
$serviceLower="connectedcar"
$environment="Dev"
$environmentLower="dev"
$version="1.0.0"
$stage="api"

$number=(Get-Date -UFormat "%H%M%S")
$domain="connectedcar${number}"

$account=(Get-STSCallerIdentity).Account
$region="[enter region code]"

Write-Host " "
Write-Host "*************************************************************"
Write-Host "*            Validating the config.ps1 variables            *"
Write-Host "*************************************************************"
Write-Host " "

if ("${account}" -eq "") {
    Write-Host "Error: account is not valid"
    return
}

if ("${region}" -eq "") {
    Write-Host "Error: region is not valid"
    return
}

if (!(Test-S3Bucket -BucketName ${bucket})) {
    Write-Host "Error: bucket is not valid"
    return
}

if (!(Test-Path -path ${workspacePath})) {
    Write-Host "Error: workspacePath is not valid"
    return
}  

