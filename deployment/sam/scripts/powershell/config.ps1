
Import-Module AWSPowerShell.NetCore

$ErrorActionPreference = "Stop"

$workspacePath="[enter base path]/aws-basics-java-connected-car"
$bucket="[enter bucket name]"
$service="ConnectedCar"
$serviceLower="connectedcar"
$environment="Dev"
$environmentLower="dev"
$version="1.0.0"
$stage="api"

$number=(Get-Date -UFormat "%H%M%S")
$domain="connectedcar${number}"

Write-Host " "
Write-Host "*************************************************************"
Write-Host "*            Validating the config.ps1 variables            *"
Write-Host "*************************************************************"
Write-Host " "

if (!(Test-S3Bucket -BucketName ${bucket})) {
    Write-Host "Error: bucket is not valid"
    return
}

if (!(Test-Path -path ${workspacePath})) {
    Write-Host "Error: workspacePath is not valid"
    return
}  
