#!/bin/zsh

set -e

workspacePath="[enter base path]/aws-basics-java-connected-car"
bucket="[enter bucket name]"
service="ConnectedCar"
serviceLower="connectedcar"
environment="Dev"
environmentLower="dev"
version="1.0.0"
stage="api"

number=$(date +"%H%M%S")
domain="connectedcar${number}"

echo " "
echo "*************************************************************"
echo "*            Validating the config.sh variables             *"
echo "*************************************************************"
echo " "

if ! [ -d "${workspacePath}" ] ; then
  echo "Error: workspacePath is not valid"
  exit 1
fi

aws s3api head-bucket --bucket ${bucket}


