package com.assetflow.mobile.core.data.mock

import com.assetflow.mobile.core.domain.model.MaintenanceHistoryEntry
import com.assetflow.mobile.core.domain.model.MaintenanceRecord
import com.assetflow.mobile.core.domain.model.MaintenanceStatus

object MockMaintenance {
    val records = listOf(
        MaintenanceRecord(
            id = "maint-001",
            assetId = "asset-004",
            title = "Projector lamp replacement",
            maintenanceType = "Preventive",
            status = MaintenanceStatus.DueSoon,
            dueDate = "2026-07-14",
            description = "Replace lamp module and clean intake filters before next booking cycle.",
            assignedTo = "Facilities Team",
            priority = "High",
        ),
        MaintenanceRecord(
            id = "maint-002",
            assetId = "asset-012",
            title = "Circular saw safety inspection",
            maintenanceType = "Safety",
            status = MaintenanceStatus.Overdue,
            dueDate = "2026-07-05",
            description = "Inspect blade guard, power trigger, and emergency stop response.",
            assignedTo = "Workshop Supervisor",
            priority = "Critical",
        ),
        MaintenanceRecord(
            id = "maint-003",
            assetId = "asset-007",
            title = "Microscope calibration",
            maintenanceType = "Calibration",
            status = MaintenanceStatus.Scheduled,
            dueDate = "2026-07-22",
            description = "Recalibrate optics after lab relocation and vibration testing.",
            assignedTo = "Science Lab Technician",
            priority = "Medium",
        ),
        MaintenanceRecord(
            id = "maint-004",
            assetId = "asset-001",
            title = "Laptop health check",
            maintenanceType = "Preventive",
            status = MaintenanceStatus.Completed,
            dueDate = "2026-05-10",
            description = "Battery diagnostics, OS updates, and dock port cleaning completed.",
            assignedTo = "IT Support",
            priority = "Low",
        ),
        MaintenanceRecord(
            id = "maint-005",
            assetId = "asset-005",
            title = "Drill battery cycle test",
            maintenanceType = "Preventive",
            status = MaintenanceStatus.Scheduled,
            dueDate = "2026-07-28",
            description = "Test both battery packs and replace worn chuck key.",
            assignedTo = "Facilities Team",
            priority = "Low",
        ),
        MaintenanceRecord(
            id = "maint-006",
            assetId = "asset-014",
            title = "PA amplifier repair",
            maintenanceType = "Corrective",
            status = MaintenanceStatus.Overdue,
            dueDate = "2026-07-01",
            description = "Replace failed amplifier module and verify speaker output balance.",
            assignedTo = "Events AV Team",
            priority = "High",
        ),
        MaintenanceRecord(
            id = "maint-007",
            assetId = "asset-006",
            title = "Fleet van service",
            maintenanceType = "Preventive",
            status = MaintenanceStatus.DueSoon,
            dueDate = "2026-07-16",
            description = "Oil change, tire rotation, and brake inspection before peak term usage.",
            assignedTo = "Transport Office",
            priority = "Medium",
        ),
    )

    val history = listOf(
        MaintenanceHistoryEntry(
            id = "hist-001",
            maintenanceId = "maint-004",
            date = "2026-05-10",
            summary = "Completed laptop health check with no issues found.",
            performedBy = "IT Support",
        ),
        MaintenanceHistoryEntry(
            id = "hist-002",
            maintenanceId = "maint-001",
            date = "2026-03-18",
            summary = "Cleaned projector vents and replaced air filter.",
            performedBy = "Facilities Team",
        ),
        MaintenanceHistoryEntry(
            id = "hist-003",
            maintenanceId = "maint-002",
            date = "2026-02-02",
            summary = "Adjusted blade guard tension and lubricated motor housing.",
            performedBy = "Workshop Supervisor",
        ),
        MaintenanceHistoryEntry(
            id = "hist-004",
            maintenanceId = "maint-003",
            date = "2025-12-12",
            summary = "Annual microscope calibration completed before relocation.",
            performedBy = "Science Lab Technician",
        ),
    )

    fun getMaintenanceById(maintenanceId: String): MaintenanceRecord? =
        records.firstOrNull { it.id == maintenanceId }

    fun getMaintenanceForAsset(assetId: String): List<MaintenanceRecord> =
        records.filter { it.assetId == assetId }

    fun getHistoryForMaintenance(maintenanceId: String): List<MaintenanceHistoryEntry> =
        history.filter { it.maintenanceId == maintenanceId }

    fun getDueSoonRecords(): List<MaintenanceRecord> =
        records.filter { it.status == MaintenanceStatus.DueSoon }

    fun getOverdueRecords(): List<MaintenanceRecord> =
        records.filter { it.status == MaintenanceStatus.Overdue }

    fun getCompletedRecords(): List<MaintenanceRecord> =
        records.filter { it.status == MaintenanceStatus.Completed }
}
