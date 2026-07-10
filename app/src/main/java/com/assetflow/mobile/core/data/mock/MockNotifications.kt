package com.assetflow.mobile.core.data.mock

import com.assetflow.mobile.core.domain.model.AppNotification
import com.assetflow.mobile.core.domain.model.NotificationType

object MockNotifications {
    val notifications = listOf(
        AppNotification(
            id = "notif-001",
            type = NotificationType.BookingApproval,
            title = "Booking approval needed",
            message = "Kwesi Boateng requested the iPad Pro for Jul 15, 09:00 to 12:00.",
            timestamp = "Today, 08:42",
            isRead = false,
            relatedAssetId = "asset-013",
            relatedBookingId = "booking-001",
        ),
        AppNotification(
            id = "notif-002",
            type = NotificationType.ReturnReminder,
            title = "Asset return reminder",
            message = "Nikon Z6 Camera is due back today at 16:00.",
            timestamp = "Today, 07:15",
            isRead = false,
            relatedAssetId = "asset-010",
            relatedBookingId = "booking-004",
        ),
        AppNotification(
            id = "notif-003",
            type = NotificationType.MaintenanceAlert,
            title = "Maintenance due soon",
            message = "Epson PowerLite Projector lamp replacement is due on Jul 14.",
            timestamp = "Yesterday, 17:30",
            isRead = true,
            relatedAssetId = "asset-004",
        ),
        AppNotification(
            id = "notif-004",
            type = NotificationType.MaintenanceAlert,
            title = "Overdue maintenance",
            message = "DeWalt Circular Saw safety inspection is overdue since Jul 5.",
            timestamp = "Yesterday, 09:10",
            isRead = false,
            relatedAssetId = "asset-012",
        ),
        AppNotification(
            id = "notif-005",
            type = NotificationType.BookingApproval,
            title = "Booking approved",
            message = "Your MacBook Pro booking for Jul 12 to 14 was approved.",
            timestamp = "Jul 9, 14:20",
            isRead = true,
            relatedAssetId = "asset-002",
            relatedBookingId = "booking-002",
        ),
        AppNotification(
            id = "notif-006",
            type = NotificationType.SystemAnnouncement,
            title = "Inventory audit week",
            message = "Campus asset audit begins Jul 21. Please return borrowed items on time.",
            timestamp = "Jul 8, 11:00",
            isRead = true,
        ),
        AppNotification(
            id = "notif-007",
            type = NotificationType.ReturnReminder,
            title = "Fleet van return",
            message = "Toyota Hilux Fleet Van should be returned by Jul 11, 18:00.",
            timestamp = "Jul 8, 08:05",
            isRead = true,
            relatedAssetId = "asset-006",
            relatedBookingId = "booking-003",
        ),
        AppNotification(
            id = "notif-008",
            type = NotificationType.BookingApproval,
            title = "Booking approval needed",
            message = "Daniel Osei requested the Shure microphone pack for Jul 18 evening event.",
            timestamp = "Jul 7, 19:45",
            isRead = false,
            relatedAssetId = "asset-008",
            relatedBookingId = "booking-007",
        ),
    )

    fun getNotificationById(notificationId: String): AppNotification? =
        notifications.firstOrNull { it.id == notificationId }

    fun getUnreadCount(): Int = notifications.count { !it.isRead }
}
