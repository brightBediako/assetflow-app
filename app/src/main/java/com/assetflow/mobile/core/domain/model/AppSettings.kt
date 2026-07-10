package com.assetflow.mobile.core.domain.model

enum class ThemePreference {
    System,
    Light,
    Dark,
}

fun ThemePreference.displayLabel(): String = when (this) {
    ThemePreference.System -> "System default"
    ThemePreference.Light -> "Light"
    ThemePreference.Dark -> "Dark"
}

data class AppSettings(
    val bookingApprovalNotifications: Boolean,
    val returnReminderNotifications: Boolean,
    val maintenanceAlertNotifications: Boolean,
    val systemAnnouncementNotifications: Boolean,
    val returnDueReminders: Boolean,
    val maintenanceDueReminders: Boolean,
    val bookingStartReminders: Boolean,
    val themePreference: ThemePreference,
)
