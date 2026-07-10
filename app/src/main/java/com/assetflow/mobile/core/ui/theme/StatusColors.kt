package com.assetflow.mobile.core.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Semantic status colors for operational states across assets, bookings, and maintenance.
 * Always pair with a text label — never rely on color alone.
 */
object AssetFlowStatusColors {
    val Available = Color(0xFF2E7D32)
    val OnAvailable = Color(0xFFFFFFFF)
    val AvailableContainer = Color(0xFFC8E6C9)

    val Booked = Color(0xFF1976D2)
    val OnBooked = Color(0xFFFFFFFF)
    val BookedContainer = Color(0xFFBBDEFB)

    val Active = Color(0xFF1565C0)
    val OnActive = Color(0xFFFFFFFF)
    val ActiveContainer = Color(0xFFB3E5FC)

    val Pending = Color(0xFFF9A825)
    val OnPending = Color(0xFF1B1C1C)
    val PendingContainer = Color(0xFFFFF3C4)

    val Approved = Color(0xFF388E3C)
    val OnApproved = Color(0xFFFFFFFF)
    val ApprovedContainer = Color(0xFFC8E6C9)

    val MaintenanceDue = Color(0xFFEF6C00)
    val OnMaintenanceDue = Color(0xFFFFFFFF)
    val MaintenanceDueContainer = Color(0xFFFFE0B2)

    val Overdue = Color(0xFFC62828)
    val OnOverdue = Color(0xFFFFFFFF)
    val OverdueContainer = Color(0xFFFFCDD2)

    val Completed = Color(0xFF546E7A)
    val OnCompleted = Color(0xFFFFFFFF)
    val CompletedContainer = Color(0xFFCFD8DC)

    val Unavailable = Color(0xFF757575)
    val OnUnavailable = Color(0xFFFFFFFF)
    val UnavailableContainer = Color(0xFFE0E0E0)

    val Cancelled = Color(0xFF9E9E9E)
    val OnCancelled = Color(0xFFFFFFFF)
    val CancelledContainer = Color(0xFFEEEEEE)
}
