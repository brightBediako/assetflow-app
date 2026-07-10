package com.assetflow.mobile.core.domain.model

enum class OperationalStatus {
    Available,
    Booked,
    MaintenanceDue,
    Unavailable,
    Pending,
    Approved,
    Active,
    Completed,
    Cancelled,
    Overdue,
}

fun OperationalStatus.displayLabel(): String = when (this) {
    OperationalStatus.Available -> "Available"
    OperationalStatus.Booked -> "Booked"
    OperationalStatus.MaintenanceDue -> "Maintenance Due"
    OperationalStatus.Unavailable -> "Unavailable"
    OperationalStatus.Pending -> "Pending"
    OperationalStatus.Approved -> "Approved"
    OperationalStatus.Active -> "Active"
    OperationalStatus.Completed -> "Completed"
    OperationalStatus.Cancelled -> "Cancelled"
    OperationalStatus.Overdue -> "Overdue"
}
