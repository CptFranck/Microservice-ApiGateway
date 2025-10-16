# 🧭 Microservice - API Gateway

The **API Gateway** is a core component of the microservice architecture.  
It serves as the **single entry point** for all external clients, routes requests to backend services (Booking, Inventory, Order), handles **OAuth2 authentication** via **Keycloak**, ensures **resilience** using **Resilience4j**, and exposes **REST API documentation** with **OpenAPI**.

---

## 🚀 Overview

The API Gateway acts as a **reverse proxy** that:  
- Routes client requests to backend microservices.  
- Secures endpoints with **JWT tokens** validated by **Keycloak**.  
- Provides fault tolerance through **circuit breakers**.  
- Centralizes API documentation via **SpringDoc / OpenAPI**.  
- Monitors system health using **Spring Boot Actuator**.

---

## ⚙️ Key Features

- **Spring Boot 3.5.6** – Core application framework  
- **Spring Cloud Gateway (WebMVC)** – Routing and request filtering  
- **Keycloak** – OAuth2 authentication and JWT validation  
- **Resilience4j** – Circuit breaker and retry mechanisms  
- **Spring Boot Actuator** – Monitoring and health checks  
- **SpringDoc OpenAPI** – Interactive API documentation (Swagger UI)  
- **Java 21** – Runtime language  

---

## 🧩 Architecture Integration

![Architecture du projet](docs/schemaProject.png)

The API Gateway is part of a **5-repository microservice ecosystem**:

1. **Common** – Shared DTOs and utilities used across services.  
2. **Booking Service** – Handles booking operations and emits events.  
3. **Inventory Service** – Maintains venue and event stock, communicates via Kafka.  
4. **Order Service** – Manages customer orders and coordinates with booking and inventory services.  
5. **API Gateway** – Central entry point for all external clients, handles routing, authentication, and unified API documentation.

The gateway routes requests to backend services, enforces security policies, and provides a single, centralized access point for the system.
