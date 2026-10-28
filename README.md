# OpsPilot

An AI-powered operations and knowledge management platform.

## Purpose

Help organizations manage equipment, incidents, maintenance
tickets, and technical documents through secure workflows.

## Technology Stack

- Backend: Java 21 and Spring Boot
- Frontend: TypeScript, React, and Next.js
- Database: PostgreSQL
- AI service, added later: Python and FastAPI

## Initial Architecture

Next.js → Spring Boot REST API → PostgreSQL

Spring Boot owns business rules, authorization, and database access.
Python will handle AI orchestration when we introduce AI features.

We will start with a modular monolith: one backend application
organized into clear business modules.

## First Feature

Create equipment and display saved equipment in a list.

This feature will connect the frontend, backend, and database.

## Backend Development

Requires JDK 21. The Maven Wrapper downloads Maven and dependencies on first use.
Keep the hidden `backend/spring-boot/.mvn` directory alongside `mvnw`.

From the project root:

```bash
cd backend/spring-boot
./mvnw test
./mvnw spring-boot:run -Dspring-boot.run.arguments=--server.address=127.0.0.1
```

The server listens at http://localhost:8080. A 404 response at `/` is expected
until we implement a controller. Stop the server with Ctrl+C.

Current baseline: Spring Boot 3.5.16, Java 21, and JUnit 5.
Database connectivity and business endpoints are not implemented yet.
