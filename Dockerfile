FROM openjdk:11-jdk
COPY ./target/api-gateway-0.0.1.jar api-gateway.jar
ENTRYPOINT ["java","-jar","api-gateway.jar"]
