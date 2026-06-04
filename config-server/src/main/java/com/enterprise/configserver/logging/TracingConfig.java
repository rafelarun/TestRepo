package com.enterprise.configserver.logging;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Tracing configuration for Micrometer + Zipkin integration.
 *
 * <p>Enables distributed tracing with W3C TraceContext propagation.
 * Trace spans are exported to Zipkin via the OpenTelemetry Zipkin exporter.
 * Tracing endpoint and sampling rate are configured in application.yml.</p>
 */
@Configuration
public class TracingConfig {

    /**
     * Enables @Observed annotation support for custom span creation.
     * Allows methods to be annotated with @Observed for automatic span tracking.
     */
    @Bean
    public ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
        return new ObservedAspect(observationRegistry);
    }
}
