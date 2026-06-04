package com.enterprise.sampleclient.service;

import com.enterprise.sampleclient.config.AppConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service layer for configuration property access.
 *
 * <p>Retrieves refreshable configuration values from the {@link AppConfigProperties}
 * bean. When a refresh event is processed, subsequent calls return updated values.</p>
 */
@Service
public class ConfigService {

    private static final Logger log = LoggerFactory.getLogger(ConfigService.class);

    private final AppConfigProperties configProperties;

    public ConfigService(AppConfigProperties configProperties) {
        this.configProperties = configProperties;
    }

    /**
     * Returns the current message property value from Config Server.
     *
     * @return current message string
     */
    public String getMessage() {
        String message = configProperties.getMessage();
        log.debug("Returning config message: {}", message);
        return message;
    }
}
