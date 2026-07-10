package com.assetflow.mobile.core.data.mock

import com.assetflow.mobile.core.domain.model.CategorySummary
import com.assetflow.mobile.core.domain.model.DashboardActivity
import com.assetflow.mobile.core.domain.model.DashboardSummary
import com.assetflow.mobile.core.domain.model.MaintenanceStatus
import com.assetflow.mobile.core.domain.model.OperationalStatus

object MockDashboard {
    val summary: DashboardSummary by lazy {
        val assets = MockAssets.assets
        val bookings = MockBookings.bookings
        val maintenance = MockMaintenance.records

        DashboardSummary(
            totalAssets = assets.size,
            availableAssets = assets.count { it.status == OperationalStatus.Available },
            activeBookings = bookings.count { it.status == OperationalStatus.Active },
            maintenanceDue = maintenance.count {
                it.status == MaintenanceStatus.DueSoon || it.status == MaintenanceStatus.Overdue
            },
            utilizationPercent = 68,
        )
    }

    val recentActivity = listOf(
        DashboardActivity(
            id = "activity-001",
            message = "Kwesi Boateng requested iPad Pro 12.9\" for Jul 15.",
            timestamp = "Today, 08:42",
        ),
        DashboardActivity(
            id = "activity-002",
            message = "Nikon Z6 Camera checked out for graduation photography.",
            timestamp = "Today, 10:05",
        ),
        DashboardActivity(
            id = "activity-003",
            message = "Epson PowerLite Projector flagged for lamp replacement.",
            timestamp = "Yesterday, 17:30",
        ),
        DashboardActivity(
            id = "activity-004",
            message = "Toyota Hilux Fleet Van active booking extended to Jul 11.",
            timestamp = "Yesterday, 15:10",
        ),
        DashboardActivity(
            id = "activity-005",
            message = "Canon EOS R6 booking completed and returned on time.",
            timestamp = "Jul 3, 17:45",
        ),
        DashboardActivity(
            id = "activity-006",
            message = "PA Speaker System marked unavailable pending amplifier repair.",
            timestamp = "Jul 2, 09:20",
        ),
    )

    val categoryDistribution: List<CategorySummary> by lazy {
        MockAssets.assets
            .groupBy { it.category }
            .map { (category, items) ->
                CategorySummary(category = category, assetCount = items.size)
            }
            .sortedByDescending { it.assetCount }
    }

    val bookingTrend = listOf(
        "Mon" to 3,
        "Tue" to 5,
        "Wed" to 4,
        "Thu" to 6,
        "Fri" to 8,
        "Sat" to 2,
        "Sun" to 1,
    )

    val maintenanceStatusSummary by lazy {
        mapOf(
            MaintenanceStatus.Scheduled to MockMaintenance.records.count { it.status == MaintenanceStatus.Scheduled },
            MaintenanceStatus.DueSoon to MockMaintenance.records.count { it.status == MaintenanceStatus.DueSoon },
            MaintenanceStatus.Overdue to MockMaintenance.records.count { it.status == MaintenanceStatus.Overdue },
            MaintenanceStatus.Completed to MockMaintenance.records.count { it.status == MaintenanceStatus.Completed },
        )
    }
}
