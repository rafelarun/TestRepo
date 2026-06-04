package com.enterprise.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Enterprise Spring Cloud Config Server Application.
 *
 * <p>Serves centralized configuration from a Git repository, supports encrypted properties,
 * webhook-based auto-refresh via Spring Cloud Bus and Kafka, and exposes actuator endpoints
 * for monitoring and operations.</p>
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
