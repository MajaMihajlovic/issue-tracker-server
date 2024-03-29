package com.issuetracker.issuetracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ServletComponentScan
@EnableConfigurationProperties
@EnableScheduling
public class IssuetrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(IssuetrackerApplication.class, args);
    }

}

