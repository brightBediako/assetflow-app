package com.assetflow.mobile.features.notifications.presentation

import com.assetflow.mobile.core.data.mock.MockDataRepository
import com.assetflow.mobile.core.domain.model.AppNotification
import com.assetflow.mobile.core.domain.model.NotificationType

data class NotificationsUiState(
    val bookingApprovalNotifications: List<AppNotification>,
    val returnReminderNotifications: List<AppNotification>,
    val maintenanceAlertNotifications: List<AppNotification>,
    val systemAnnouncementNotifications: List<AppNotification>,
    val unreadCount: Int,
) {
    val totalCount: Int =
        bookingApprovalNotifications.size +
            returnReminderNotifications.size +
            maintenanceAlertNotifications.size +
            systemAnnouncementNotifications.size
}

fun loadNotificationsUiState(): NotificationsUiState {
    val notifications = MockDataRepository.notifications

    return NotificationsUiState(
        bookingApprovalNotifications = notifications.filter { it.type == NotificationType.BookingApproval },
        returnReminderNotifications = notifications.filter { it.type == NotificationType.ReturnReminder },
        maintenanceAlertNotifications = notifications.filter { it.type == NotificationType.MaintenanceAlert },
        systemAnnouncementNotifications = notifications.filter { it.type == NotificationType.SystemAnnouncement },
        unreadCount = MockDataRepository.getUnreadNotificationCount(),
    )
}
