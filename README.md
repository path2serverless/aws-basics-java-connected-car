# ConnectedCar Example Solution

This repository contains the ConnectedCar example solution, which is an educational resource that accompanies an upcoming online course about AWS serverless basics. This solution is for an imaginary car OEM that seeks to connect cars and registered owners for dealer service. The solution demonstrates AWS native serverless services such as API Gateway, Lambda, DynamoDB, Cognito, and containerization using Fargate. This version is implemented in Java, but note that there's a sister repository with a functionally equivalent solution implemented in .Net Core.

## Solution Overview

There are three APIs in this solution to serve this purpose:

API | Description | Authentication
--- | --- | ---
Admin API | For staff to maintain dealers, timeslots, customers, vehicles, and registrations | API keys
Vehicle API | For submission of service-related vehicle events | Custom authorizer
Customer API | For registered owners to maintain their profiles, search for dealers, and managed service appointments | Cognito

Note that the Postman collections described below are a good source for details about the endpoints and JSON inputs for these APIs.

The solution also supports three different serverless deployment types:

Deployment | Description
--- | ---
SAM | Serverless-application-model deployment with API Gateway backed by Lambdas
OpenApi | As above, but with APIs defined in OpenApi documents with schemas
Containers | As above, but with the APIs backed by Fargate-provisioned containers

The solution data model, implemented using AWS DynamoDB, looks like this with tables on the left and global secondary indexes (shaded) on the right:

<img src="https://imagedelivery.net/l6V-00C4u79oAkXwH_ULEg/14f9f710-f1ba-45cd-00bf-f279950cac00/standard" width="800px" height="900px">

## Serverless Topics

The solution demonstrates a range of native serverless techniques. See the upcoming online course for a deep dive into these topics, but here is an outline:

CloudFormation

*	Nested stacks
*	Parameterized resource naming
*	Conditions

CloudWatch

*	Configuration for X-Ray and Lambda/Container insights
*	Scripts for command-line access to logs and metrics

API Gateway

*	OpenApi for defining endpoints and schemas
*	API keys, custom authorizers and Cognito for authentication

Lambda

*	Dependency injection and service components to minimize lock-in
*	Least-privilege custom execution roles

Fargate

*	JAX-RS resources and dependency injection for service components
*	Multi-stage Dockerfiles
*	Secure networking with VPC link and VPC endpoints
*	CloudWatch alarms and Auto-Scaling

DynamoDB

*	High-level interface with data binding
*	Various queries and scans with different consistency levels
*	Batch operations for seeding test data

CodePipeline

*	CloudFormation-defined pipeline
*	Buildspec files for CodeProject stages
*	API test automation with Newman

## Repository Contents

### [.vscode](https://github.com/path2serverless/aws-basics-java-connected-car/tree/main/.vscode)

Task and launch files to assist development with VS Code

### [buildspec](https://github.com/path2serverless/aws-basics-java-connected-car/tree/main/buildspec)

AWS CodeProject buildspec files for each of the three deployment architectures, plus an additional buildspec file for automated API testing. These files are used by the pipeline that's described below.

### [cloudwatch](https://github.com/path2serverless/aws-basics-java-connected-car/tree/main/cloudwatch)

Example scripts for querying cloudwatch logs and metrics.

### [deployment](https://github.com/path2serverless/aws-basics-java-connected-car/tree/main/deployment)

Parent for the three sets of deployment folders, each containing manual deployment scripts for zsh and powershell, as well as CloudFormation templates and where applicable, OpenApi documents. Individual deployment folders are listed below.

### [deployment/containers](https://github.com/path2serverless/aws-basics-java-connected-car/tree/main/deployment/containers)

Contains the deployment files for the Fargate-provisioned containerized version of the solution.

### [deployment/openapi](https://github.com/path2serverless/aws-basics-java-connected-car/tree/main/deployment/openapi)

Contains the deployment files for the OpenApi-defined version of the solution.

### [deployment/sam](https://github.com/path2serverless/aws-basics-java-connected-car/tree/main/deployment/sam)

Contains the deployment files for the basic serverless-application-model version of the solution.

### [main](https://github.com/path2serverless/aws-basics-java-connected-car/tree/main/main)

The parent folder for the java source code which is a multi-module maven project. Details about each of the modules follows below.

### [main/apis](https://github.com/path2serverless/aws-basics-java-connected-car/tree/main/main/apis)

Contains the JAX-RS APIs and Grizzly HTTP server for the containerized version of the solution.

### [main/authorizers](https://github.com/path2serverless/aws-basics-java-connected-car/tree/main/main/authorizers)

Contains the Lambda that implements the custom VIN/Pin Number authorizer.

### [main/events](https://github.com/path2serverless/aws-basics-java-connected-car/tree/main/main/events)

Contains the Lambda that's triggered by the SQS endpoint used for creating Cognito users in the containerized version of the solution.

### [main/functions](https://github.com/path2serverless/aws-basics-java-connected-car/tree/main/main/functions)

Contains the Lambdas that back the APIs for the SAM and OpenApi versions of the solution.

### [main/services](https://github.com/path2serverless/aws-basics-java-connected-car/tree/main/main/services)

Contains the implementations for the service components that are invoked by the Lambdas and JAX-RS API resources.

### [main/shared](https://github.com/path2serverless/aws-basics-java-connected-car/tree/main/main/shared)

Contains the classes for the API inputs/outputs and the service interfaces for the solution.

### [main/test](https://github.com/path2serverless/aws-basics-java-connected-car/tree/main/main/test)

Contains example component tests for the Lambdas and JAX-RS resources.

### [main/tools](https://github.com/path2serverless/aws-basics-java-connected-car/tree/main/main/tools)

Contains command-line tools that can be used to seed performance-testing data in the database.

### [pipeline](https://github.com/path2serverless/aws-basics-java-connected-car/tree/main/pipeline)

Contains scripts (zsh and powershell versions) and CloudFormation template that defines a CodePipeline process for the solution.

### [postman](https://github.com/path2serverless/aws-basics-java-connected-car/tree/main/postman)

Contains Postman test collections for each of the three APIs and a global variables definition file.

## Setup Checklist

These are the required AWS account setup steps:

*	Create a private S3 bucket to which the solution will copy templates and Lambda packages
*	Create a "connectedcar-dev" container registry for the Fargate version of the solution
*	Configure API Gateway with a CloudWatch service role
*	Download access keys for your workstation

These are the required workstation setup steps:

*	Install the AWS CLI on Macs, or the AWS Tools Powershell Module on Windows
*	Configure the credentials and default region
*	Install the following tools:
    *	Java 11 JDK (or later)
    *	VS Code plus Java development extensions
    *	Docker desktop
    *	Postman
    *	Node.js
    *	Newman
    *	Maven
    *	Git

Lastly, in the deployment scripts folders, apply these settings to the config.sh or config.ps1 scripts:

*	Set the workspacePath if different to the location of the cloned repository
*	Set the bucket name
* Set the region code (for the containers config.ps1 script only)

## Deployment Options

As noted, you can deploy the solution with one of three architectures. For any of these you have the option to manually run the scripts in one of the three deployment folders in this order:

Script | Description
--- | ---
clean | Removes previously copied artifacts from the S3 deployment bucket
build | Builds and copies Lambda packages to the deployment bucket, and for the Fargate version, builds a container image and pushes it to the registry
deploy | Copies the CloudFormation templates and OpenApi documents to the deployment bucket and executes the CreateStack or UpdateStack commands to deploy the solution in AWS

Alternatively, you can run one of the two pipeline scripts to deploy the pre-defined CodePipeline process. Once this pipeline is deployed, it will automatically start executing and will build, deploy, and test the solution. The two pipeline scripts are parameterized, so include the deployment type as a parameter using one of these three values:  "sam", "openapi", "containers".

## Postman Collections

As noted above, there are Postman collections that can perform basic tests of the three APIs. Simply import these collections into a Postman Workspace along with the global variables file. Once the solution is deployed, testing should then follow this sequence:

Step 1. Admin API Testing

From the outputs of the deployment script used, or from running one of the query-stack scripts in the cloudwatch folder, you can obtain the Admin API ID and the API key. These need to be applied to the adminApi and apiKey global variables. Once applied, you can run the tests for Admin API collection, which will populate test data for a vehicle and a customer.

Step 2. Vehicle API Testing

Once the Admin API tests have executed, there will be updated vehicle VIN values in the global variables that will enable the Vehicle API tests to also be executed.

Step 3. Customer API Testing

Once the Admin API tests have executed, there will also be an updated customer username value in the global variables. To test the Customer API, you will need to configure Postman to obtain an OAuth2 access token from Cognito (via the AWS console). Once configured, you can use the username in the global variables and the password in the Admin API Create Dealer test, and authenticate to obtain the access token. Copy this token to the token global variable, and the Customer API collection can be executed.

Alternatively, you can use the AWS command-line to obtain the access token. See the test.buildspec file in the /buildspec folder for an example. Note that this technique will only work when the solution is deployed as a "Dev" environment because in that case no Cognito client secret is generated.

