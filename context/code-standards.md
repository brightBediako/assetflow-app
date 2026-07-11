# Code Standards

Implementation rules and conventions for AssetFlow Mobile. Follow these in every session to keep the project consistent across Android UI, dummy data, and later backend integration work.

---

## Engineering Mindset

- Read `context/project-overview.md`, `context/architecture.md`, `context/build-plan.md`, and `context/library-docs.md` before implementing.
- Build only the current feature from `context/build-plan.md`.
- The current workflow is UI-first with dummy data.
- Every screen must be visible, navigable, and testable before real backend logic is added.
- Do not create duplicate functions, components, models, or files. Search first and reuse existing patterns.
- Keep changes small and directly related to the current task.
- Prefer simple, readable code over clever abstractions.
- Do not add backend, database, authentication, or background worker logic during UI-only phases.

---

## Kotlin Standards

- Prefer `val` over `var`.
- Use clear `data class` models for UI and mock data.
- Use `enum class` for simple status values.
- Use `sealed class` or `sealed interface` for finite UI states when needed.
- Keep functions small and named around user intent.
- Avoid nullable values unless the UI truly needs an absent state.
- Avoid force unwraps and unsafe assumptions.
- Keep domain naming aligned with AssetFlow concepts: asset, booking, maintenance, notification, dashboard, organization, role.

---

## Package Structure

Follow the structure in `context/architecture.md`.

Shared app code belongs in:

```text
core/
  data/
  domain/
  navigation/
  ui/
  worker/
```

Feature code belongs in:

```text
features/
  auth/
  dashboard/
  assets/
  bookings/
  maintenance/
  notifications/
```

Each feature should use:

```text
data/
domain/
presentation/
```

Do not place feature-specific UI in `core/ui/components` unless it is reused by multiple features.

---

## File Naming

- Kotlin files use PascalCase: `AssetListScreen.kt`, `BookingStatusBadge.kt`.
- Models use singular names: `Asset.kt`, `Booking.kt`, `MaintenanceRecord.kt`.
- Mock data files use `Mock` prefix: `MockAssets.kt`, `MockBookings.kt`.
- Navigation files should be clearly named: `Routes.kt`, `AppNavHost.kt`.
- Theme files stay under `core/ui/theme`.
- One major composable per file unless helper composables are small and private.

---

## Compose Component Structure

Screen files should be easy to scan.

Preferred order:

```kotlin
// 1. Imports

// 2. Screen route composable

// 3. Stateless screen composable

// 4. Private section composables

// 5. Preview or mock preview state when useful
```

Preferred screen shape:

```kotlin
@Composable
fun AssetListScreen(
    uiState: AssetListUiState,
    onSearchChange: (String) -> Unit,
    onAssetClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    // UI only
}
```

Rules:

- Screens receive state and callbacks.
- Reusable components receive only the data they need.
- Keep business rules out of composables.
- Do not call Retrofit, Room, or WorkManager directly from composables.
- Keep local UI-only state local with `remember`.

---

## UI State

Use UI state classes for screens that have filters, search, selected items, loading, empty, or error states.

```kotlin
data class AssetListUiState(
    val searchQuery: String = "",
    val selectedCategory: String = "All",
    val assets: List<Asset> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
```

Rules:

- UI state should be immutable.
- UI state should be specific to the screen.
- Use user-facing error messages only.
- Do not expose raw exceptions to UI state.

---

## ViewModel Rules

Use ViewModels when a screen has meaningful interaction.

- ViewModels expose immutable state.
- ViewModels can use mock repositories during UI-first phases.
- ViewModels should not know about composable functions.
- ViewModels should not call Android UI APIs.
- Keep event methods explicit: `onSearchChange`, `onAssetSelected`, `onSubmitBooking`.

Do not add real network or database logic to ViewModels until the backend wiring phase.

---

## Dummy Data Rules

Dummy data is required until the UI prototype is approved.

Location:

```text
app/src/main/java/com/assetflow/mobile/core/data/mock/
```

Rules:

- Centralize mock data.
- Reuse IDs across assets, bookings, maintenance, and notifications.
- Keep sample data realistic.
- Include enough data for normal, empty, loading, and error UI states.
- Do not create unrelated fake records inside individual screens.
- Keep mock models close to future domain models.

Required mock coverage:

- At least 12 assets
- Multiple asset categories
- Available, booked, maintenance due, and unavailable assets
- Pending, approved, active, completed, and cancelled bookings
- Scheduled, due soon, overdue, and completed maintenance records
- Booking, return reminder, maintenance, and system notifications

---

## Navigation Rules

- Keep route names centralized.
- Do not pass full objects through navigation.
- Pass IDs and look up mock records by ID.
- Main authenticated navigation should include Dashboard, Assets, Bookings, Maintenance, and Notifications.
- Profile and settings should be reachable from an account/profile entry point.
- Login and registration are UI-only until real auth begins.

---

## Error Handling

- Never use empty catch blocks.
- User-facing errors must be clear and human readable.
- Do not expose raw exception messages to users.
- During UI-first work, provide designed error states even when errors are simulated.
- Later backend/API errors should be mapped to user-safe UI messages.

---

## Security Rules

During UI-first work:

- Use a mock authenticated user.
- Do not hardcode real credentials, API keys, JWTs, or backend URLs.
- Do not add production secrets to source files.

During backend wiring:

- Store JWTs using secure Android storage patterns.
- Attach JWTs through a Retrofit interceptor.
- Enforce role-based rules on the backend, not only in the UI.
- Never store passwords or long-lived secrets in Room.

---

## Later Backend Integration Rules

Only begin this after the UI prototype is approved.

- Retrofit DTOs stay separate from UI models.
- Room entities stay separate from UI models.
- Repositories map DTOs/entities into domain models.
- Android talks to Spring Boot APIs, never directly to PostgreSQL.
- Backend owns authentication, role checks, booking conflict validation, maintenance rules, dashboard aggregation, and audit trails.
- WorkManager may schedule reminders and safe sync retries, but it must not make final booking or approval decisions without backend confirmation.

---

## Dependency Rules

- Do not add new dependencies unless the current feature requires them.
- Prefer Kotlin, Jetpack Compose, Material components, Navigation Compose, and mock data first.
- Avoid chart libraries unless dashboard visuals cannot be built clearly with Compose primitives.
- Do not add old web/job-search dependencies from the previous project direction.

Approved direction:

- Kotlin
- Jetpack Compose
- Material components
- Navigation Compose
- ViewModel and StateFlow
- Retrofit later
- Room later
- WorkManager later

Do not use:

- Next.js
- React
- Tailwind
- InsForge
- Adzuna
- PostHog
- Browserbase
- Stagehand
- OpenAI job matching flows

---

## Comments

- Comments should explain why, not what.
- Avoid comments that repeat the code.
- Do not leave TODO comments as a substitute for implementation.
- Brief comments are acceptable for non-obvious UI state or future backend boundary decisions.

---

## Completion Standard

A feature is complete only when:

- It matches the current build-plan feature.
- It uses dummy data if the UI has not been approved.
- It is navigable in the app.
- It has normal and relevant empty/loading/error states.
- It follows existing components and naming.
- It does not introduce unrelated backend or infrastructure work.
