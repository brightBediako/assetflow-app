# AssetFlow Mobile

Native Android app for managing shared organizational assets — bookings, maintenance, notifications, and utilization insights.

**Current delivery:** Demo-ready UI build with centralized mock data.  
**Default theme:** Light  
**Not included yet:** Spring Boot APIs, JWT auth, Room, Retrofit, or WorkManager (planned after UI approval).

---

## Stack

- Kotlin
- Jetpack Compose + Material 3
- Navigation Compose
- MVVM-ready package structure
- Mock data under `core/data/mock`

---

## Prerequisites

1. Install [Android Studio](https://developer.android.com/studio)
2. Finish the SDK setup wizard once
3. Create `local.properties`:

```powershell
.\scripts\setup-android.ps1
```

If the SDK lives elsewhere, copy `local.properties.example` to `local.properties` and set `sdk.dir`.

---

## Build and run

PowerShell 5.x:

```powershell
.\gradlew.bat assembleDebug; if ($?) { .\gradlew.bat installDebug }
```

PowerShell 7+:

```powershell
./gradlew assembleDebug && ./gradlew installDebug
```

Or open the project in Android Studio and run the `app` configuration on an emulator or device.

---

## Demo login

1. Open the app → **Welcome / Landing**
2. Tap **Log in** (or **Create account**)
3. Use any of these accounts. Password can be any non-empty value.

| Name | Email | Organization | Role |
|------|-------|--------------|------|
| Bright Bediako | `bright@gmail.com` | CodeCraft LTD | System Admin |
| Nana Ama | `ama@gmail.com` | Takoradi Technical University | Manager |
| Kwesi Boateng | `kwesi@gmail.com` | Takoradi Technical University | Staff |

The login screen is prefilled with Bright’s email. Tap **Log in** to enter the main app (Dashboard). Logout returns to the Landing screen. No real authentication is performed.

---

## What you can click through

1. **Dashboard** — metrics, analytics, quick actions  
2. **Assets** — search, filters, details, book CTA  
3. **Bookings** — history, cancel, create booking, approvals  
4. **Maintenance** — list, details, mark complete  
5. **Notifications** — types, read/unread, mark all read  
6. **Profile** (avatar) — account details, logout  
7. **Settings** — notification/reminder toggles, theme, about  

Optional: Settings → **Demo screen states** previews empty / loading / error / offline on major tabs. Set back to **Normal** before stakeholder demos.

---

## Project docs

Agent and build guidance lives under `context/`:

| File | Purpose |
|------|---------|
| `project-overview.md` | Product scope |
| `architecture.md` | Package structure and boundaries |
| `build-plan.md` | Feature order (01–20 complete) |
| `progress-tracker.md` | Status and next phase |
| `code-standards.md` | Kotlin / Compose conventions |
| `library-docs.md` | Library usage rules |
| `ui-rules.md` / `ui-tokens.md` / `ui-registry.md` | UI system |

Root `AGENTS.md` is the entry point for AI agents.

---

## Status

- [x] Demo-ready UI (features 01–20 + Landing)  
- [ ] Backend wiring (JWT, Retrofit, Spring Boot, PostgreSQL, Room, WorkManager)

Do not start backend work until this UI delivery is approved.
