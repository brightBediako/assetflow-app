package com.assetflow.mobile.features.dashboard.presentation

import com.assetflow.mobile.core.domain.model.BookingTrendPoint
import com.assetflow.mobile.core.domain.model.CategorySummary
import com.assetflow.mobile.core.domain.model.DashboardActivity
import com.assetflow.mobile.core.domain.model.DashboardSummary
import com.assetflow.mobile.core.domain.model.MaintenanceStatusCount
import com.assetflow.mobile.core.domain.model.OperationalStatusCount

data class DashboardUiState(
    val userName: String,
    val organizationName: String,
    val summary: DashboardSummary,
    val recentActivity: List<DashboardActivity>,
    val categoryDistribution: List<CategorySummary>,
    val bookingTrend: List<BookingTrendPoint>,
    val assetAvailabilityBreakdown: List<OperationalStatusCount>,
    val maintenanceStatusCounts: List<MaintenanceStatusCount>,
)
