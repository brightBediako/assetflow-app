package com.assetflow.mobile.core.domain.model

enum class NotificationType {
    BookingApproval,
    ReturnReminder,
    MaintenanceAlert,
    SystemAnnouncement,
}

fun NotificationType.displayLabel(): String = when (this) {
    NotificationType.BookingApproval -> "Booking Approval"
    NotificationType.ReturnReminder -> "Return Reminder"
    NotificationType.MaintenanceAlert -> "Maintenance Alert"
    NotificationType.SystemAnnouncement -> "System Announcement"
}

data class AppNotification(
    val id: String,
    val type: NotificationType,
    val title: String,
    val message: String,
    val timestamp: String,
    val isRead: Boolean,
    val relatedAssetId: String? = null,
    val relatedBookingId: String? = null,
)
