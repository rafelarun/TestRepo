package com.enterprise.configserver.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Custom metrics configuration for operational monitoring.
 *
 * <p>Registers custom counters and timers for webhook processing,
 * bus event publishing, config retrieval, and Git operations.</p>
 */
@Configuration
public class MetricsConfig {

    @Bean
    public Counter webhookReceivedCounter(MeterRegistry registry) {
        return Counter.builder("config.server.webhook.received")
                .description("Number of webhook notifications received")
                .tag("application", "config-server")
                .register(registry);
    }

    @Bean
    public Counter webhookProcessedCounter(MeterRegistry registry) {
        return Counter.builder("config.server.webhook.processed")
                .description("Number of webhook notifications successfully processed")
                .tag("application", "config-server")
                .register(registry);
    }

    @Bean
    public Counter webhookFailedCounter(MeterRegistry registry) {
        return Counter.builder("config.server.webhook.failed")
                .description("Number of webhook notifications that failed processing")
                .tag("application", "config-server")
                .register(registry);
    }

    @Bean
    public Counter busEventsPublishedCounter(MeterRegistry registry) {
        return Counter.builder("config.server.bus.events.published")
                .description("Number of bus refresh events published to Kafka")
                .tag("application", "config-server")
                .register(registry);
    }

    @Bean
    public Timer configRetrievalTimer(MeterRegistry registry) {
        return Timer.builder("config.server.config.retrieval.duration")
                .description("Duration of configuration retrieval requests")
                .tag("application", "config-server")
                .register(registry);
    }

    @Bean
    public Timer gitPullTimer(MeterRegistry registry) {
        return Timer.builder("config.server.git.pull.duration")
                .description("Duration of Git pull operations")
                .tag("application", "config-server")
                .register(registry);
    }
}
