package com.assetflow.mobile.core.domain.model

data class DashboardSummary(
    val totalAssets: Int,
    val availableAssets: Int,
    val activeBookings: Int,
    val maintenanceDue: Int,
    val utilizationPercent: Int,
)

data class DashboardActivity(
    val id: String,
    val message: String,
    val timestamp: String,
)

data class CategorySummary(
    val category: String,
    val assetCount: Int,
)

data class BookingTrendPoint(
    val label: String,
    val count: Int,
)

data class MaintenanceStatusCount(
    val status: MaintenanceStatus,
    val count: Int,
)

data class OperationalStatusCount(
    val status: OperationalStatus,
    val count: Int,
)
