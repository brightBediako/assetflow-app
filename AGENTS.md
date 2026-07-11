# AGENTS.md

Persistent guidance for AI agents working on AssetFlow Mobile.

AssetFlow Mobile is a native Android app for managing shared organizational assets. The current workflow is UI-first with dummy data. Real backend, database, authentication, Room caching, and WorkManager scheduling must wait until the UI prototype is approved.

---

## Read Before Anything Else

Read these files in this exact order before implementation:

1. `context/project-overview.md`
2. `context/architecture.md`
3. `context/build-plan.md`
4. `context/progress-tracker.md`
5. `context/code-standards.md`
6. `context/library-docs.md`
7. `context/ui-rules.md`
8. `context/ui-tokens.md`
9. `context/ui-registry.md`

Use these files as the source of truth. If they conflict, prefer the most specific file for the task:

- Product scope: `project-overview.md`
- System boundaries and package structure: `architecture.md`
- Current feature order: `build-plan.md`
- Completion status: `progress-tracker.md`
- Kotlin and implementation rules: `code-standards.md`
- Library usage: `library-docs.md`
- Visual and screen rules: `ui-rules.md`
- Built component inventory: `ui-registry.md`

---

## Current Project Direction

- Product: AssetFlow Mobile
- Platform: Native Android
- Language: Kotlin
- UI: Jetpack Compose
- Architecture: MVVM
- Current phase: UI prototype complete (features 01–20); awaiting approval
- Data source for now: centralized dummy data
- Backend later: Java Spring Boot REST APIs
- Database later: PostgreSQL through backend APIs
- Local cache later: Room Database
- Background work later: WorkManager

Do not start Retrofit, Room, JWT, or WorkManager wiring until the UI prototype is approved.

---

## Rules That Never Change

- Search first before creating any function, component, model, or file.
- Do not create duplicates.
- Build only the current feature from `context/build-plan.md`.
- Use dummy data until the UI prototype is approved.
- Keep dummy data centralized under `core/data/mock`.
- Every screen must be visible, navigable, and testable.
- Do not add real backend, database, authentication, Room, Retrofit, or WorkManager logic during UI-only phases.
- Update `context/progress-tracker.md` after every completed feature.
- Update `context/ui-registry.md` after every new reusable UI component.
- Keep changes scoped to the current task.
- Do not introduce old JobPilot/web stack assumptions.

---

## UI-First Workflow

For every feature:

1. Read the current feature in `context/build-plan.md`.
2. Check `context/progress-tracker.md` for status.
3. Search for existing screens, components, models, and mock data.
4. Build the UI with dummy data.
5. Make the screen navigable.
6. Include normal states and relevant empty/loading/error states.
7. Reuse shared components where possible.
8. Update `context/ui-registry.md` if a reusable component is added.
9. Update `context/progress-tracker.md` when the feature is complete.

The UI prototype is successful when all major workflows can be clicked through without backend availability.

---

## Android Implementation Rules

- Use Jetpack Compose for UI.
- Use Material components as the default UI base.
- Use `Scaffold` for top-level screen layout.
- Use `LazyColumn` for long lists.
- Use route composables for navigation wiring.
- Keep stateless screen composables separate from route/state wiring where practical.
- Screens receive state and callbacks.
- Keep business rules out of composables.
- Use ViewModels only when screen interaction needs structure.
- During UI-first work, ViewModels may read from mock repositories only.

---

## Dummy Data Rules

Dummy data must be realistic and internally consistent.

Required coverage:

- At least 12 assets
- Multiple asset categories
- Available, booked, maintenance due, and unavailable assets
- Pending, approved, active, completed, and cancelled bookings
- Scheduled, due soon, overdue, and completed maintenance records
- Booking, return reminder, maintenance, and system notifications
- Dashboard metrics that match the mock data where practical

Do not invent separate fake records inside individual screens. Reuse shared mock records.

---

## Navigation Rules

Main authenticated navigation should include:

- Dashboard
- Assets
- Bookings
- Maintenance
- Notifications

Profile and settings should be reachable from an account/avatar entry point.

Do not pass full objects through navigation. Pass IDs and look up records from mock data or repositories.

---

## Later Backend Wiring

Only start backend wiring after the UI prototype is approved.

Later work may include:

- Real JWT login and registration
- Retrofit API interfaces
- Spring Boot REST API integration
- PostgreSQL-backed records
- Room Database caching
- WorkManager reminders
- Backend booking conflict validation
- Backend role-based permissions
- Backend dashboard aggregation

When backend wiring begins:

- Android talks to Spring Boot APIs, never directly to PostgreSQL.
- Retrofit DTOs stay separate from UI models.
- Room entities stay separate from UI models.
- Repositories map DTOs/entities into domain models.
- Backend remains the source of truth for authentication, authorization, booking conflicts, maintenance rules, dashboard totals, and audit records.

---

## Do Not Use

These belonged to a previous project direction and must not guide AssetFlow Mobile:

- Next.js
- React
- Tailwind
- InsForge
- Adzuna
- PostHog
- Browserbase
- Stagehand
- OpenAI job matching flows
- Resume generation
- Company research agents

---

## Completion Checklist

Before calling a feature complete, confirm:

- The feature matches `context/build-plan.md`.
- Existing components and files were checked first.
- The UI uses dummy data.
- The screen is reachable through navigation.
- Normal and relevant empty/loading/error states are covered.
- No unrelated backend or infrastructure work was added.
- `context/progress-tracker.md` is updated.
- `context/ui-registry.md` is updated if reusable UI was added.
