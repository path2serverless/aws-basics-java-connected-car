#!/bin/zsh

source config.sh

echo " "
echo "*************************************************************"
echo "*            Building the container image locally           *"
echo "*************************************************************"
echo " "

cd ${workspacePath}

aws ecr get-login-password \
    --region ${region} | docker login \
    --username AWS \
    --password-stdin ${account}.dkr.ecr.${region}.amazonaws.com

docker build -t ${serviceLower}-${environmentLower} --pull -f Dockerfile .

docker tag \
    ${serviceLower}-${environmentLower}\:latest \
    ${account}.dkr.ecr.${region}.amazonaws.com/${serviceLower}-${environmentLower}\:latest

echo " "
echo "*************************************************************"
echo "*            Pushing the image to the repository            *"
echo "*************************************************************"
echo " "

docker push ${account}.dkr.ecr.${region}.amazonaws.com/${serviceLower}-${environmentLower}\:latest

echo " "
echo "*************************************************************"
echo "*                 Running the maven build                   *"
echo "*************************************************************"
echo " "

mvn clean install -q -f ${workspacePath}/main/pom.xml

echo " "
echo "*************************************************************"
echo "*      Uploading the Lambda zip files to the S3 folder      *"
echo "*************************************************************"
echo " "

aws s3 cp \
    ${workspacePath}/main/authorizers/target/authorizers-SNAPSHOT-AUTHORIZERS.jar \
    s3://${bucket}/${service}/${environment}/authorizers-${version}.jar

aws s3 cp \
    ${workspacePath}/main/events/target/events-SNAPSHOT-EVENTS.jar \
    s3://${bucket}/${service}/${environment}/events-${version}.jar

echo " "
