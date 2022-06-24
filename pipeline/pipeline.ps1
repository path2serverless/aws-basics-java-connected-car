
$deployment=$args[0]

if ($deployment -eq $null) {
    Write-Host "Usage: must include deployment parameter (sam, openapi, containers)"
    return
}
elseif (("${deployment}" -ne "sam") -and ("${deployment}" -ne "openapi") -and ("${deployment}" -ne "containers")) {
    Write-Host "Usage: must include deployment parameter (sam, openapi, containers)"
    return
}

$workspacePath="[enter base path]/aws-basics-java-connected-car"
$zip="connectedcar.zip"
$bucket="[enter bucket name]"
$service="ConnectedCar"
$serviceLower="connectedcar"
$environment="Dev"
$environmentLower="dev"
$stage="api"

$buildFile="buildspec/${deployment}.buildspec.yml"
$deployFile="deployment/${deployment}/templates/master.yaml"
$testFile="buildspec/test.buildspec.yml"

Import-Module AWSPowerShell.NetCore

$ErrorActionPreference = "Stop"

Write-Host " "
Write-Host "*************************************************************"
Write-Host "*                 Performing a maven clean                  *"
Write-Host "*************************************************************"
Write-Host " "

mvn clean -q -f ${workspacePath}/main/pom.xml

Write-Host " "
Write-Host "*************************************************************"
Write-Host "*               Creating zip file of solution               *"
Write-Host "*************************************************************"
Write-Host " "

if (Test-Path ${env:TEMP}/${zip}) {
    Remove-Item ${env:TEMP}/${zip}
}

Compress-Archive `
    -Path ${workspacePath}\* `
    -DestinationPath ${env:TEMP}/${zip}

Write-Host " "
Write-Host "*************************************************************"
Write-Host "*              Uploading zip file to S3 folder              *"
Write-Host "*************************************************************"
Write-Host " "

Write-S3Object `
    -BucketName ${bucket} `
    -File ${env:TEMP}/${zip} `
    -Key ${service}/Repo/${zip}

Write-Host " "
Write-Host "*************************************************************"
Write-Host "*      Executing create stack command for the pipeline      *"
Write-Host "*************************************************************"
Write-Host " "

$templateBody = Get-Content -Path "${workspacePath}/pipeline/pipeline.yaml" -raw

New-CFNStack `
    -StackName "${service}Pipeline${environment}" `
    -TemplateBody ${templateBody} `
    -Parameter @( @{ ParameterKey="BucketName"; ParameterValue="${bucket}" }, `
                  @{ ParameterKey="ZipFile"; ParameterValue="${zip}" }, `
                  @{ ParameterKey="ServiceName"; ParameterValue="${service}" }, `
                  @{ ParameterKey="ServiceNameLower"; ParameterValue="${serviceLower}" }, `
                  @{ ParameterKey="EnvironmentName"; ParameterValue="${environment}" }, `
                  @{ ParameterKey="EnvironmentNameLower"; ParameterValue="${environmentLower}" }, `
                  @{ ParameterKey="StageName"; ParameterValue="${stage}" }, `
                  @{ ParameterKey="BuildFile"; ParameterValue="${buildFile}" }, `
                  @{ ParameterKey="TestFile"; ParameterValue="${testFile}" }, `
                  @{ ParameterKey="DeployFile"; ParameterValue="${deployFile}" }) `
    -Capability CAPABILITY_IAM,CAPABILITY_NAMED_IAM,CAPABILITY_AUTO_EXPAND

Write-Host " "
