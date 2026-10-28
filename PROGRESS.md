# OpsPilot — Progress

## Current Milestone

Phase 1: Foundation and the first equipment vertical slice.

## Completed

- Created the GitHub repository.
- Documented the project overview in README.md.
- Defined development phases in PROJECT_PLAN.md.
- Bootstrapped Spring Boot 3.5.16 with Java 21 and the web starter.
- Restored missing Maven Wrapper configuration and ignored backend build output.
- Verified the existing context-load test: 1 test, 0 failures, 0 errors.
- Verified Tomcat startup on localhost:8080 and the expected HTTP 404 at `/`.

## In Progress

- Establishing project documentation.
- Understanding the initial architecture.

## Next Steps

1. Document the architecture and its main trade-offs.
2. Verify local development prerequisites.
3. Implement and test the first Spring Boot REST endpoint.
4. Start PostgreSQL using Docker Compose.
5. Implement and test equipment creation and listing.
6. Connect the Next.js frontend.

## Blockers

- None reported. Environment readiness still needs verification.

## Technical Debt and Limitations

- Authentication and tenant isolation are planned for Phase 2.
- The Phase 1 application must remain local-only.

## Decisions

- Start with a modular monolith.
- Use Spring Boot for business rules and persistence.
- Introduce Python when AI functionality is needed.
- Defer Kafka, Redis, and Kubernetes until justified.
- Push to GitHub at meaningful milestones.
