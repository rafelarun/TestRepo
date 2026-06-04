package com.enterprise.configserver.security;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Rate limiting filter for sensitive endpoints.
 *
 * <p>Applies Resilience4j token-bucket rate limiting to /encrypt, /decrypt,
 * and /monitor endpoints. Returns HTTP 429 with Retry-After header when
 * rate limit is exceeded.</p>
 */
@Component
public class RateLimitingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(RateLimitingFilter.class);

    private final RateLimiter encryptRateLimiter;
    private final RateLimiter decryptRateLimiter;
    private final RateLimiter monitorRateLimiter;

    public RateLimitingFilter(RateLimiterRegistry rateLimiterRegistry) {
        this.encryptRateLimiter = rateLimiterRegistry.rateLimiter("encryptEndpoint");
        this.decryptRateLimiter = rateLimiterRegistry.rateLimiter("decryptEndpoint");
        this.monitorRateLimiter = rateLimiterRegistry.rateLimiter("monitorEndpoint");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        RateLimiter applicableLimiter = getApplicableRateLimiter(path);

        if (applicableLimiter != null) {
            boolean permitted = applicableLimiter.acquirePermission();
            if (!permitted) {
                log.warn("Rate limit exceeded for endpoint: {} from IP: {}", path, request.getRemoteAddr());
                response.setStatus(HttpServletResponse.SC_TOO_MANY_REQUESTS);
                response.setHeader("Retry-After", "1");
                response.setContentType("application/json");
                response.getWriter().write("{\"error\":\"Too Many Requests\",\"message\":\"Rate limit exceeded. Please retry after 1 second.\"}");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private RateLimiter getApplicableRateLimiter(String path) {
        if (path.startsWith("/encrypt")) {
            return encryptRateLimiter;
        } else if (path.startsWith("/decrypt")) {
            return decryptRateLimiter;
        } else if (path.startsWith("/monitor")) {
            return monitorRateLimiter;
        }
        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return !path.startsWith("/encrypt")
                && !path.startsWith("/decrypt")
                && !path.startsWith("/monitor");
    }
}
