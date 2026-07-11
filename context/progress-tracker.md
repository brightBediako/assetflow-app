# Progress Tracker

Update this file after every completed feature. Any AI agent reading this should immediately know what is done, what is in progress, and what is next.

The current workflow is UI-first with dummy data. Do not mark backend/API/database work as in progress until the UI prototype is approved.

---

## Current Status

**Phase:** UI prototype delivered — awaiting approval  
**Last completed:** 20 Visual QA Pass  
**In progress:** Paused  
**Next:** Later Phase - Backend Wiring (only after explicit UI approval)

The UI-first delivery (features 01–20) is complete. Major workflows are navigable with dummy data and do not depend on a backend.

---

## Progress

### Phase 1 - App Foundation

- [x] 01 Android Project Shell
- [x] 02 Theme and Shared Components
- [x] 03 Mock Data Layer

### Phase 2 - Authentication UI

- [x] 04 Login Screen
- [x] 05 Registration Screen

### Phase 3 - Main Navigation

- [x] 06 App Navigation Shell

### Phase 4 - Dashboard UI

- [x] 07 Dashboard Screen
- [x] 08 Dashboard Analytics UI

### Phase 5 - Asset Management UI

- [x] 09 Asset List Screen
- [x] 10 Asset Details Screen

### Phase 6 - Booking UI

- [x] 11 Create Booking Screen
- [x] 12 Booking History Screen
- [x] 13 Booking Approval UI

### Phase 7 - Maintenance UI

- [x] 14 Maintenance List Screen
- [x] 15 Maintenance Details Screen

### Phase 8 - Notifications UI

- [x] 16 Notifications Screen

### Phase 9 - Profile and Settings UI

- [x] 17 Profile Screen
- [x] 18 Settings Screen

### Phase 10 - UI Polish and Review

- [x] 19 Empty, Loading, and Error States
- [x] 20 Visual QA Pass

### Later Phase - Backend Wiring

- [ ] Real JWT login and registration
- [ ] Retrofit API interfaces
- [ ] Spring Boot API integration
- [ ] PostgreSQL-backed records
- [ ] Room Database caching
- [ ] WorkManager reminders
- [ ] Backend booking conflict validation
- [ ] Backend role-based permissions
- [ ] Backend dashboard aggregation

---

## Decisions Made During Build

- AssetFlow Mobile will be built as a native Android app using Kotlin and Jetpack Compose.
- The project follows a UI-first workflow with dummy data before backend wiring.
- Dummy data should be centralized under `core/data/mock`.
- The backend remains the source of truth later, but it should not block UI prototype work.
- Design tokens from `ui-tokens.md` are implemented under `core/ui/theme` (Color, Type, Shape, Spacing, StatusColors, Theme).
- Mock data lives under `core/data/mock` with `MockDataRepository` as the single access point for UI screens.
- `MockAuthSession` tracks prototype login state; login screen uses mock user email prefilled from `MockDataRepository`.
- Feature 19 uses `MockContentStateStore` (Settings → Demo screen states) so Assets, Bookings, Maintenance, Notifications, and Dashboard can preview empty/loading/error/offline states without a backend.
- Feature 20 Visual QA: scrubbed user-facing backend/prototype copy, completed Roboto Flex typography roles, slimmed top app bar to title + organization, aligned Profile/Settings bars with `AssetFlowTopAppBar`, removed unused placeholder helpers.

---

## Notes

- Keep this file updated after each completed feature.
- When a feature is started, update `In progress`.
- When a feature is completed, check it off and update `Last completed` and `Next`.
- If implementation differs from `context/build-plan.md`, record the reason here.
- **UI freeze:** Treat screens as approved until product review requests changes. Backend wiring should map into existing UI without redesign unless a gap is found.
