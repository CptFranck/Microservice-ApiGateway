# 🧭 API Gateway for Microservices Architecture

This project is an **API Gateway** built with **Spring Cloud Gateway (WebMVC)** as part of a microservice-based architecture.  
It routes incoming requests to backend services (Booking and Inventory), handles **OAuth2 authentication** through **Keycloak**, provides **resilience** via **Resilience4j**, and exposes **OpenAPI** documentation for each service.

---

## 🚀 Overview

The **API Gateway** serves as a single entry point to the system.  
It acts as a reverse proxy that:
- Routes client requests to backend microservices.
- Secures endpoints using **JWT tokens** validated by **Keycloak**.
- Provides fault tolerance using **Circuit Breaker patterns**.
- Offers unified **API documentation** using **SpringDoc / OpenAPI**.
- Monitors and manages service health through **Spring Boot Actuator**.

---

## 🧩 Connected Microservices

| Service Name     | Port  | Description |
|------------------|-------|-------------|
| **Booking Service**   | `8081` | Handles booking-related operations. |
| **Inventory Service** | `8080` | Manages venue and event inventory. |

The gateway forwards requests to these services based on defined routes.

---

## ⚙️ Key Technologies

| Technology | Purpose |
|-------------|----------|
| **Spring Boot 3.5.6** | Application framework |
| **Spring Cloud Gateway (WebMVC)** | API Gateway routing and filters |
| **Keycloak** | OAuth 2.0 authentication and JWT validation |
| **Resilience4j** | Circuit breaker and retry mechanism |
| **Spring Boot Actuator** | Monitoring and health checks |
| **SpringDoc OpenAPI** | API documentation (Swagger UI) |
| **Java 21** | Runtime language |

---
