# Project Overview

## Product Summary

AssetFlow Mobile is a native Android application for managing, tracking, scheduling, and monitoring shared physical assets. It gives organizations mobile access to asset records, availability, bookings, maintenance information, notifications, and utilization insights.

The app acts as a mobile extension of the AssetFlow platform. Users should be able to perform core asset management tasks from Android devices without needing desktop access.

---

## Problem

Organizations that manage shared resources often rely on manual processes or desktop-only systems. This creates issues such as limited asset visibility, double bookings, weak accountability, poor record keeping, asset loss, and delayed maintenance tracking.

AssetFlow Mobile solves this by making asset information, booking schedules, and maintenance status available from anywhere.

---

## Goal

Build a mobile-based asset management application that helps organizations manage shared assets more efficiently through Android devices.

The project should improve:

- Asset visibility
- Booking coordination
- User accountability
- Maintenance planning
- Remote access to asset information
- Overall resource utilization

---

## Target Users

The system is intended for organizations that manage shared equipment or resources, including:

- Universities and colleges
- Small and medium enterprises
- Equipment rental companies
- Event management organizations
- Churches and NGOs
- Co-working spaces
- Corporate organizations

Example assets include laptops, projectors, cameras, tools, laboratory equipment, vehicles, event materials, and other shared organizational resources.

---

## Core Features

### Authentication and Authorization

- User registration
- User login
- JWT authentication
- Role-based access control

### Asset Management

- View assets
- Search assets
- View asset details
- Check asset availability status

### Booking Management

- Create asset bookings
- View booking history
- Cancel bookings
- Support booking approval workflows
- Prevent or surface booking conflicts

### Maintenance Tracking

- View maintenance records
- Track maintenance history
- Receive scheduled maintenance alerts

### Dashboard and Analytics

- Total assets
- Available assets
- Active bookings
- Maintenance due
- Asset utilization summary

### Notifications

- Booking approval notifications
- Asset return reminders
- Maintenance alerts
- System announcements

---

## Technical Direction

AssetFlow Mobile follows a client-server architecture.

### Mobile Application

- Kotlin
- Jetpack Compose
- MVVM architecture
- Retrofit for API communication
- Room Database for local storage
- WorkManager for background work and notifications

### Backend

- Existing Java Spring Boot backend
- RESTful APIs
- JWT authentication
- Business logic for users, assets, bookings, and maintenance

### Database

- PostgreSQL
- Stores user data, asset records, booking records, maintenance records, and audit information

---

## Scope

The project includes:

- Android mobile application development
- Asset viewing and management
- Booking and scheduling
- Maintenance tracking
- Notifications and reminders
- Mobile dashboard and analytics

The project does not include:

- iOS application development
- Online payment processing
- Asset procurement management
- Advanced AI-based predictions

---

## Expected Outcomes

When complete, AssetFlow Mobile should:

- Improve visibility of organizational assets
- Reduce booking conflicts
- Improve accountability and asset tracking
- Make asset operations accessible on mobile devices
- Reduce asset loss and misuse
- Support maintenance planning
- Improve overall resource utilization

---

## Product Principle

AssetFlow Mobile should remain focused on practical, mobile-first asset operations. Every feature should help users know what assets exist, where they are, whether they are available, who is responsible for them, and what maintenance or booking action is needed next.
