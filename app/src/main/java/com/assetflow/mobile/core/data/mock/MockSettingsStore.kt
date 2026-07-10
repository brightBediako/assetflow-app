package com.assetflow.mobile.core.data.mock

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.assetflow.mobile.core.domain.model.AppSettings
import com.assetflow.mobile.core.domain.model.ThemePreference

/**
 * Mutable app settings for UI prototype interactions. No persistence yet.
 */
object MockSettingsStore {
    var settings by mutableStateOf(MockSettings.default)
        private set

    fun updateSettings(transform: (AppSettings) -> AppSettings) {
        settings = transform(settings)
    }

    fun setBookingApprovalNotifications(enabled: Boolean) {
        updateSettings { it.copy(bookingApprovalNotifications = enabled) }
    }

    fun setReturnReminderNotifications(enabled: Boolean) {
        updateSettings { it.copy(returnReminderNotifications = enabled) }
    }

    fun setMaintenanceAlertNotifications(enabled: Boolean) {
        updateSettings { it.copy(maintenanceAlertNotifications = enabled) }
    }

    fun setSystemAnnouncementNotifications(enabled: Boolean) {
        updateSettings { it.copy(systemAnnouncementNotifications = enabled) }
    }

    fun setReturnDueReminders(enabled: Boolean) {
        updateSettings { it.copy(returnDueReminders = enabled) }
    }

    fun setMaintenanceDueReminders(enabled: Boolean) {
        updateSettings { it.copy(maintenanceDueReminders = enabled) }
    }

    fun setBookingStartReminders(enabled: Boolean) {
        updateSettings { it.copy(bookingStartReminders = enabled) }
    }

    fun setThemePreference(preference: ThemePreference) {
        updateSettings { it.copy(themePreference = preference) }
    }
}
