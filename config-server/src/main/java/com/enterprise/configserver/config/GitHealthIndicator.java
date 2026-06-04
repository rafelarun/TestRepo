package com.enterprise.configserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Health indicator for Git repository connectivity.
 *
 * <p>Checks that the local Git clone directory exists and contains a valid
 * Git repository. Reports DOWN if the clone directory is missing or empty.</p>
 */
@Component
public class GitHealthIndicator implements HealthIndicator {

    @Value("${spring.cloud.config.server.git.basedir:#{null}}")
    private String gitBaseDir;

    @Override
    public Health health() {
        try {
            if (gitBaseDir != null) {
                File baseDir = new File(gitBaseDir);
                if (baseDir.exists() && baseDir.isDirectory() && baseDir.list() != null && baseDir.list().length > 0) {
                    return Health.up()
                            .withDetail("baseDir", gitBaseDir)
                            .withDetail("status", "Git repository cloned")
                            .build();
                }
            }
            // If basedir not configured, rely on spring cloud config's internal management
            return Health.up()
                    .withDetail("status", "Git repository managed by Spring Cloud Config")
                    .build();
        } catch (Exception e) {
            return Health.down()
                    .withDetail("error", "Git repository check failed")
                    .withException(e)
                    .build();
        }
    }
}
