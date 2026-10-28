# OpsPilot — Project Plan

## Development Principles

- Build one small, working feature at a time.
- Understand each dependency before introducing it.
- Keep business rules and authorization in Spring Boot.
- Test behavior and document verified results.
- Introduce infrastructure only when a feature requires it.

## Phase 1 — Foundation

Build the first vertical slice: create and list equipment.

Tasks:
- Set up Spring Boot with Java 21 and Maven.
- Run PostgreSQL with Docker Compose.
- Create the equipment schema using Flyway.
- Implement equipment creation and listing APIs.
- Set up Next.js with TypeScript.
- Connect the equipment form and list to the backend.
- Add automated tests.

Acceptance criteria:
- A user can submit an equipment code and name.
- Valid equipment is saved in PostgreSQL.
- Saved equipment appears in the UI after a page refresh.
- Blank fields are rejected with clear validation errors.
- Duplicate equipment codes within an organization are rejected.
- An automated integration test verifies creation and retrieval.
- Setup and test commands are documented.

Scope:
- Local development only.
- One fixed development organization.
- Authentication and real tenant isolation follow in Phase 2.
- No AI, Kafka, Redis, or cloud deployment yet.

## Phase 2 — Core Enterprise Platform

Add authentication, organization membership, roles,
tenant isolation, incidents, and maintenance tickets.

Acceptance criteria:
- Unauthenticated requests cannot access business data.
- Users can access only authorized organizations.
- Tests verify role restrictions and cross-tenant isolation.
- Users can create incidents and manage maintenance tickets.
- Important changes produce audit records.

## Phase 3 — Document Intelligence

Add document uploads, object storage, ingestion,
the Python AI service, vector search, and cited answers.

Acceptance criteria:
- Authorized users can upload supported documents.
- Processing status and failures are visible.
- Retrieval respects tenant and document permissions.
- AI answers cite retrieved sources.
- Insufficient evidence is clearly communicated.
- Retrieval quality is evaluated against a documented dataset.

## Phase 4 — AI Workflows

Add tool calling and maintenance ticket drafting.

Acceptance criteria:
- The assistant can retrieve authorized operational data.
- State-changing actions require explicit human approval.
- Approved actions are reauthorized by the backend.
- Retries do not create duplicate tickets.
- Executions and approvals are auditable.

## Phase 5 — Asynchronous Processing

Introduce messaging and caching where measured needs justify them.

Acceptance criteria:
- Each new dependency has a documented justification.
- Async workflows handle retries and duplicate events.
- Failed processing can be inspected and recovered.
- Consistency expectations are documented and tested.

## Phase 6 — Production Readiness

Add deployment automation, observability,
security review, and performance evaluation.

Acceptance criteria:
- CI runs the required builds and tests.
- Logs, metrics, and traces support troubleshooting.
- Secrets are kept outside source control.
- Deployment and database recovery procedures are documented.
- Performance and AI quality results come from actual tests.