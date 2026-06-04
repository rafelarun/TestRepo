package com.enterprise.configserver.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration for the Config Server.
 *
 * <p>Configures Basic Authentication with role-based endpoint protection.
 * The /monitor endpoint is excluded from auth (validated by webhook secret instead).</p>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${app.security.admin.username}")
    private String adminUsername;

    @Value("${app.security.admin.password}")
    private String adminPassword;

    /**
     * Security filter chain configuration.
     * <ul>
     *   <li>/monitor - permitted without auth (webhook secret validated separately)</li>
     *   <li>/encrypt, /decrypt - requires ADMIN role</li>
     *   <li>/actuator/** - requires ADMIN role</li>
     *   <li>All other endpoints - requires USER or ADMIN role</li>
     * </ul>
     */
    @Bean
    @Order(1)
    public SecurityFilterChain monitorFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/monitor/**")
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/encrypt/**", "/decrypt/**").hasRole("ADMIN")
                .requestMatchers("/actuator/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    /**
     * In-memory user details service with admin user.
     * In production, externalize credentials via environment variables.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        var admin = User.builder()
                .username(adminUsername)
                .password(passwordEncoder().encode(adminPassword))
                .roles("ADMIN", "USER")
                .build();
        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
