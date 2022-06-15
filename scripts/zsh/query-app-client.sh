#!/bin/zsh

service="ConnectedCar"
environment="Dev"

userPoolId=$(aws cloudformation describe-stacks \
    --stack-name ${service}${environment} \
    --query "Stacks[0].Outputs[?OutputKey=='UserPoolId'].OutputValue" \
    --output text)

clientId=$(aws cloudformation describe-stacks \
    --stack-name ${service}${environment} \
    --query "Stacks[0].Outputs[?OutputKey=='UserClientId'].OutputValue" \
    --output text)

aws cognito-idp describe-user-pool-client \
    --user-pool-id $userPoolId \
    --client-id $clientId \
    --output json

echo " "

