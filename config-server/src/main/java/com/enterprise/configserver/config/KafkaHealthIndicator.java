package com.enterprise.configserver.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * Health indicator for Kafka connectivity via circuit-breaker state.
 *
 * <p>Reports health based on the kafkaProducer circuit-breaker state:
 * <ul>
 *   <li>CLOSED → UP (Kafka reachable, normal operation)</li>
 *   <li>HALF_OPEN → UP with warning (recovery in progress)</li>
 *   <li>OPEN → DOWN (Kafka unreachable, circuit open)</li>
 * </ul></p>
 */
@Component
public class KafkaHealthIndicator implements HealthIndicator {

    private final CircuitBreakerRegistry circuitBreakerRegistry;

    public KafkaHealthIndicator(CircuitBreakerRegistry circuitBreakerRegistry) {
        this.circuitBreakerRegistry = circuitBreakerRegistry;
    }

    @Override
    public Health health() {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("kafkaProducer");
        CircuitBreaker.State state = circuitBreaker.getState();

        return switch (state) {
            case CLOSED -> Health.up()
                    .withDetail("circuitBreakerState", "CLOSED")
                    .withDetail("status", "Kafka connectivity normal")
                    .build();
            case HALF_OPEN -> Health.up()
                    .withDetail("circuitBreakerState", "HALF_OPEN")
                    .withDetail("status", "Kafka connectivity recovering")
                    .build();
            case OPEN -> Health.down()
                    .withDetail("circuitBreakerState", "OPEN")
                    .withDetail("status", "Kafka unreachable - circuit breaker open")
                    .withDetail("fallback", "Manual refresh via /actuator/refresh still available")
                    .build();
            default -> Health.unknown()
                    .withDetail("circuitBreakerState", state.name())
                    .build();
        };
    }
}
