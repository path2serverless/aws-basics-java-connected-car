
. "./config.ps1"

Write-Host " "
Write-Host "*************************************************************"
Write-Host "*       Uploading the CloudFormation template files         *"
Write-Host "*************************************************************"
Write-Host " "

Write-S3Object `
    -BucketName ${bucket} `
    -File "${workspacePath}/deployment/sam/templates/services.yaml" `
    -Key "${service}/${environment}/services.yaml"

Write-S3Object `
    -BucketName ${bucket} `
    -File "${workspacePath}/deployment/sam/templates/customer.yaml" `
    -Key "${service}/${environment}/customer.yaml"

Write-S3Object `
    -BucketName ${bucket} `
    -File "${workspacePath}/deployment/sam/templates/admin.yaml" `
    -Key "${service}/${environment}/admin.yaml"

Write-S3Object `
    -BucketName ${bucket} `
    -File "${workspacePath}/deployment/sam/templates/vehicle.yaml" `
    -Key "${service}/${environment}/vehicle.yaml"

$templateBody = Get-Content -Path "${workspacePath}/deployment/sam/templates/master.yaml" -raw

if (-Not (Test-CFNStack -Region ${region} -StackName "${service}${environment}"))
{
  Write-Host " "
  Write-Host "*************************************************************"
  Write-Host "*            Executing the create stack command             *"
  Write-Host "*************************************************************"
  Write-Host " "

  New-CFNStack `
      -Region ${region} `
      -StackName "${service}${environment}" `
      -TemplateBody $templateBody `
      -Parameter @( @{ ParameterKey="BucketName"; ParameterValue="${bucket}" }, `
                    @{ ParameterKey="ServiceName"; ParameterValue="${service}" }, `
                    @{ ParameterKey="ServiceNameLower"; ParameterValue="${serviceLower}" }, `
                    @{ ParameterKey="EnvironmentName"; ParameterValue="${environment}" }, `
                    @{ ParameterKey="EnvironmentNameLower"; ParameterValue="${environmentLower}" }, `
                    @{ ParameterKey="VersionNumber"; ParameterValue="${version}" }, `
                    @{ ParameterKey="StageName"; ParameterValue="${stage}" }, `
                    @{ ParameterKey="UserPoolDomainName"; ParameterValue="${domain}" }) `
      -Capability CAPABILITY_IAM,CAPABILITY_NAMED_IAM,CAPABILITY_AUTO_EXPAND

  Wait-CFNStack `
      -Region ${region} `
      -StackName "${service}${environment}" `
      -Status CREATE_COMPLETE,ROLLBACK_COMPLETE `
      -Timeout 1200
}
else {
  Write-Host " "
  Write-Host "*************************************************************"
  Write-Host "*            Executing the update stack command             *"
  Write-Host "*************************************************************"
  Write-Host " "

  Update-CFNStack `
      -Region ${region} `
      -StackName "${service}${environment}" `
      -TemplateBody $templateBody `
      -Parameter @( @{ ParameterKey="BucketName"; ParameterValue="${bucket}" }, `
                    @{ ParameterKey="ServiceName"; ParameterValue="${service}" }, `
                    @{ ParameterKey="ServiceNameLower"; ParameterValue="${serviceLower}" }, `
                    @{ ParameterKey="EnvironmentName"; ParameterValue="${environment}" }, `
                    @{ ParameterKey="EnvironmentNameLower"; ParameterValue="${environmentLower}" }, `
                    @{ ParameterKey="VersionNumber"; ParameterValue="${version}" }, `
                    @{ ParameterKey="StageName"; ParameterValue="${stage}" }, `
                    @{ ParameterKey="UserPoolDomainName"; ParameterValue="${domain}" }) `
      -Capability CAPABILITY_IAM,CAPABILITY_NAMED_IAM,CAPABILITY_AUTO_EXPAND

  Wait-CFNStack `
      -Region ${region} `
      -StackName "${service}${environment}" `
      -Status UPDATE_COMPLETE,ROLLBACK_COMPLETE `
      -Timeout 1200 
}

Write-Host " "
Write-Host "*************************************************************"
Write-Host "*                Listing the stack outputs                  *"
Write-Host "*************************************************************"
Write-Host " "

$outputs = (Get-CFNStack -Region ${region} -StackName "${service}${environment}").Outputs

Write-Output $outputs

Write-Host " "

