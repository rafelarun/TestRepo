package com.enterprise.sampleclient.controller;

import com.enterprise.sampleclient.service.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * REST controller exposing configuration properties.
 *
 * <p>Demonstrates dynamic configuration refresh. The /api/config/message endpoint
 * returns the current value of the 'message' property, which updates automatically
 * when Spring Cloud Bus delivers a refresh event.</p>
 */
@RestController
@RequestMapping("/api/config")
public class ConfigController {

    private static final Logger log = LoggerFactory.getLogger(ConfigController.class);

    private final ConfigService configService;

    public ConfigController(ConfigService configService) {
        this.configService = configService;
    }

    /**
     * Returns the current message property value.
     *
     * <p>Response format: {@code {"message": "<current value>"}}</p>
     *
     * @return JSON response with current message
     */
    @GetMapping("/message")
    public ResponseEntity<Map<String, String>> getMessage() {
        String message = configService.getMessage();
        log.info("GET /api/config/message - returning: {}", message);
        return ResponseEntity.ok(Map.of("message", message));
    }
}
