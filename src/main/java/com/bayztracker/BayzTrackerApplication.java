package com.bayztracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableScheduling
@EnableSwagger2
@SpringBootApplication
public class BayzTrackerApplication {
    public static void main(String[] args) {
        SpringApplication.run(BayzTrackerApplication.class, args);
    }
}
