FROM maven:3.8.6-openjdk-11 as build

ENV HOME=/opt/src/app
WORKDIR $HOME
ADD pom.xml $HOME
RUN ["/usr/local/bin/mvn-entrypoint.sh", "mvn", "verify", "clean", "--fail-never"]
COPY . .
RUN ["mvn", "package"]

FROM openjdk:11-jdk

ENV HOME=/opt/src/app
WORKDIR $HOME
COPY --from=build opt/src/app/target/*.jar app.jar
