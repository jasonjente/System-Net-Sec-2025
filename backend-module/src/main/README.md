# 🎲 Fair Dice Game — Clean Architecture with Hexagonal Structure and CQRS

This project implements a secure online dice game using **Hexagonal Architecture**, **Clean Architecture**, and **CQRS (Command Query Responsibility Segregation)** for clean modularity and testability. It is designed for separation of concerns, easy testing, and future scalability.

---

## 🧱 Architectural Overview

**Flow:**

Web DTOs ⇄ Mappers ⇄ Domain Models ⇄ Entities ⇄ Database
Controllers ⇄ Services ⇄ Ports ⇄ Adapters


### Layers

- **Domain Layer**  
  Core business logic (e.g. `User`, `LoginRequest`) with no external dependencies.

- **Ports (Interfaces)**
    - **Primary Ports**: Define application-level use cases (`UserCommandPort`, `UserQueryPort`)
    - **Secondary Ports**: Define infrastructure interactions (`UserPersistencePort`)

- **Adapters (Implementations)**
    - **Primary Adapters**: Input controllers (`AuthController`)
    - **Secondary Adapters**: Output implementations for DB (`UserRepository`, `UserPersistenceService`)

- **Application Layer**
    - `UserCommandService`: Handles writes (e.g. registration)
    - `UserQueryService`: Handles reads (e.g. login, queries)

- **Web Layer (DTOs and Mappers)**  
  DTOs are mapped to domain models using MapStruct.

- **Security Layer**  
  Stateless JWT-based authentication via `JwtService`.

- **Configuration Layer**
    - `SecurityConfig`: Disables gameResult auth, enables JWT setup
    - `WebConfig`: Registers interceptors
    - `AuditLoggingInterceptor`: Logs every API call
    - `GlobalExceptionHandler`: Formats validation and generic errors

---

## 📁 Project Structure
```
backend-module/
├── src/
│ ├── main/
│ │ ├── java/org/aueb/fair/dice/
│ │ │ ├── adapter/
│ │ │ │ ├── primary/web/controller/
│ │ │ │ │ └── AuthenticationController
│ │ │ │ └── secondary/persistence/
│ │ │ │ ├── entity/UserEntity
│ │ │ │ ├── mapper/UserEntityMapper
│ │ │ │ ├── repository/UserRepository
│ │ │ │ └── service/UserPersistenceService
│ │ │ ├── application/service/user/
│ │ │ │ ├── UserCommandService
│ │ │ │ └── UserQueryService
│ │ │ ├── config/
│ │ │ │ ├── AuditLoggingInterceptor
│ │ │ │ ├── GlobalExceptionHandler
│ │ │ │ ├── SecurityConfig
│ │ │ │ └── WebConfig
│ │ │ ├── domain/user/
│ │ │ │ ├── LoginRequest
│ │ │ │ └── User
│ │ │ ├── port/
│ │ │ │ ├── primary/user/
│ │ │ │ │ ├── UserCommandPort
│ │ │ │ │ └── UserQueryPort
│ │ │ │ └── secondary/user/
│ │ │ │ └── UserPersistencePort
│ │ │ ├── security/JwtService
│ │ │ ├── web/dto/
│ │ │ │ ├── JwtResponse
│ │ │ │ ├── LoginRequestDTO
│ │ │ │ └── UserRegisterRequestDTO
│ │ │ └── web/mapper/
│ │ │ ├── LoginRequestDTOMapper
│ │ │ └── UserRegistrationRequestDTOMapper
│ │ └── resources/
│ │ ├── application.yml
│ │ ├── import.sql
│ │ ├── README.md
│ │ └── progress.md
│ └── test/
│ └── unit tests
```
---

## 🧪 Running the App

1. Ensure PostgreSQL is running (or use `docker-compose`)
2. Start the backend:

```bash
# add this for the integration testing
echo "testcontainers.reuse.enable=true" >> ~/.testcontainers.properties
```
```bash
mvn clean compile
mvn spring-boot:run
```

```bash
curl -X POST http://localhost:8080/api/auth/login \
-H "Content-Type: application/json" \
-d '{"username": "ichatzop", "password": "mysecurepass"}'
```

🔐 Authentication Flow
- POST /api/auth/register: Register new users
- POST /api/auth/login: Authenticate and receive JWT
- Protected endpoints will soon require: Authorization: Bearer <token>
- Follow the structure shown above
  - Never pass Entities to controllers — always use DTOs
  - Use MapStruct to map between DTO ↔ Domain ↔ Entity
  - Respect CQRS:
    - Use CommandService for changes (e.g. register)
    - Use QueryService for reads (e.g. login)

