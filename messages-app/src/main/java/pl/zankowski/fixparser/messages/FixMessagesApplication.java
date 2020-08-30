package pl.zankowski.fixparser.messages;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class FixMessagesApplication {

    public static void main(String[] args) {
        SpringApplication.run(FixMessagesApplication.class, args);
    }
}
