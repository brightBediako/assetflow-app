package com.assetflow.mobile.core.data.mock

import com.assetflow.mobile.core.domain.model.AppSettings
import com.assetflow.mobile.core.domain.model.ThemePreference

object MockSettings {
    val default = AppSettings(
        bookingApprovalNotifications = true,
        returnReminderNotifications = true,
        maintenanceAlertNotifications = true,
        systemAnnouncementNotifications = false,
        returnDueReminders = true,
        maintenanceDueReminders = true,
        bookingStartReminders = false,
        themePreference = ThemePreference.Light,
    )
}
