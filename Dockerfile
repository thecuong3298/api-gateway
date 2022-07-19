FROM openjdk:11-jdk
COPY ./target/api-gateway-0.0.1.jar api-gateway.jar
ENTRYPOINT ["java","-jar","-Djava.security.egd=file:./config/application.yml","api-gateway.jar"]
