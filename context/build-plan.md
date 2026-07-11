# Build Plan

## Core Principle

Build the full Android UI first using dummy data. Every screen must look complete, be navigable, and be visually testable before any real backend, database, or authentication logic is wired in.

The first delivery goal is a polished AssetFlow Mobile prototype that demonstrates the full user experience for asset management, bookings, maintenance, dashboard analytics, and notifications.

Implementation should follow:

- `context/project-overview.md` for product scope
- `context/architecture.md` for app structure and boundaries
- UI-first development with mock repositories or hardcoded sample data
- Small feature increments that can be opened and verified immediately

Real Spring Boot APIs, PostgreSQL persistence, JWT validation, Room caching, and WorkManager scheduling come after the UI is complete.

---

## Dummy Data Rule

Until the UI is approved, every screen should use local dummy data.

Use realistic sample records for:

- Users and roles
- Organizations
- Asset categories
- Assets
- Asset availability statuses
- Bookings
- Booking approval states
- Maintenance schedules
- Notifications
- Dashboard metrics

Dummy data should be centralized so screens do not each invent their own inconsistent sample records.

Recommended location:

```
app/src/main/java/com/assetflow/mobile/core/data/mock/
```

---

## Phase 1 - App Foundation

### 01 Android Project Shell

Create the basic Android app foundation.

**UI:**

- App name and package structure
- Splash/loading entry screen if needed
- Root app container
- Navigation host
- Shared scaffold for authenticated screens

**Dummy Data:**

- No API calls
- No database setup
- Hardcoded authenticated user can be used after the login screen

**Done When:**

- App launches successfully
- Screens can be registered in navigation
- Project structure follows `context/architecture.md`

---

### 02 Theme and Shared Components

Create the reusable visual system for the mobile app.

**UI:**

- Color palette
- Typography
- Buttons
- Text fields
- Search input
- Cards
- Status badges
- Empty states
- Loading placeholders
- Section headers
- Bottom navigation or top-level navigation pattern

**Dummy Data:**

- Use static previews or sample values for components

**Done When:**

- Shared components are ready before feature screens are built
- Components can support assets, bookings, maintenance, dashboard, and notifications

---

### 03 Mock Data Layer

Create centralized dummy data for the UI prototype.

**UI Support Data:**

- Current user profile
- Organization name
- Asset list with categories, availability, locations, and images/icons
- Booking list with pending, approved, active, completed, and cancelled states
- Maintenance list with due, overdue, scheduled, and completed states
- Notification list
- Dashboard summary metrics

**Logic:**

- Use simple Kotlin objects or mock repository functions
- Return static lists and details
- Keep naming aligned with future domain models

**Done When:**

- All feature screens can read from consistent mock data
- No screen depends on live API or local database setup

---

## Phase 2 - Authentication UI

### 04 Login Screen

Build the login experience as UI only.

**UI:**

- AssetFlow logo or app title
- Email input
- Password input
- Login button
- Forgot password link
- Create account link
- Short value statement for asset management

**Dummy Data:**

- Login button navigates to dashboard without real authentication
- Use a mock user session

**Done When:**

- Login screen is visually complete
- User can tap login and enter the main app flow

---

### 05 Registration Screen

Build the registration UI.

**UI:**

- Full name input
- Organization input
- Email input
- Password input
- Confirm password input
- Role selector with sample roles such as Admin, Staff, and Manager
- Create account button
- Link back to login

**Dummy Data:**

- Create account button navigates to dashboard or login
- No account is persisted

**Done When:**

- Registration screen is complete and navigable
- Role selection is visible and understandable

---

## Phase 3 - Main Navigation

### 06 App Navigation Shell

Build the main authenticated app shell.

**UI:**

- Dashboard tab
- Assets tab
- Bookings tab
- Maintenance tab
- Notifications tab or notification entry point
- Consistent top app bar with organization or screen title
- Profile/avatar entry point

**Dummy Data:**

- Use mock user and organization names
- All tabs route to placeholder or completed screens

**Done When:**

- User can move between all main sections
- Navigation feels like a real mobile app

---

## Phase 4 - Dashboard UI

### 07 Dashboard Screen

Build the full dashboard with dummy metrics.

**UI:**

- Greeting and organization summary
- Total assets card
- Available assets card
- Active bookings card
- Maintenance due card
- Asset utilization summary card
- Recent activity list
- Quick actions for View Assets, Book Asset, and Report Maintenance

**Dummy Data:**

- Total assets: realistic count
- Available assets: realistic count
- Active bookings: realistic count
- Maintenance due: realistic count
- Recent activity: 5 to 7 sample events

**Done When:**

- Dashboard communicates the state of the organization at a glance
- All quick actions navigate to the correct screens

---

### 08 Dashboard Analytics UI

Add lightweight visual analytics.

**UI:**

- Asset utilization chart or progress indicators
- Category distribution summary
- Booking trend summary
- Maintenance status summary

**Dummy Data:**

- Static chart values or simple progress bars
- Values should match the mock asset and booking records where practical

**Done When:**

- Analytics look polished even without real calculations
- Empty and normal states are both designed if needed

---

## Phase 5 - Asset Management UI

### 09 Asset List Screen

Build the asset browsing experience.

**UI:**

- Search field
- Category filter
- Availability filter
- Asset cards or list rows
- Asset image/icon
- Asset name
- Category
- Location
- Availability badge
- Last maintenance hint if relevant

**Dummy Data:**

- Include at least 12 sample assets
- Use mixed statuses such as Available, Booked, Maintenance Due, and Unavailable

**Done When:**

- User can visually scan and filter assets
- Asset list feels populated and realistic

---

### 10 Asset Details Screen

Build the detailed view for one asset.

**UI:**

- Asset image/icon
- Asset name and category
- Availability status
- Location
- Asset ID or tag number
- Description
- Current booking status
- Maintenance summary
- Booking CTA if available
- View maintenance history CTA

**Dummy Data:**

- Details come from the selected mock asset
- CTA states change based on availability

**Done When:**

- Asset details clearly answer what the asset is, where it is, and whether it can be booked

---

## Phase 6 - Booking UI

### 11 Create Booking Screen

Build the booking request experience.

**UI:**

- Selected asset summary
- Date picker UI placeholder
- Start time and end time fields
- Purpose/reason field
- Notes field
- Submit booking button
- Conflict warning design
- Success confirmation design

**Dummy Data:**

- Submit can show a local confirmation state
- Conflict warning can be triggered by a sample selected date or mock toggle

**Done When:**

- Booking flow is understandable without backend validation
- Success and conflict states are both visible

---

### 12 Booking History Screen

Build the user's booking list.

**UI:**

- Upcoming bookings section
- Past bookings section
- Booking status badges
- Asset name and location
- Booking date and time
- Cancel booking action for eligible records
- Empty state

**Dummy Data:**

- Include pending, approved, active, completed, and cancelled bookings

**Done When:**

- User can understand booking status at a glance
- Cancel action has a visible UI state, even if not persisted

---

### 13 Booking Approval UI

Build the approval workflow UI for admin or manager roles.

**UI:**

- Pending approvals list
- Requester name
- Asset requested
- Date/time requested
- Purpose
- Approve button
- Reject button
- Approval detail bottom sheet or detail screen

**Dummy Data:**

- Use mock pending requests
- Approve/reject can update only local UI state

**Done When:**

- Admin approval flow is visible and testable with mock data

---

## Phase 7 - Maintenance UI

### 14 Maintenance List Screen

Build the maintenance tracking list.

**UI:**

- Maintenance due section
- Overdue maintenance section
- Completed maintenance section
- Asset name
- Maintenance type
- Due date
- Priority/status badge
- Assigned person or team

**Dummy Data:**

- Include scheduled, due soon, overdue, and completed records

**Done When:**

- Maintenance risk is visible from the list screen
- Status colors and labels are easy to understand

---

### 15 Maintenance Details Screen

Build the maintenance detail view.

**UI:**

- Asset summary
- Maintenance title/type
- Status
- Due date
- Description
- Maintenance history timeline
- Mark complete button UI
- Schedule maintenance button UI

**Dummy Data:**

- Use sample history entries
- Mark complete can change the local visual state

**Done When:**

- User can see what maintenance is needed and what has happened before

---

## Phase 8 - Notifications UI

### 16 Notifications Screen

Build the notification center.

**UI:**

- Booking approval notifications
- Asset return reminders
- Maintenance alerts
- System announcements
- Read/unread state
- Timestamp
- Empty state

**Dummy Data:**

- Include mixed notification types
- Mark-as-read can be local UI only

**Done When:**

- Notification types are visually distinct
- User can understand what action is needed

---

## Phase 9 - Profile and Settings UI

### 17 Profile Screen

Build the user profile and account screen.

**UI:**

- User name
- Email
- Role
- Organization
- Logout button
- Basic account details
- App version or support link if useful

**Dummy Data:**

- Use mock current user
- Logout returns to login without clearing real data

**Done When:**

- User identity and role are visible
- Logout flow can be tested visually

---

### 18 Settings Screen

Build simple app settings UI.

**UI:**

- Notification preferences
- Reminder preferences
- Theme/system preference placeholder if needed
- About AssetFlow section

**Dummy Data:**

- Toggles update locally only

**Done When:**

- Settings are visually complete
- No real preference persistence is required yet

---

## Phase 10 - UI Polish and Review

### 19 Empty, Loading, and Error States

Design support states across the app.

**UI:**

- Empty asset list
- Empty booking history
- Empty notifications
- Loading skeletons or progress indicators
- Generic error state
- Network unavailable state placeholder

**Dummy Data:**

- Use toggles or preview states to verify each condition

**Done When:**

- Every major screen has normal, empty, loading, and error presentation covered

---

### 20 Visual QA Pass

Review the full prototype end to end.

**UI:**

- Check spacing consistency
- Check typography consistency
- Check status badge colors
- Check navigation labels
- Check button states
- Check screen readability on common Android screen sizes

**Done When:**

- The app can be demonstrated as a polished UI prototype
- No screen depends on backend availability
- All main user journeys can be clicked through with dummy data

---

## Later Phase - Backend Wiring

Do not start this phase until the UI prototype is approved.

Future logic work will include:

- Real JWT login and registration
- Retrofit API interfaces
- Spring Boot API integration
- PostgreSQL-backed records
- Room Database caching
- WorkManager reminders
- Booking conflict validation from the backend
- Role-based permissions from the backend
- Dashboard aggregation from backend APIs

This later phase should wire real data into the approved UI without redesigning the screens unless the approved UI exposes a product gap.

---

## Feature Count

- Phase 1 - App Foundation: 3 features
- Phase 2 - Authentication UI: 2 features
- Phase 3 - Main Navigation: 1 feature
- Phase 4 - Dashboard UI: 2 features
- Phase 5 - Asset Management UI: 2 features
- Phase 6 - Booking UI: 3 features
- Phase 7 - Maintenance UI: 2 features
- Phase 8 - Notifications UI: 1 feature
- Phase 9 - Profile and Settings UI: 2 features
- Phase 10 - UI Polish and Review: 2 features
- Total: 20 UI-first features
