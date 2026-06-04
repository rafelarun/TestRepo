package com.enterprise.configserver.bus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.bus.event.RefreshRemoteApplicationEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * Spring Cloud Bus Kafka configuration for the Config Server.
 *
 * <p>Configures the Config Server as a bus event publisher. When webhook notifications
 * trigger a refresh, events are published to the Kafka springCloudBus topic.</p>
 *
 * <p>Kafka connection properties are defined in application.yml under spring.kafka.*</p>
 */
@Configuration
public class BusKafkaConfig {

    private static final Logger log = LoggerFactory.getLogger(BusKafkaConfig.class);

    /**
     * Listens for refresh events published to the bus.
     * Logs event details for operational visibility and tracing.
     */
    @EventListener
    public void onRefreshRemoteEvent(RefreshRemoteApplicationEvent event) {
        log.info("Bus refresh event published - origin: {}, destination: {}, id: {}",
                event.getOriginService(),
                event.getDestinationService(),
                event.getId());
    }
}
