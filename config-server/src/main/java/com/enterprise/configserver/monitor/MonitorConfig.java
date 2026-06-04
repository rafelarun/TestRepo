package com.enterprise.configserver.monitor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.config.monitor.GithubPropertyPathNotificationExtractor;
import org.springframework.cloud.config.monitor.PropertyPathNotificationExtractor;

/**
 * Configuration for the /monitor webhook endpoint.
 *
 * <p>The Spring Cloud Config Monitor automatically provides the /monitor endpoint.
 * This configuration sets up GitHub-specific webhook payload parsing for selective
 * notification of affected applications.</p>
 *
 * <p>Webhook secret validation is handled via the spring.cloud.config.server.monitor
 * configuration in application.yml.</p>
 */
@Configuration
public class MonitorConfig {

    @Value("${app.webhook.secret}")
    private String webhookSecret;

    /**
     * GitHub webhook payload extractor.
     * Parses the push event payload to identify changed configuration files
     * and determine which applications should be notified.
     */
    @Bean
    public PropertyPathNotificationExtractor githubNotificationExtractor() {
        return new GithubPropertyPathNotificationExtractor();
    }

    public String getWebhookSecret() {
        return webhookSecret;
    }
}
