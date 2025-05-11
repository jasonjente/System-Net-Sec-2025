# ✅ Fair Digital Dice – Implementation Checklist

This checklist breaks down all implementation tasks based on the official assignment requirements.

---

## 🔐 1. Cryptographic Protocol – **40%**

> Prevent dishonest behavior and ensure fair play in the dice game.

- [ ] Implement dice roll generation on both **server** and **client**
- [ ] Use a **commitment scheme**:
  - [ ] Server generates random dice value and salt
  - [ ] Server commits to its roll using: `commitment = hash(roll + salt)`
  - [ ] Client sends guess
  - [ ] Server reveals roll and salt
  - [ ] Client verifies: `hash(roll + salt) == commitment`
- [ ] Determine outcome:
  - [ ] Client wins if guess == server roll
- [ ] Display outcome (e.g., console or frontend interface)
- [ ] Document protocol with diagram in report

---

## 🔒 2. Server Authentication & HTTPS – **30%**

> Secure communications using OpenSSL and HTTPS.

- [x] Generate self-signed **Certificate Authority (CA)**
- [x] Generate and sign **server SSL certificate** using the CA
- [x] Configure Spring Boot HTTPS:
  - [x] `server.ssl.key-store`
  - [x] `server.ssl.key-store-password`
  - [x] `server.ssl.key-alias`
- [x] Redirect all HTTP requests to HTTPS
- [x] Validate SSL certificate chain (e.g., via browser or curl)

---

## 👤 3. User Registration & Login – **30%**

> Create a secure user registration and authentication system.

### ✅ Web Layer

- [x] `UserRegisterRequestDTO`, `LoginRequestDTO`, `JwtResponse` DTOs
- [x] MapStruct mappers to domain (`User`, `LoginRequest`)

### ✅ Application Layer

- [x] `UserCommandService` for registration
- [x] `UserQueryService` for login
- [x] Password encoding with Spring Security `PasswordEncoder`

### ✅ Persistence Layer

- [x] `UserPersistencePort` interface
- [x] `UserRepository` with JPA
- [x] `UserEntity`, `UserEntityMapper`

### ✅ Database

- [x] Create PostgreSQL DB: `GDPR`
- [x] Create `users` table with:
  - [x] `id`, `first_name`, `last_name`, `username`, `password`
  - [x] Define `id` or `username` as **primary key**
- [x] Insert two users:
  - [x] One with your student ID as username
  - [x] One with username `admin`
- [x] Use **BCrypt** to encode passwords

---

## ✅ 4. JWT Authentication (**Optional +15%**)

> Stateless token-based authentication.

- [x] Implement `JwtService` to issue and verify tokens
- [x] Add `/login` endpoint to return JWT
- [ ] Create JWT filter for authorization
- [ ] Configure Spring Security to allow stateless JWT auth
- [ ] Ensure token expiration is configurable

---

## 🔐 5. SQL Injection Protection (**Optional +15%**)

> Prevent SQL injection attacks.

- [x] Use Spring Data JPA repositories only
- [x] Avoid unsafe native queries
- [x] Validate and sanitize any dynamic inputs
- [ ] Mention mitigation techniques in report

---

## 📄 6. Deliverables

- [ ] Report (max 20 pages):
  - [ ] Security protocol overview
  - [ ] Flow diagram of protocol
  - [ ] Authentication mechanism design
  - [ ] Code description and decisions
- [ ] Final project archive (`.zip` or `.tar`) with README/instructions
- [ ] Demonstration of functionality (CLI or Web UI)
---

## 🌐 7. Frontend (Web UI)

> Provide a minimal user interface for interaction and demonstration.

- [ ] Create registration form (first name, last name, username, password)
- [ ] Create login form (username, password)
- [ ] Display login success and show JWT/token
- [ ] Allow user to trigger dice roll and guess opponent’s roll
- [ ] Display local roll, guess, and final outcome (win/loss)
- [ ] Integrate with backend via REST API using `fetch`/`axios`
- [ ] Handle token storage securely (e.g., `localStorage` or in-memory)
- [ ] Provide basic UI feedback (errors, success messages)
- [ ] Use any frontend framework or vanilla HTML/CSS/JS
