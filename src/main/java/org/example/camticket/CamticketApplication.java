package org.example.camticket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CamticketApplication {

    public static void main(String[] args) {
        SpringApplication.run(CamticketApplication.class, args);
    }

}
