package com.enterprise.configserver.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnStateTransitionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Resilience4j configuration for circuit-breaker and retry patterns.
 *
 * <p>Circuit-breaker instances and their parameters are defined in application.yml.
 * This class registers event listeners for operational visibility.</p>
 */
@Configuration
public class ResilienceConfig {

    private static final Logger log = LoggerFactory.getLogger(ResilienceConfig.class);

    /**
     * Configures circuit-breaker event listeners for Kafka producer.
     * Logs state transitions (CLOSED → OPEN → HALF_OPEN) for operational monitoring.
     */
    @Bean
    public CircuitBreaker kafkaCircuitBreaker(CircuitBreakerRegistry registry) {
        CircuitBreaker circuitBreaker = registry.circuitBreaker("kafkaProducer");

        circuitBreaker.getEventPublisher()
                .onStateTransition(this::logStateTransition);

        return circuitBreaker;
    }

    private void logStateTransition(CircuitBreakerOnStateTransitionEvent event) {
        log.warn("Circuit breaker '{}' state transition: {} -> {}",
                event.getCircuitBreakerName(),
                event.getStateTransition().getFromState(),
                event.getStateTransition().getToState());
    }
}
