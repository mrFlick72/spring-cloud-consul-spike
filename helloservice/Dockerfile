FROM openjdk:11

ADD target/hello-service.jar /usr/local/hello-service/

WORKDIR /usr/local/hello-service/

EXPOSE 8080

CMD ["java", "-jar", "hello-service.jar"]