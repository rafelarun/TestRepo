package com.enterprise.sampleclient.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * Refreshable configuration properties bean.
 *
 * <p>Annotated with {@code @RefreshScope} so that when a Spring Cloud Bus
 * refresh event is received, this bean is destroyed and recreated with
 * the latest configuration values from Config Server.</p>
 */
@Component
@RefreshScope
public class AppConfigProperties {

    @Value("${message:Default Message}")
    private String message;

    /**
     * Returns the current message property value.
     * This value automatically updates when configuration is refreshed.
     *
     * @return current message from Config Server
     */
    public String getMessage() {
        return message;
    }
}
