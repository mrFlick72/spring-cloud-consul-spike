FROM openjdk:11

ADD target/hello-service-client.jar /usr/local/hello-service-client/

WORKDIR /usr/local/hello-service-client/

EXPOSE 8080

CMD ["java", "-jar", "hello-service-client.jar"]