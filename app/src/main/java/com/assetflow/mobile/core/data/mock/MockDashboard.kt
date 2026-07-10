package com.assetflow.mobile.core.data.mock

import com.assetflow.mobile.core.domain.model.BookingTrendPoint
import com.assetflow.mobile.core.domain.model.CategorySummary
import com.assetflow.mobile.core.domain.model.DashboardActivity
import com.assetflow.mobile.core.domain.model.DashboardSummary
import com.assetflow.mobile.core.domain.model.MaintenanceStatus
import com.assetflow.mobile.core.domain.model.MaintenanceStatusCount
import com.assetflow.mobile.core.domain.model.OperationalStatus
import com.assetflow.mobile.core.domain.model.OperationalStatusCount

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

    val bookingTrend: List<BookingTrendPoint> = listOf(
        BookingTrendPoint(label = "Mon", count = 3),
        BookingTrendPoint(label = "Tue", count = 5),
        BookingTrendPoint(label = "Wed", count = 4),
        BookingTrendPoint(label = "Thu", count = 6),
        BookingTrendPoint(label = "Fri", count = 8),
        BookingTrendPoint(label = "Sat", count = 2),
        BookingTrendPoint(label = "Sun", count = 1),
    )

    val assetAvailabilityBreakdown: List<OperationalStatusCount> by lazy {
        listOf(
            OperationalStatus.Available,
            OperationalStatus.Booked,
            OperationalStatus.MaintenanceDue,
            OperationalStatus.Unavailable,
        ).map { status ->
            OperationalStatusCount(
                status = status,
                count = MockAssets.assets.count { it.status == status },
            )
        }.filter { it.count > 0 }
    }

    val maintenanceStatusCounts: List<MaintenanceStatusCount> by lazy {
        listOf(
            MaintenanceStatus.Overdue,
            MaintenanceStatus.DueSoon,
            MaintenanceStatus.Scheduled,
            MaintenanceStatus.Completed,
        ).map { status ->
            MaintenanceStatusCount(
                status = status,
                count = MockMaintenance.records.count { it.status == status },
            )
        }
    }
}
