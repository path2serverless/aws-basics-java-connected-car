#!/bin/zsh

service="ConnectedCar"
environment="Dev"
region=$(aws configure get region)
stage="api"

domain=$(aws cloudformation describe-stacks \
    --stack-name ${service}${environment} \
    --query "Stacks[0].Outputs[?OutputKey=='UserPoolDomainName'].OutputValue" \
    --output text)

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

callbackUrl=$(aws cognito-idp describe-user-pool-client \
    --user-pool-id $userPoolId \
    --client-id $clientId \
    --query 'UserPoolClient.CallbackURLs[0]' \
    --output text)

adminApi=$(aws cloudformation describe-stacks \
    --stack-name ${service}${environment} \
    --query "Stacks[0].Outputs[?OutputKey=='AdminAPI'].OutputValue" \
    --output text)

apiKey=$(aws apigateway get-api-keys \
    --query "items[?contains(stageKeys,\`${adminApi}/${stage}\`)==\`true\`].value" \
    --include-values \
    --output text)

echo " "
echo "Cognito Callback URL: ${callbackUrl}"
echo " "

echo "Cognito Auth URL: https://${domain}.auth.${region}.amazoncognito.com/login"
echo " "

echo "Cognito Token URL: https://${domain}.auth.${region}.amazoncognito.com/oauth2/token"
echo " "

echo "ClientId: ${clientId}"
echo " "

echo "ClientSecret: ${clientSecret}"
echo " "

echo "ApiKey: ${apiKey}"
echo " "
