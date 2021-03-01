package it.valeriovaudi.consulspike.helloserviceclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class HelloserviceClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloserviceClientApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}

@RestController
class HelloEndPoint {

    private final String uri;
    private final RestTemplate restTemplate;

    HelloEndPoint(@Value("${hello-service.uri}") String uri,
                  RestTemplate restTemplate) {
        this.uri = uri;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/say-hello-to/{name}")
    public String hello(@PathVariable String name) {
        return restTemplate.getForObject(uri + name, String.class);
    }
}