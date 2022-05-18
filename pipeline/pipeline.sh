#!/bin/zsh

if [ -z "$1" ]; then
    echo "Usage: must include deployment parameter (sam, openapi, containers)"
    exit 1
elif [ "$1" != "sam" ] && [ "$1" != "openapi" ] && [ "$1" != "containers" ]; then
    echo "Usage: must include deployment parameter (sam, openapi, containers)"
    exit 1
fi

deployment="$1"

workspacePath="[enter base path]/ref-aws-java-connected-car"
zip="connectedcar.zip"
bucket="[enter bucket name]"
service="ConnectedCar"
serviceLower="connectedcar"
environment="Dev"
environmentLower="dev"
stage="api"

buildFile="buildspec/${deployment}.buildspec.yml"
deployFile="deployment/${deployment}/templates/master.yaml"
testFile="buildspec/test.buildspec.yml"

echo " "
echo "*************************************************************"
echo "*                 Performing a maven clean                  *"
echo "*************************************************************"
echo " "

mvn clean -q -f ${workspacePath}/main/pom.xml

echo " "
echo "*************************************************************"
echo "*               Creating zip file of solution               *"
echo "*************************************************************"
echo " "

cd ${workspacePath}

rm -f ${TMPDIR}/${zip}

zip -r ${TMPDIR}/${zip} . -x '*.git*' -x '*/*.zip' -x '*/*.jar' -x '*.DS_Store*'

echo " "
echo "*************************************************************"
echo "*              Uploading zip file to S3 folder              *"
echo "*************************************************************"
echo " "

aws s3 cp \
    ${TMPDIR}/${zip} \
    s3://${bucket}/${service}/Repo/${zip}

echo " "
echo "*************************************************************"
echo "*      Executing create stack command for the pipeline      *"
echo "*************************************************************"
echo " "

aws cloudformation create-stack \
    --stack-name ${service}Pipeline${environment} \
    --template-body file://${workspacePath}/pipeline/pipeline.yaml \
    --parameters ParameterKey=BucketName,ParameterValue=${bucket} \
                 ParameterKey=ZipFile,ParameterValue=${zip} \
                 ParameterKey=ServiceName,ParameterValue=${service} \
                 ParameterKey=ServiceNameLower,ParameterValue=${serviceLower} \
                 ParameterKey=EnvironmentName,ParameterValue=${environment} \
                 ParameterKey=EnvironmentNameLower,ParameterValue=${environmentLower} \
                 ParameterKey=StageName,ParameterValue=${stage}  \
                 ParameterKey=BuildFile,ParameterValue=${buildFile} \
                 ParameterKey=TestFile,ParameterValue=${testFile} \
                 ParameterKey=DeployFile,ParameterValue=${deployFile} \
    --capabilities CAPABILITY_IAM CAPABILITY_NAMED_IAM CAPABILITY_AUTO_EXPAND

echo " "
