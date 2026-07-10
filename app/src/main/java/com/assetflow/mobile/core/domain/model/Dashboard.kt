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
