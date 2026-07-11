# UI Rules

Concise rules for building the AssetFlow Mobile UI. The app is a native Android Jetpack Compose product focused on a polished dummy-data prototype before backend wiring.

Read this file before building any screen or shared component.

---

## Product Feel

AssetFlow Mobile should feel:

- Clear
- Practical
- Trustworthy
- Mobile-first
- Fast to scan
- Built for operational work

The UI should help users quickly answer:

- What assets exist?
- Which assets are available?
- Who booked an asset?
- What maintenance is due?
- What action needs attention?

---

## Platform

- Build native Android UI with Jetpack Compose.
- Use Material components as the base.
- Use dummy data until the UI prototype is approved.
- Prefer simple Compose primitives before adding extra UI libraries.
- Do not use web-specific rules, Tailwind classes, React components, or CSS tokens.

---

## Layout

- Use mobile-first vertical layouts.
- Keep screens readable on common Android phone sizes.
- Use `Scaffold` for top-level screen structure.
- Use `LazyColumn` for long vertical content.
- Use `LazyVerticalGrid` only when asset browsing benefits from grid cards.
- Keep section spacing consistent.
- Avoid dense desktop-style tables.

Recommended screen structure:

- Top app bar
- Optional summary or search/filter area
- Main content list/cards
- Primary action button or floating action where appropriate
- Empty/loading/error state when applicable

---

## Navigation

Main authenticated navigation should include:

- Dashboard
- Assets
- Bookings
- Maintenance
- Notifications

Profile and settings should be reachable through an account/avatar entry point.

Rules:

- Use clear route labels.
- Do not hide primary workflows behind deep menus.
- Use bottom navigation for the main mobile sections unless a later design direction changes this.
- Detail screens should show a clear back affordance.
- Primary CTAs should navigate to the next logical workflow.

---

## Top App Bar

Use the top app bar for:

- Current screen title
- Organization name or context when useful
- Back navigation on detail screens
- Profile/avatar access
- Notification shortcut if notifications are not in bottom navigation

Keep titles short:

- Dashboard
- Assets
- Asset Details
- Bookings
- Maintenance
- Notifications
- Profile
- Settings

---

## Cards

Use cards for grouped operational content:

- Dashboard metrics
- Asset rows/cards
- Booking records
- Maintenance records
- Notification items
- Profile sections

Card rules:

- Keep each card focused on one object or one summary.
- Put the most important status near the top or top-right.
- Avoid cramming too many actions into one card.
- Use consistent padding and spacing.
- Prefer white or neutral card surfaces with color used for status and emphasis.

---

## Typography

Use a simple hierarchy:

- Screen title: prominent and short
- Section title: clear grouping label
- Card title: asset name, booking asset, maintenance title, or notification title
- Body text: operational details
- Supporting text: timestamps, IDs, locations, descriptions

Rules:

- Keep labels human readable.
- Use short text on cards.
- Avoid long paragraphs in list items.
- Long descriptions belong on detail screens.

---

## Colors and Status

Use color to communicate operational state.

Suggested meanings:

- Available: green
- Booked or active: blue
- Pending: amber
- Maintenance due: orange
- Overdue or rejected: red
- Completed: neutral or green
- Unavailable or cancelled: gray

Rules:

- Status color must be paired with text, not color alone.
- Keep status labels consistent across screens.
- Do not use many competing accent colors on the same screen.
- Alerts should be visually distinct but not overwhelming.

---

## Status Badges

Use badges for statuses such as:

- Available
- Booked
- Maintenance Due
- Unavailable
- Pending
- Approved
- Active
- Completed
- Cancelled
- Overdue

Badge rules:

- Use short labels.
- Use consistent casing.
- Use pill-shaped or rounded badges.
- Reuse one badge component where possible.

---

## Buttons and Actions

Primary actions:

- Login
- Create Account
- Book Asset
- Submit Booking
- Approve
- Mark Complete

Secondary actions:

- Cancel
- View Details
- View History
- Reject
- Change Filters

Rules:

- One primary action per screen section when possible.
- Use destructive styling only for destructive actions.
- Disabled buttons should clearly look disabled.
- Show success, conflict, loading, and error feedback for important actions, even when simulated with dummy data.

---

## Forms

Forms must be complete visually before real validation is wired.

Rules:

- Every input needs a clear label.
- Required fields should be obvious.
- Use helpful placeholders, not instructions as labels.
- Show error text near the field.
- Keep booking forms short and focused.
- Split long forms into sections.

Important forms:

- Login
- Registration
- Create booking
- Maintenance scheduling or completion
- Settings preferences

---

## Search and Filters

Search and filters are required for asset browsing.

Asset list filters should support:

- Search by name or tag
- Category
- Availability status
- Location if useful

Rules:

- Filters should be visible and easy to reset.
- Use chips, dropdowns, or compact filter rows.
- Do not make filtering depend on backend data during UI-first work.
- Dummy filters can update local UI state.

---

## Dashboard

Dashboard must give users a quick operational overview.

Required dashboard sections:

- Greeting or organization summary
- Total assets
- Available assets
- Active bookings
- Maintenance due
- Utilization summary
- Recent activity
- Quick actions

Rules:

- Metrics should be large and easy to scan.
- Quick actions should navigate to real screens.
- Use simple charts, progress bars, or summaries before adding chart libraries.
- Dashboard data should come from mock dashboard records during UI-first work.

---

## Asset Screens

Asset list cards should show:

- Asset name
- Category
- Location
- Availability badge
- Asset icon or placeholder
- Maintenance hint if relevant

Asset details should show:

- Asset name
- Category
- Availability
- Location
- Asset ID or tag
- Description
- Current booking status
- Maintenance summary
- Book asset CTA when available

Rules:

- Asset availability must be visible without opening details.
- Unavailable actions should be disabled or clearly explained.
- Maintenance-related warnings should be visible but not noisy.

---

## Booking Screens

Booking screens should make status and time clear.

Booking list items should show:

- Asset name
- Location
- Date and time
- Status badge
- Cancel action when allowed

Create booking should show:

- Selected asset summary
- Date
- Start time
- End time
- Purpose
- Notes
- Submit action
- Conflict state
- Success state

Approval UI should show:

- Requester
- Asset
- Date and time
- Purpose
- Approve action
- Reject action

---

## Maintenance Screens

Maintenance screens should make urgency obvious.

Maintenance list items should show:

- Asset name
- Maintenance type
- Due date
- Priority or status
- Assigned person or team

Maintenance details should show:

- Asset summary
- Maintenance title/type
- Status
- Due date
- Description
- History timeline
- Mark complete action
- Schedule action

Rules:

- Overdue items must be visually distinct.
- Maintenance history should be easy to scan.
- Completion can update local UI state during the prototype.

---

## Notifications

Notifications should be grouped by action relevance.

Notification types:

- Booking approval
- Asset return reminder
- Maintenance alert
- System announcement

Rules:

- Show read/unread state.
- Show timestamp.
- Make action-required notifications visually clear.
- Provide an empty state when there are no notifications.

---

## Empty, Loading, and Error States

Every major screen must eventually include:

- Normal state
- Empty state
- Loading state
- Error state

Empty states should include:

- Short title
- Helpful description
- CTA when there is a logical next action

Examples:

- No assets found
- No bookings yet
- No maintenance records
- No notifications
- Could not load data

During UI-first work, these states can be triggered with dummy state toggles or previews.

---

## Accessibility

- Tap targets should be comfortable for mobile use.
- Text should remain readable on small screens.
- Do not rely on color alone for state.
- Icons should have meaningful labels where needed.
- Maintain clear contrast between text and background.

---

## Do Nots

- Do not build desktop-style tables for mobile lists.
- Do not add backend dependencies while building UI-only features.
- Do not create one-off status badge styles per screen.
- Do not use raw backend or exception messages in UI.
- Do not use JobPilot, job-search, resume, or company-research language.
- Do not use Tailwind, React, Next.js, or web layout rules.
- Do not make UI screens depend on live API availability during the dummy-data phase.
