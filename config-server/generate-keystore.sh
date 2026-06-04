#!/bin/bash
# Generate a self-signed PKCS12 keystore for development/testing TLS
# For production, use a CA-signed certificate

KEYSTORE_FILE="src/main/resources/keystore.p12"
KEYSTORE_PASSWORD="${KEYSTORE_PASSWORD:-changeit}"
KEY_ALIAS="config-server"
VALIDITY_DAYS=365
CN="localhost"

echo "Generating self-signed PKCS12 keystore for Config Server..."
echo "  Output: ${KEYSTORE_FILE}"
echo "  Alias: ${KEY_ALIAS}"
echo "  CN: ${CN}"
echo "  Validity: ${VALIDITY_DAYS} days"
echo ""

keytool -genkeypair \
  -alias "${KEY_ALIAS}" \
  -keyalg RSA \
  -keysize 2048 \
  -storetype PKCS12 \
  -keystore "${KEYSTORE_FILE}" \
  -storepass "${KEYSTORE_PASSWORD}" \
  -validity ${VALIDITY_DAYS} \
  -dname "CN=${CN}, OU=Engineering, O=Enterprise, L=City, ST=State, C=US" \
  -ext "SAN=dns:localhost,ip:127.0.0.1"

if [ $? -eq 0 ]; then
  echo ""
  echo "Keystore generated successfully: ${KEYSTORE_FILE}"
  echo "Set KEYSTORE_PASSWORD environment variable to match (default: changeit)"
else
  echo ""
  echo "ERROR: Keystore generation failed. Ensure 'keytool' is in your PATH (comes with JDK)."
  exit 1
fi
