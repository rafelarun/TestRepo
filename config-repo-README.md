# Configuration Repository

This Git repository contains centralized configuration for all Spring Boot applications managed by the Spring Cloud Config Server.

## Structure

```
application.yml              # Global defaults (all apps, all profiles)
application-dev.yml          # Global development profile
application-qa.yml           # Global QA profile
application-prod.yml         # Global production profile
sample-client.yml            # sample-client specific defaults
sample-client-dev.yml        # sample-client development profile
sample-client-qa.yml         # sample-client QA profile
sample-client-prod.yml       # sample-client production profile
```

## Property Resolution Order (highest priority first)

1. `{application}-{profile}.yml` (most specific)
2. `{application}.yml` (application defaults)
3. `application-{profile}.yml` (global profile)
4. `application.yml` (global defaults)

## Encrypted Properties

To encrypt a property value:
```bash
curl -u admin:admin123 -X POST https://config-server:8888/encrypt -d "my-secret-value"
```

Store the result with `{cipher}` prefix:
```yaml
db.password: '{cipher}AQA...<encrypted-value>'
```

## Adding a New Application

1. Create `{app-name}.yml` with default properties
2. Create `{app-name}-{profile}.yml` for each profile
3. Push to this repository
4. Webhook triggers automatic refresh for the new app

## Webhook

GitHub webhook configured to POST to Config Server `/monitor` endpoint on push events.
This triggers automatic refresh of affected client applications.
