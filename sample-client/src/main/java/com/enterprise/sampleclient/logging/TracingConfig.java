package com.enterprise.sampleclient.logging;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Tracing configuration for Micrometer + Zipkin integration.
 *
 * <p>Enables distributed tracing with W3C TraceContext propagation.
 * Trace spans are exported to Zipkin via the OpenTelemetry Zipkin exporter.</p>
 */
@Configuration
public class TracingConfig {

    @Bean
    public ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
        return new ObservedAspect(observationRegistry);
    }
}
