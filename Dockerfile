FROM maven:3.8.3-openjdk-11 AS build
WORKDIR /src
COPY main /src/main/
RUN mvn -q -f /src/main/pom.xml clean package

FROM openjdk:11
RUN adduser \
  --disabled-password \
  --home /app \
  --gecos '' app \
  && chown -R app /app
USER app
WORKDIR /app
COPY --from=build /src/main/apis/target/apis-SNAPSHOT-APIS.jar /app/apis-SNAPSHOT-APIS.jar 
EXPOSE 8080
ENTRYPOINT ["java","-cp","/app/apis-SNAPSHOT-APIS.jar","com.path2serverless.connectedcar.apis.GrizzlyLauncher"]