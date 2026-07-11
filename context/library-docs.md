# Library Docs

Project-specific usage patterns for AssetFlow Mobile. This file explains how libraries and platform APIs should be used in this project, based on `context/project-overview.md`, `context/architecture.md`, and `context/build-plan.md`.

The current focus is UI-first Android development with dummy data. Real backend, Room persistence, JWT validation, and WorkManager scheduling should not be implemented until the UI prototype is approved.

---

## Before Using Any Library

Before implementing a feature that touches a library:

1. Read `context/project-overview.md` to confirm the product goal.
2. Read `context/architecture.md` to confirm the intended boundary.
3. Read `context/build-plan.md` to confirm whether the current phase is UI-only or backend wiring.
4. Check whether the feature already has a component, model, or mock record before creating a new one.

During the UI-first phase:

- Prefer dummy data over real services.
- Prefer simple local state over persistence.
- Prefer Compose previews and visible screen states over invisible infrastructure.
- Do not add backend, database, analytics, or auth dependencies unless the current phase explicitly calls for them.

---

## Kotlin

Kotlin is the main language for the Android application.

### Rules

- Use clear data classes for UI and mock models.
- Prefer immutable values with `val`.
- Use `sealed class` or `sealed interface` for finite UI states when needed.
- Keep functions small and named around user intent.
- Avoid large utility objects that mix unrelated domains.
- Keep feature-specific types inside the feature package unless they are shared across multiple features.

### Example Model

```kotlin
data class Asset(
    val id: String,
    val name: String,
    val category: String,
    val location: String,
    val status: AssetStatus,
    val description: String,
)

enum class AssetStatus {
    Available,
    Booked,
    MaintenanceDue,
    Unavailable,
}
```

### UI State Pattern

Use simple UI state classes for screens.

```kotlin
data class AssetListUiState(
    val searchQuery: String = "",
    val selectedCategory: String = "All",
    val selectedStatus: AssetStatus? = null,
    val assets: List<Asset> = emptyList(),
)
```

---

## Jetpack Compose

Jetpack Compose is the UI framework for AssetFlow Mobile.

### Rules

- Build complete screens with dummy data first.
- Keep composables focused and readable.
- Screen composables should receive state and callbacks.
- Reusable UI pieces belong in `core/ui/components`.
- Feature-specific UI pieces stay inside the feature's `presentation` package.
- Avoid putting data creation directly inside reusable components.
- Use previews or mock screen states for visual verification where practical.

### Screen Structure

Preferred screen shape:

```kotlin
@Composable
fun AssetListScreen(
    uiState: AssetListUiState,
    onSearchChange: (String) -> Unit,
    onAssetClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    // UI only. State comes from caller or ViewModel.
}
```

During the dummy-data phase, it is acceptable for a route-level composable to load mock data directly from the mock data layer.

```kotlin
@Composable
fun AssetListRoute(
    onAssetClick: (String) -> Unit,
) {
    val uiState = remember {
        AssetListUiState(assets = MockAssets.assets)
    }

    AssetListScreen(
        uiState = uiState,
        onSearchChange = {},
        onAssetClick = onAssetClick,
    )
}
```

### Compose State

- Use `remember` and `mutableStateOf` for local UI-only state.
- Use `ViewModel` state for screen-level state when interaction grows.
- Do not introduce persistence just to keep prototype UI state alive.
- Use `LazyColumn` or `LazyVerticalGrid` for lists.
- Use stable keys for list items when possible.

---

## Material Design Components

Use Material components as the base UI toolkit unless the project later defines a custom design system.

### Recommended Components

- `Scaffold` for top-level screen layout
- `TopAppBar` for screen headers
- `NavigationBar` for main mobile navigation
- `Card` for asset, booking, maintenance, and dashboard content
- `Button`, `OutlinedButton`, and `TextButton` for actions
- `TextField` or `OutlinedTextField` for forms and search
- `AssistChip`, `FilterChip`, or custom badges for status labels
- `SnackbarHost` for lightweight feedback

### Rules

- Use consistent spacing between sections.
- Keep status colors consistent across the app.
- Do not over-customize Material components before the prototype is visually stable.
- Every major feature screen should have normal, empty, loading, and error states by the polish phase.

### Status Badges

Use consistent status labels:

- `Available`
- `Booked`
- `Maintenance Due`
- `Unavailable`
- `Pending`
- `Approved`
- `Active`
- `Completed`
- `Cancelled`
- `Overdue`

---

## Navigation Compose

Use Navigation Compose for moving between screens.

### Route Rules

- Keep route names centralized.
- Use simple string IDs for details screens during the dummy-data phase.
- Do not pass full objects through navigation.
- Detail screens should look up mock records by ID.

### Expected Routes

```kotlin
object Routes {
    const val Login = "login"
    const val Register = "register"
    const val Dashboard = "dashboard"
    const val Assets = "assets"
    const val AssetDetails = "assets/{assetId}"
    const val CreateBooking = "bookings/create/{assetId}"
    const val Bookings = "bookings"
    const val BookingApprovals = "bookings/approvals"
    const val Maintenance = "maintenance"
    const val MaintenanceDetails = "maintenance/{maintenanceId}"
    const val Notifications = "notifications"
    const val Profile = "profile"
    const val Settings = "settings"
}
```

### Main Navigation

Main authenticated navigation should include:

- Dashboard
- Assets
- Bookings
- Maintenance
- Notifications or notification entry point

Profile and settings can be reached from the top app bar or account entry point.

---

## ViewModel and Lifecycle

Use ViewModels when a screen has meaningful interaction or multiple UI states.

### Rules

- ViewModels expose immutable UI state.
- UI sends events through clear callback methods.
- Keep Android framework dependencies out of domain models.
- During the UI-first phase, ViewModels may read from mock repositories.
- Do not introduce real API or database calls in ViewModels until backend wiring begins.

### StateFlow Pattern

```kotlin
class AssetListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        AssetListUiState(assets = MockAssets.assets)
    )
    val uiState: StateFlow<AssetListUiState> = _uiState.asStateFlow()

    fun onSearchChange(query: String) {
        _uiState.update { current ->
            current.copy(searchQuery = query)
        }
    }
}
```

Compose screens can collect state with lifecycle-aware collection once that dependency is available.

---

## Mock Data

Mock data is required for the current UI-first build.

### Location

Use this package for shared dummy data:

```text
app/src/main/java/com/assetflow/mobile/core/data/mock/
```

### Rules

- Keep mock records realistic and internally consistent.
- Reuse the same asset IDs across assets, bookings, maintenance, and notifications.
- Do not create separate fake data inside every screen.
- Include enough records to make list, detail, empty, and status states visible.
- Keep mock data close to future domain model shapes so backend wiring is easier later.

### Recommended Mock Files

```text
core/data/mock/
  MockUsers.kt
  MockAssets.kt
  MockBookings.kt
  MockMaintenance.kt
  MockNotifications.kt
  MockDashboard.kt
```

### Required Sample Coverage

- At least 12 assets
- Multiple asset categories
- Available, booked, maintenance due, and unavailable assets
- Pending, approved, active, completed, and cancelled bookings
- Scheduled, due soon, overdue, and completed maintenance records
- Booking, return reminder, maintenance, and system notifications

---

## Retrofit

Retrofit will be used later for Spring Boot REST API communication. Do not wire Retrofit during the UI-first phase unless the build plan explicitly moves into backend integration.

### Later Usage

Retrofit should own:

- HTTP API interfaces
- Request DTOs
- Response DTOs
- Auth header handling through an interceptor

### Rules

- Keep Retrofit DTOs separate from UI models.
- Map DTOs to domain models in repositories.
- Do not call Retrofit directly from composables.
- Do not call Retrofit directly from screen-level UI state classes.
- All protected requests must include a JWT when real auth exists.

### Expected API Groups

- `/auth`
- `/users`
- `/assets`
- `/bookings`
- `/maintenance`
- `/dashboard`
- `/notifications`

---

## Room Database

Room will be used later for local cache only. It is not the source of truth for shared organizational data.

### Later Usage

Room may cache:

- Asset lists and asset details
- Booking history for the signed-in user
- Maintenance records relevant to visible assets
- Dashboard summary snapshots
- Notification history

### Rules

- Do not use Room during the first UI prototype unless explicitly needed.
- Never store plain text passwords.
- Never treat cached data as final authorization truth.
- Keep entities separate from UI models.
- Map entities to domain models through repositories.

---

## WorkManager

WorkManager will be used later for background reminders and safe sync tasks.

### Later Usage

Use WorkManager for:

- Asset return reminders
- Maintenance due reminders
- Periodic refresh of cached data
- Retry of safe network sync operations

### Rules

- Do not use WorkManager during UI-only phases.
- Do not create final bookings or approvals from background workers without backend confirmation.
- Keep worker inputs small and serializable.
- Workers should call repositories, not UI code.

---

## JWT and Secure Storage

JWT authentication will be wired after the UI prototype is approved.

### Later Usage

- Store tokens using secure Android storage patterns.
- Attach tokens to API requests through the Retrofit auth interceptor.
- Clear tokens on logout.
- Do not store tokens in Room entities.
- Do not hardcode production tokens in source code.

During UI-first work, use a mock authenticated user instead of real token handling.

---

## Spring Boot API Contract

The Spring Boot backend is outside the Android UI layer. The mobile app should consume it only through REST APIs when backend integration begins.

### Rules

- Backend owns authentication, role checks, booking conflict validation, maintenance rules, dashboard aggregation, and audit trails.
- Android owns UI state, screen navigation, user interactions, and local display formatting.
- Any real mutation must be validated by the backend before it is treated as final.
- The mobile app should show conflict, success, loading, and error states clearly.

---

## PostgreSQL

PostgreSQL is the backend source of truth. The Android app should not connect directly to PostgreSQL.

### Rules

- Android talks to Spring Boot APIs, not PostgreSQL.
- Room is only a cache, not a replacement for PostgreSQL.
- Organization and role scoping must be enforced by the backend.
- Dashboard totals should come from backend APIs when real data is introduced.

---

## Images and Icons

Use simple local icons or placeholders during the UI-first phase.

### Rules

- Asset cards can use category icons instead of real images.
- Keep image handling simple until real asset media requirements are confirmed.
- Do not add cloud storage or image upload logic during UI-first work.
- Use consistent icon meanings for assets, bookings, maintenance, notifications, and settings.

---

## Forms and Validation

Forms should be visually complete before real validation is wired.

### UI-First Rules

- Show required fields clearly.
- Show disabled, error, success, and loading button states where relevant.
- For login/register, submit can navigate using a mock session.
- For bookings, submit can show local success or conflict state.
- For maintenance, mark complete can update local UI state only.

### Later Rules

- Backend validates credentials, roles, booking windows, conflicts, and maintenance updates.
- Client-side validation improves UX but must not replace backend validation.

---

## Charts and Dashboard Visuals

Dashboard analytics should use simple visuals first.

### UI-First Rules

- Use progress bars, compact summaries, or lightweight chart components.
- Prefer simple visuals over adding a chart dependency too early.
- Use dummy values that match the mock data where practical.
- Empty states should be designed before real analytics exist.

Only add a chart library if the UI cannot be represented clearly with Compose primitives.

---

## Libraries Not Used In This Project

The old project context referenced web and job-search tooling. These are not part of AssetFlow Mobile unless the product scope changes.

Do not use:

- InsForge
- Adzuna
- PostHog
- Browserbase
- Stagehand
- OpenAI job matching flows
- Next.js Server Actions
- React PDF
- Recharts

These belonged to a different product direction and should not guide AssetFlow Mobile implementation.

---

## Implementation Priority

Use libraries in this order:

1. Kotlin and Jetpack Compose for screens
2. Material components for reusable UI
3. Navigation Compose for app flow
4. Mock data for every feature screen
5. ViewModel and StateFlow when screen interactions need structure
6. Retrofit after UI approval
7. Room after API data exists and caching is needed
8. WorkManager after reminder and sync behavior is confirmed

The UI prototype is successful when every major AssetFlow workflow can be clicked through with dummy data and no backend availability.
