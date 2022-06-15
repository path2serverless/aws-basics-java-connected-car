#!/bin/zsh

service="ConnectedCar"
environment="Dev"

aws cloudformation describe-stacks \
    --stack-name ${service}${environment} \
    --query 'Stacks[].Outputs[]' \
    --output table

echo " "

apiKey=$(aws apigateway get-api-keys \
    --query 'items[?contains(name,`Admin`)==`true`].value' \
    --include-values \
    --output text)

echo "ApiKey: ${apiKey}"

userPoolId=$(aws cloudformation describe-stacks \
    --stack-name ${service}${environment} \
    --query "Stacks[0].Outputs[?OutputKey=='UserPoolId'].OutputValue" \
    --output text)

clientId=$(aws cloudformation describe-stacks \
    --stack-name ${service}${environment} \
    --query "Stacks[0].Outputs[?OutputKey=='UserClientId'].OutputValue" \
    --output text)

clientSecret=$(aws cognito-idp describe-user-pool-client \
    --user-pool-id $userPoolId \
    --client-id $clientId \
    --query 'UserPoolClient.ClientSecret' \
    --output text)

echo "ClientSecret: ${clientSecret}"

echo " "
