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