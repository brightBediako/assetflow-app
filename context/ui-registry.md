# UI Registry

Living document. Updated after every component is built. Read this before building any new component — match existing patterns exactly before inventing new ones.

---

## How to Use

Before building any component:

1. Check if a similar component already exists here
2. If yes — match its exact classes
3. If no — build it following ui-rules.md and ui-tokens.md, then add it here

After building any component — update this file with the component name, file path, and exact classes used.

---

## Theme Tokens

| Token Set | File | Notes |
|-----------|------|-------|
| Color palette | `core/ui/theme/Color.kt` | MD3 surface, primary, secondary, tertiary, error, fixed, and dark variants from `ui-tokens.md` |
| Status colors | `core/ui/theme/StatusColors.kt` | Semantic operational colors: Available, Booked, Pending, Maintenance Due, Overdue, etc. |
| Typography | `core/ui/theme/Type.kt` | Roboto Flex via Google Fonts; display, headline, title, body, label scales including medium/small roles |
| Shapes | `core/ui/theme/Shape.kt` | `AssetFlowCornerRadius` + `AssetFlowShapes` (sm 4dp → xl 24dp, search bar 28dp) |
| Spacing | `core/ui/theme/Spacing.kt` | xs 4dp, sm 8dp, md 16dp, lg 24dp, xl 32dp, gutter/margins, 48dp touch target |
| Theme | `core/ui/theme/Theme.kt` | `AssetFlowTheme` composable wrapping Material 3 light/dark schemes |

## Domain Models (UI-related)

| Model | File | Notes |
|-------|------|-------|
| OperationalStatus | `core/domain/model/OperationalStatus.kt` | Shared status enum + `displayLabel()` for badges and lists |
| UserRole | `core/domain/model/UserRole.kt` | Admin, Staff, Manager |
| User | `core/domain/model/User.kt` | Current user profile fields |
| Organization | `core/domain/model/User.kt` | Organization id + name |
| Asset | `core/domain/model/Asset.kt` | Asset list/detail fields |
| Booking | `core/domain/model/Booking.kt` | Booking history and approval fields |
| MaintenanceRecord | `core/domain/model/MaintenanceRecord.kt` | Maintenance list/detail + `MaintenanceStatus` |
| AppNotification | `core/domain/model/AppNotification.kt` | Notification center fields + `NotificationType` |
| AppSettings | `core/domain/model/AppSettings.kt` | Local preference toggles + `ThemePreference` |
| ContentLoadState | `core/domain/model/ContentLoadState.kt` | Prototype Normal/Loading/Empty/Error/Offline presentation states |
| Dashboard | `core/domain/model/Dashboard.kt` | Summary metrics, activity, category distribution, booking trend, status counts |

## Mock Data

| Source | File | Notes |
|--------|------|-------|
| MockDataRepository | `core/data/mock/MockDataRepository.kt` | Central lookup API for all prototype screens |
| MockUsers | `core/data/mock/MockUsers.kt` | Organization + 4 staff users |
| MockAssets | `core/data/mock/MockAssets.kt` | 14 assets across 7 categories |
| MockBookings | `core/data/mock/MockBookings.kt` | Pending, approved, active, completed, cancelled |
| MockMaintenance | `core/data/mock/MockMaintenance.kt` | Scheduled, due soon, overdue, completed + history |
| MockNotifications | `core/data/mock/MockNotifications.kt` | Mixed notification types with read/unread |
| MockDashboard | `core/data/mock/MockDashboard.kt` | Metrics derived from mock lists |
| MockBookingStore | `core/data/mock/MockBookingStore.kt` | Mutable bookings for submit/cancel/approve/reject |
| MockMaintenanceStore | `core/data/mock/MockMaintenanceStore.kt` | Mutable maintenance records and history for mark complete |
| MockNotificationStore | `core/data/mock/MockNotificationStore.kt` | Mutable notifications for mark read / mark all read |
| MockSettings | `core/data/mock/MockSettings.kt` | Default app preference values |
| MockSettingsStore | `core/data/mock/MockSettingsStore.kt` | Mutable settings toggles and theme preference |
| MockContentStateStore | `core/data/mock/MockContentStateStore.kt` | Prototype override for empty/loading/error/offline list states |

## Navigation

| Item | File | Notes |
|------|------|-------|
| MainDestination | `core/navigation/MainDestination.kt` | Bottom nav destinations: Dashboard, Assets, Bookings, Maintenance, Notifications |
| MainAppNavHost | `core/navigation/MainAppNavHost.kt` | Authenticated shell: single scaffold + nested tab NavHost + auth guard |
| ProfileShellScreen | `features/profile/presentation/ProfileScreen.kt` | Profile detail with back navigation, outside bottom tabs |
| SettingsShellScreen | `features/settings/presentation/SettingsScreen.kt` | Settings detail with back navigation, outside bottom tabs |
| Routes.Main | `core/navigation/Routes.kt` | Root route for authenticated main app graph |

| MockAuthSession | `core/data/mock/MockAuthSession.kt` | Prototype sign-in/sign-out flag for UI-only auth flow |

## Screens

| Screen | File | Notes |
|--------|------|-------|
| LoginScreen | `features/auth/presentation/LoginScreen.kt` | Email/password form, brand header, forgot password snackbar, links to register |
| LoginRoute | `features/auth/presentation/LoginScreen.kt` | Wires state, validation, `MockAuthSession.signIn()`, navigation callbacks |
| LoginUiState | `features/auth/presentation/LoginUiState.kt` | Email, password, loading, field errors |
| AuthBrandHeader | `features/auth/presentation/AuthBrandHeader.kt` | Shared logo/title block for login and register |
| RegisterScreen | `features/auth/presentation/RegisterScreen.kt` | Full registration form with role chips |
| RegisterRoute | `features/auth/presentation/RegisterScreen.kt` | Validation, `MockAuthSession.signIn()`, navigation |
| RegisterUiState | `features/auth/presentation/RegisterUiState.kt` | Name, org, email, passwords, role, errors |

## Dashboard Screens

| Screen | File | Notes |
|--------|------|-------|
| DashboardScreen | `features/dashboard/presentation/DashboardScreen.kt` | Metrics grid, utilization, analytics, quick actions, recent activity |
| DashboardRoute | `features/dashboard/presentation/DashboardScreen.kt` | Loads `MockDataRepository` dashboard data |
| DashboardUiState | `features/dashboard/presentation/DashboardUiState.kt` | User, org, summary, analytics, recent activity |
| DashboardAnalyticsSection | `features/dashboard/presentation/DashboardAnalytics.kt` | Availability, category, booking trend, maintenance charts |

## Asset Screens

| Screen | File | Notes |
|--------|------|-------|
| AssetListScreen | `features/assets/presentation/AssetListScreen.kt` | Search, category/status filters, scrollable asset cards |
| AssetListRoute | `features/assets/presentation/AssetListScreen.kt` | Loads assets from `MockDataRepository`, local filter state |
| AssetListUiState | `features/assets/presentation/AssetListUiState.kt` | Search query, filters, filtered asset list |
| AssetDetailsScreen | `features/assets/presentation/AssetDetailsScreen.kt` | Full asset detail, booking/maintenance summary, CTAs |
| AssetDetailsRoute | `features/assets/presentation/AssetDetailsScreen.kt` | Loads asset by ID from `MockDataRepository` |
| AssetDetailsUiState | `features/assets/presentation/AssetDetailsUiState.kt` | Asset, booking, maintenance, book eligibility |

## Booking Screens

| Screen | File | Notes |
|--------|------|-------|
| CreateBookingScreen | `features/bookings/presentation/CreateBookingScreen.kt` | Asset summary, form, conflict + success states |
| CreateBookingRoute | `features/bookings/presentation/CreateBookingScreen.kt` | Validates and submits via `MockBookingStore` |
| CreateBookingUiState | `features/bookings/presentation/CreateBookingUiState.kt` | Form fields and submission state |
| BookingHistoryScreen | `features/bookings/presentation/BookingHistoryScreen.kt` | Upcoming/past sections, cancel action, approvals entry |
| BookingHistoryRoute | `features/bookings/presentation/BookingHistoryScreen.kt` | Current user bookings from `MockBookingStore` |
| BookingApprovalScreen | `features/bookings/presentation/BookingApprovalScreen.kt` | Pending list + approval bottom sheet |
| BookingApprovalRoute | `features/bookings/presentation/BookingApprovalScreen.kt` | Approve/reject updates local mock state |
| BookingListItem | `features/bookings/presentation/BookingModels.kt` | Booking enriched with asset name/location |

## Maintenance Screens

| Screen | File | Notes |
|--------|------|-------|
| MaintenanceListScreen | `features/maintenance/presentation/MaintenanceListScreen.kt` | Overdue, due soon, scheduled, completed sections |
| MaintenanceListRoute | `features/maintenance/presentation/MaintenanceListScreen.kt` | Loads records from `MockDataRepository` |
| MaintenanceListUiState | `features/maintenance/presentation/MaintenanceListUiState.kt` | Grouped maintenance list items with asset context |
| MaintenanceDetailsScreen | `features/maintenance/presentation/MaintenanceDetailsScreen.kt` | Asset summary, work order, history timeline, actions |
| MaintenanceDetailsRoute | `features/maintenance/presentation/MaintenanceDetailsScreen.kt` | Loads record by ID; mark complete via `MockMaintenanceStore` |
| MaintenanceDetailsUiState | `features/maintenance/presentation/MaintenanceDetailsUiState.kt` | Record, asset, history, completion eligibility |

## Notification Screens

| Screen | File | Notes |
|--------|------|-------|
| NotificationsScreen | `features/notifications/presentation/NotificationsScreen.kt` | Grouped by type, read/unread, mark all read |
| NotificationsRoute | `features/notifications/presentation/NotificationsScreen.kt` | Loads from `MockNotificationStore`, tap navigates by type |
| NotificationsUiState | `features/notifications/presentation/NotificationsUiState.kt` | Grouped notifications + unread count |

## Profile Screens

| Screen | File | Notes |
|--------|------|-------|
| ProfileScreen | `features/profile/presentation/ProfileScreen.kt` | Identity header, account details, activity summary, settings entry, logout |
| ProfileRoute | `features/profile/presentation/ProfileScreen.kt` | Loads current user from `MockDataRepository` |
| ProfileShellScreen | `features/profile/presentation/ProfileScreen.kt` | Scaffold with back navigation for root graph |
| ProfileUiState | `features/profile/presentation/ProfileUiState.kt` | User, org, booking counts, app version |

## Settings Screens

| Screen | File | Notes |
|--------|------|-------|
| SettingsScreen | `features/settings/presentation/SettingsScreen.kt` | Notification/reminder toggles, theme chips, prototype demo states, about section |
| SettingsRoute | `features/settings/presentation/SettingsScreen.kt` | Reads/writes `MockSettingsStore` |
| SettingsShellScreen | `features/settings/presentation/SettingsScreen.kt` | Scaffold with back navigation for root graph |
| SettingsUiState | `features/settings/presentation/SettingsUiState.kt` | Preference values + about metadata |

## Components

| Component | File | Notes |
|-----------|------|-------|
| StatusBadge | `core/ui/components/StatusBadge.kt` | Pill badge; `OperationalStatus` → `AssetFlowStatusColors`; shape `RoundedCornerShape(AssetFlowCornerRadius.Full)` |
| AssetFlowButton | `core/ui/components/AssetFlowButton.kt` | Variants: Primary, Secondary, Text, Destructive; supports `isLoading`, min height 48dp |
| AssetFlowTextField | `core/ui/components/AssetFlowTextField.kt` | Outlined text field; label, placeholder, error, supporting text |
| AssetFlowSearchField | `core/ui/components/AssetFlowSearchField.kt` | Search icon; 28dp corner radius; surface container background |
| AssetFlowCard | `core/ui/components/AssetFlowCard.kt` | Elevated card; `surfaceContainerLow`; optional `onClick` |
| AssetListCard | `core/ui/components/AssetListCard.kt` | Asset row with category icon, status badge, maintenance hint |
| AssetCategoryIcon | `core/ui/components/AssetCategoryIcon.kt` | Category-based icon placeholder for asset list and details |
| BookingCard | `core/ui/components/BookingCard.kt` | Booking row with status badge and optional action |
| MaintenanceCard | `core/ui/components/MaintenanceCard.kt` | Maintenance row with asset, due date, priority, status badge |
| NotificationCard | `core/ui/components/NotificationCard.kt` | Type icon, unread indicator, title, message, timestamp |
| UserAvatar | `core/ui/components/UserAvatar.kt` | Circular initials avatar for profile header |
| SettingsToggleRow | `core/ui/components/SettingsToggleRow.kt` | Label, optional description, and switch for settings lists |
| EmptyState | `core/ui/components/EmptyState.kt` | Icon, title, description, optional primary CTA |
| ErrorState | `core/ui/components/ErrorState.kt` | Error title/description + retry; `NetworkUnavailableState` variant |
| SupportStateHost | `core/ui/components/SupportStateHost.kt` | Switches Normal/Loading/Empty/Error/Offline for major screens |
| LoadingState | `core/ui/components/LoadingState.kt` | Full screen spinner + `LoadingListPlaceholder` skeleton rows |
| SectionHeader | `core/ui/components/SectionHeader.kt` | Title + optional text action button |
| AnalyticsBarRow | `core/ui/components/AnalyticsCharts.kt` | Horizontal labeled bar for dashboard summaries |
| AnalyticsColumnChart | `core/ui/components/AnalyticsCharts.kt` | Vertical column chart for booking trends |
| AssetFlowTopAppBar | `core/ui/components/AssetFlowTopAppBar.kt` | Center aligned; title + organization; profile icon; optional back |
| AssetFlowBottomNavigation | `core/ui/components/AssetFlowBottomNavigation.kt` | 5 tab `NavigationBar`; notification badge; pill indicator |
| MainAppScaffold | `core/ui/components/MainAppScaffold.kt` | Top bar + bottom nav + content; organization name and notification badge |
| ComponentsPreviewScreen | `core/ui/components/ComponentsPreviewScreen.kt` | Showcase for all shared components (Dashboard tab) |
| ThemePreviewScreen | `core/ui/components/ThemePreviewScreen.kt` | Dev preview for color/typography tokens (Compose preview only) |
