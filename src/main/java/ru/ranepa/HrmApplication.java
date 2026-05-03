package ru.ranepa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HrmApplication {

    private static final Logger log = LoggerFactory.getLogger(HrmApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(HrmApplication.class, args);
    }

    @Bean
    CommandLineRunner printConsoleUrl() {
        return args -> {
            log.info(" H2 Database Console available at:");
            log.info("http://localhost:8080/api/employees");
            log.info("Swagger UI: http://localhost:8080/swagger-ui.html");
        };
    }
}