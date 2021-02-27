package it.valeriovaudi.consulspike.helloservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class HelloserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloserviceApplication.class, args);
    }

}

@RefreshScope
@RestController
class HelloEndPoint {

    private final boolean uppercase;

    HelloEndPoint(@Value("${uppercase:false}") boolean uppercase) {
        this.uppercase = uppercase;
    }

    @GetMapping("/hello/{name}")
    public String hello(@PathVariable String name) {
        return String.format("hello %s", uppercase ? name.toUpperCase() : name);
    }
}