package com.assetflow.mobile.features.settings.presentation

import com.assetflow.mobile.core.domain.model.AppSettings
import com.assetflow.mobile.core.domain.model.ContentLoadState
import com.assetflow.mobile.core.domain.model.ThemePreference

data class SettingsUiState(
    val bookingApprovalNotifications: Boolean,
    val returnReminderNotifications: Boolean,
    val maintenanceAlertNotifications: Boolean,
    val systemAnnouncementNotifications: Boolean,
    val returnDueReminders: Boolean,
    val maintenanceDueReminders: Boolean,
    val bookingStartReminders: Boolean,
    val themePreference: ThemePreference,
    val contentLoadState: ContentLoadState,
    val appVersion: String,
    val aboutDescription: String,
    val supportEmail: String,
)

fun loadSettingsUiState(
    settings: AppSettings,
    contentLoadState: ContentLoadState = ContentLoadState.Normal,
): SettingsUiState {
    return SettingsUiState(
        bookingApprovalNotifications = settings.bookingApprovalNotifications,
        returnReminderNotifications = settings.returnReminderNotifications,
        maintenanceAlertNotifications = settings.maintenanceAlertNotifications,
        systemAnnouncementNotifications = settings.systemAnnouncementNotifications,
        returnDueReminders = settings.returnDueReminders,
        maintenanceDueReminders = settings.maintenanceDueReminders,
        bookingStartReminders = settings.bookingStartReminders,
        themePreference = settings.themePreference,
        contentLoadState = contentLoadState,
        appVersion = "1.0.0",
        aboutDescription = "AssetFlow Mobile helps teams track shared assets, bookings, maintenance, and approvals in one place.",
        supportEmail = "support@assetflow.app",
    )
}
