package com.assetflow.mobile.core.domain.model

enum class MaintenanceStatus {
    Scheduled,
    DueSoon,
    Overdue,
    Completed,
}

fun MaintenanceStatus.displayLabel(): String = when (this) {
    MaintenanceStatus.Scheduled -> "Scheduled"
    MaintenanceStatus.DueSoon -> "Due Soon"
    MaintenanceStatus.Overdue -> "Overdue"
    MaintenanceStatus.Completed -> "Completed"
}

fun MaintenanceStatus.toOperationalStatus(): OperationalStatus = when (this) {
    MaintenanceStatus.Scheduled -> OperationalStatus.Pending
    MaintenanceStatus.DueSoon -> OperationalStatus.MaintenanceDue
    MaintenanceStatus.Overdue -> OperationalStatus.Overdue
    MaintenanceStatus.Completed -> OperationalStatus.Completed
}

data class MaintenanceRecord(
    val id: String,
    val assetId: String,
    val title: String,
    val maintenanceType: String,
    val status: MaintenanceStatus,
    val dueDate: String,
    val description: String,
    val assignedTo: String,
    val priority: String,
)

data class MaintenanceHistoryEntry(
    val id: String,
    val maintenanceId: String,
    val date: String,
    val summary: String,
    val performedBy: String,
)
