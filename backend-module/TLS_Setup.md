# 🔐 Enabling HTTPS for Fair Dice Game Backend

This guide sets up HTTPS using a self-signed Certificate Authority (CA) and server certificate in your Spring Boot backend. It ensures encrypted communication, even during local development or testing.

---

## 1. Generate SSL Certificates

### 1.1 Create a Certificate Authority (CA)

```bash
openssl genrsa -out ca.key 2048
openssl req -x509 -new -nodes -key ca.key -sha256 -days 3650 -out ca.crt \
  -subj "/C=GR/ST=Attica/L=Athens/O=FairDice/CN=FairDiceCA"
```
### 1.2 Create a Server Key and CSR

```bash
openssl genrsa -out server.key 2048
openssl req -new -key server.key -out server.csr \
-subj "/C=GR/ST=Attica/L=Athens/O=FairDice/CN=localhost"
```

### 1.3 Sign the Server Certificate with the CA

```bash
openssl x509 -req -in server.csr -CA ca.crt -CAkey ca.key -CAcreateserial \
-out server.crt -days 365 -sha256
```

### 1.4 Package into a PKCS#12 Keystore for Spring Boot

```bash
openssl pkcs12 -export -in server.crt -inkey server.key -out keystore.p12 \
-name fair-dice -CAfile ca.crt -caname root -passout pass:changeit
```

Place keystore.p12 under [src/main/resources](src/main/resources).

---

## 2. Postman testing
To test HTTPS endpoints using a self-signed certificate in Postman, follow these steps.
✅ Files Needed
```text
    - keystore.p12 – PKCS12 file used by your Spring Boot app

    - fair-dice.crt – Server certificate (exported from keystore)

    fair-dice.key – Private key (if using mutual TLS – optional)
```
- Step 1: Add the CA Certificate (Optional if you're skipping verification)
  - Open Postman.
  - Go to Settings (top right gear icon).
  - Navigate to the Certificates tab. 
  - Scroll to CA Certificates. 
  - Click Add Certificate Authority: 
    - Host: localhost
    - Port: 8443
    - CRT File: Select your fair-dice.crt 
    - Click Add

- Step 2: Add Client Certificate (if mutual TLS is enabled – optional)
  - If your server requires a client certificate to authenticate the client:
    - In the same Certificates tab, under Client Certificates, click Add Certificate:
        - Host: localhost
        - Port: 8443 
        - CRT file: fair-dice.crt (public cert) 
        - KEY file: fair-dice.key (private key) 
        - Leave passphrase empty unless encrypted 
        - Click Add

- Step 3: Disable Certificate Verification (Optional)
  - If Postman throws warnings due to self-signed cert:
    1. In Settings → General, turn SSL certificate verification → Off
    2. This is useful during dev/testing

- Step 4: Make the HTTPS Request
  - Set the URL to https://localhost:8443/api/auth/login 
  - Use POST with Content-Type: application/json
  - Example body:
  - Click Send
```json
{
  "username": "ichatzop",
  "password": "mysecurepass"
}
```
