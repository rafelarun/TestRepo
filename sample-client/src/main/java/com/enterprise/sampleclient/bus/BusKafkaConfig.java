package com.enterprise.sampleclient.bus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.bus.event.RefreshRemoteApplicationEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * Spring Cloud Bus Kafka configuration for the Sample Client.
 *
 * <p>Configures the client as a bus event consumer. When refresh events
 * arrive via Kafka, Spring Cloud Bus triggers a context refresh, causing
 * {@code @RefreshScope} beans to be recreated with updated configuration.</p>
 */
@Configuration
public class BusKafkaConfig {

    private static final Logger log = LoggerFactory.getLogger(BusKafkaConfig.class);

    /**
     * Listens for refresh events received from the bus.
     * Logs event details for operational visibility and tracing.
     */
    @EventListener
    public void onRefreshRemoteEvent(RefreshRemoteApplicationEvent event) {
        log.info("Bus refresh event received - origin: {}, destination: {}, id: {}",
                event.getOriginService(),
                event.getDestinationService(),
                event.getId());
    }
}
