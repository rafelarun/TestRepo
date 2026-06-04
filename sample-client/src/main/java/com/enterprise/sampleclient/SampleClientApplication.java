package com.enterprise.sampleclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Sample Client Application demonstrating Spring Cloud Config auto-refresh.
 *
 * <p>Fetches configuration from Spring Cloud Config Server at startup and
 * automatically refreshes properties via Spring Cloud Bus events from Kafka.</p>
 */
@SpringBootApplication
public class SampleClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleClientApplication.class, args);
    }
}
