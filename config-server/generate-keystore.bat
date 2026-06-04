@echo off
REM Generate a self-signed PKCS12 keystore for development/testing TLS
REM For production, use a CA-signed certificate

SET KEYSTORE_FILE=src\main\resources\keystore.p12
SET KEY_ALIAS=config-server
SET VALIDITY_DAYS=365
SET CN=localhost

IF "%KEYSTORE_PASSWORD%"=="" SET KEYSTORE_PASSWORD=changeit

echo Generating self-signed PKCS12 keystore for Config Server...
echo   Output: %KEYSTORE_FILE%
echo   Alias: %KEY_ALIAS%
echo   CN: %CN%
echo   Validity: %VALIDITY_DAYS% days
echo.

keytool -genkeypair ^
  -alias %KEY_ALIAS% ^
  -keyalg RSA ^
  -keysize 2048 ^
  -storetype PKCS12 ^
  -keystore %KEYSTORE_FILE% ^
  -storepass %KEYSTORE_PASSWORD% ^
  -validity %VALIDITY_DAYS% ^
  -dname "CN=%CN%, OU=Engineering, O=Enterprise, L=City, ST=State, C=US" ^
  -ext "SAN=dns:localhost,ip:127.0.0.1"

IF %ERRORLEVEL% EQU 0 (
  echo.
  echo Keystore generated successfully: %KEYSTORE_FILE%
  echo Set KEYSTORE_PASSWORD environment variable to match (default: changeit)
) ELSE (
  echo.
  echo ERROR: Keystore generation failed. Ensure 'keytool' is in your PATH (comes with JDK).
  exit /b 1
)
