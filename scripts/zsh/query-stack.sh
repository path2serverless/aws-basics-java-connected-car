#!/bin/zsh

service="ConnectedCar"
environment="Dev"

aws cloudformation describe-stacks \
    --stack-name ${service}${environment} \
    --query 'Stacks[].Outputs[]' \
    --output table

echo " "

