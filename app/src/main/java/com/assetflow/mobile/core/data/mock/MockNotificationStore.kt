package com.assetflow.mobile.core.data.mock

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.assetflow.mobile.core.domain.model.AppNotification

/**
 * Mutable notification store for UI prototype interactions (mark read).
 */
object MockNotificationStore {
    var notifications by mutableStateOf(MockNotifications.notifications)
        private set

    fun markAsRead(notificationId: String) {
        notifications = notifications.map { notification ->
            if (notification.id == notificationId) {
                notification.copy(isRead = true)
            } else {
                notification
            }
        }
    }

    fun markAllAsRead() {
        notifications = notifications.map { it.copy(isRead = true) }
    }

    fun getUnreadCount(): Int = notifications.count { !it.isRead }
}
