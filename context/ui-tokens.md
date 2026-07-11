---
name: AssetFlow Mobile
colors:
  surface: '#fbf9f8'
  surface-dim: '#dbdad9'
  surface-bright: '#fbf9f8'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#f5f3f3'
  surface-container: '#efeded'
  surface-container-high: '#e9e8e7'
  surface-container-highest: '#e4e2e2'
  on-surface: '#1b1c1c'
  on-surface-variant: '#414752'
  inverse-surface: '#303030'
  inverse-on-surface: '#f2f0f0'
  outline: '#717783'
  outline-variant: '#c1c6d4'
  surface-tint: '#005faf'
  primary: '#005dac'
  on-primary: '#ffffff'
  primary-container: '#1976d2'
  on-primary-container: '#fffdff'
  inverse-primary: '#a5c8ff'
  secondary: '#456080'
  on-secondary: '#ffffff'
  secondary-container: '#bed9ff'
  on-secondary-container: '#445f7f'
  tertiary: '#5e5878'
  on-tertiary: '#ffffff'
  tertiary-container: '#777192'
  on-tertiary-container: '#fffdff'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#d4e3ff'
  primary-fixed-dim: '#a5c8ff'
  on-primary-fixed: '#001c3a'
  on-primary-fixed-variant: '#004786'
  secondary-fixed: '#d1e4ff'
  secondary-fixed-dim: '#adc9ed'
  on-secondary-fixed: '#001d36'
  on-secondary-fixed-variant: '#2d4867'
  tertiary-fixed: '#e6deff'
  tertiary-fixed-dim: '#cac2e6'
  on-tertiary-fixed: '#1c1833'
  on-tertiary-fixed-variant: '#484361'
  background: '#fbf9f8'
  on-background: '#1b1c1c'
  surface-variant: '#e4e2e2'
typography:
  display-lg:
    fontFamily: Roboto Flex
    fontSize: 57px
    fontWeight: '400'
    lineHeight: 64px
    letterSpacing: -0.25px
  headline-lg:
    fontFamily: Roboto Flex
    fontSize: 32px
    fontWeight: '400'
    lineHeight: 40px
    letterSpacing: 0px
  headline-lg-mobile:
    fontFamily: Roboto Flex
    fontSize: 28px
    fontWeight: '400'
    lineHeight: 36px
    letterSpacing: 0px
  title-lg:
    fontFamily: Roboto Flex
    fontSize: 22px
    fontWeight: '400'
    lineHeight: 28px
    letterSpacing: 0px
  title-md:
    fontFamily: Roboto Flex
    fontSize: 16px
    fontWeight: '500'
    lineHeight: 24px
    letterSpacing: 0.15px
  body-lg:
    fontFamily: Roboto Flex
    fontSize: 16px
    fontWeight: '400'
    lineHeight: 24px
    letterSpacing: 0.5px
  body-md:
    fontFamily: Roboto Flex
    fontSize: 14px
    fontWeight: '400'
    lineHeight: 20px
    letterSpacing: 0.25px
  label-lg:
    fontFamily: Roboto Flex
    fontSize: 14px
    fontWeight: '500'
    lineHeight: 20px
    letterSpacing: 0.1px
  label-sm:
    fontFamily: Roboto Flex
    fontSize: 11px
    fontWeight: '500'
    lineHeight: 16px
    letterSpacing: 0.5px
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  base: 4px
  xs: 4px
  sm: 8px
  md: 16px
  lg: 24px
  xl: 32px
  gutter: 16px
  margin-mobile: 16px
  margin-tablet: 24px
---

## Brand & Style

The design system for this product is a rigorous implementation of Material Design 3 (M3), tailored for high-utility asset management. The brand personality is professional, reliable, and systematic, prioritizing speed of information retrieval and data integrity over decorative flair.

The design style follows a **Corporate / Modern** approach with a "Utility-First" overlay. This means maximizing information density without sacrificing touch targets or clarity. The interface uses a clean, structured layout that feels native to the Android ecosystem while providing the specific affordances needed for managing physical and digital inventories.

Key attributes:
- **Professionalism:** A serious, neutral tone that fits within enterprise environments.
- **Utility:** High-density lists and data grids that minimize vertical scrolling.
- **Clarity:** Explicit status indicators using standardized color coding to signal system health at a glance.

## Colors

The palette utilizes a core "Calm Professional Blue" to establish trust and focus. The system relies heavily on MD3's tonal palettes to generate accessible contrast ratios for both light and dark modes.

- **Primary (#1976D2):** Used for key action buttons, active navigation states, and branding.
- **Status Colors:** These are functional semantic tokens.
    - **Green (Success/Available):** Used for asset status "Ready" or "Available."
    - **Amber (Warning/Maintenance):** Used for "Maintenance Due" or "Low Stock."
    - **Red (Error/Conflict):** Used for "Rejected," "Conflict," or "Overdue."
    - **Grey (Neutral/Unavailable):** Used for "Cancelled," "Archived," or "Missing."

In dark mode, these colors shift to their 200-300 tonal equivalents to maintain legibility against dark surfaces and prevent eye strain during prolonged use.

## Typography

This design system utilizes **Roboto Flex** for its exceptional versatility across a wide range of weights and widths. As an Android-first application, it maintains the platform's native feel while providing the variable font capabilities necessary for high-density data tables.

- **Headlines:** Reserved for page titles and major section headers.
- **Titles:** Used for asset names within cards and list items.
- **Body:** Optimized for descriptions and technical specifications.
- **Labels:** Used for tags, badges, and button text. In asset management, Labels are critical for metadata (e.g., Serial Numbers, SKU IDs).

For mobile screens, headlines scale down to ensure they do not wrap excessively, maintaining a clean vertical rhythm.

## Layout & Spacing

The layout is built on a **Fluid Grid** system following MD3 specifications. For mobile devices, a 4-column grid is used, expanding to 8 or 12 columns for tablets and foldables.

- **Density:** While MD3 defaults to generous spacing, this system implements "High Density" variants for list items and data tables, reducing vertical padding to `8px` between items to increase visible assets per screen.
- **Gutter & Margins:** A consistent `16px` margin is maintained on mobile edges.
- **Touch Targets:** Despite high density, all interactive elements maintain a minimum `48x48dp` touch area to ensure accessibility in field environments.

## Elevation & Depth

Elevation is communicated through **Tonal Layers** rather than heavy drop shadows, consistent with the Material 3 "Flat Plus" aesthetic. 

- **Level 0 (Surface):** The lowest layer, typically the background color of the app.
- **Level 1 (Elevated Cards/Search):** Uses a subtle primary-tinted overlay. This is the default state for asset cards in a list.
- **Level 2 (Navigation Bars):** Surface tint is increased to create clear separation from scrolling content.
- **Level 3 (FABs/Dialogs):** Used for the Floating Action Button (FAB) to ensure it sits prominently above the data.

Shadows, when used, are soft and diffused (ambient), acting as a secondary cue to the color-based elevation.

## Shapes

The shape language follows the standard MD3 rounding scale to maintain platform consistency.

- **Small Components (Buttons, Text Fields):** `8px` to `12px` (Soft).
- **Medium Components (Cards, Menus):** `12px` to `16px` (Rounded).
- **Large Components (FABs, Bottom Sheets):** `28px` or fully pill-shaped.

Rounded corners are used to soften the "industrial" feel of the asset data, making the app feel modern and approachable without losing its professional edge.

## Components

### Navigation
- **Top App Bar:** Center-aligned or small variant. Contains the page title and context actions (e.g., Export, Filter).
- **Bottom Navigation:** Uses five destinations: Home, Assets, Bookings, Notifications, Profile. Active states use a pill-shaped container around the icon.

### Interactive Elements
- **FAB:** The "Large FAB" variant is preferred for primary actions like "Add Asset" or "Scan QR Code." It resides in the bottom right corner.
- **Buttons:** Filled buttons for primary actions; Outlined buttons for secondary actions (e.g., "Edit Details").
- **Chips:** Used for filtering asset categories (e.g., "Electronics," "Furniture"). Filter chips include a checkmark icon when active.

### Data Display
- **Cards:** Elevated cards are the primary container for asset snapshots. They include a small thumbnail, title, and a colored status badge in the top right.
- **Badges:** Small, circular badges for notifications; label-style badges for asset status.
- **Search Bars:** Persistent at the top of list views with a rounded corner radius of 28dp.

### Input
- **Text Fields:** Outlined variants are used to provide better structure in dense forms. 
- **Segmented Tabs:** Used for switching views (e.g., "List View" vs "Map View").