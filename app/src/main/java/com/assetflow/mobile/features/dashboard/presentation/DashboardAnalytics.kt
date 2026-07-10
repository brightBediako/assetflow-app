package com.assetflow.mobile.features.dashboard.presentation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Construction
import androidx.compose.material.icons.outlined.PieChart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.assetflow.mobile.core.domain.model.BookingTrendPoint
import com.assetflow.mobile.core.domain.model.CategorySummary
import com.assetflow.mobile.core.domain.model.MaintenanceStatus
import com.assetflow.mobile.core.domain.model.MaintenanceStatusCount
import com.assetflow.mobile.core.domain.model.OperationalStatus
import com.assetflow.mobile.core.domain.model.OperationalStatusCount
import com.assetflow.mobile.core.domain.model.displayLabel
import com.assetflow.mobile.core.domain.model.toOperationalStatus
import com.assetflow.mobile.core.ui.components.AnalyticsBarRow
import com.assetflow.mobile.core.ui.components.AnalyticsColumnChart
import com.assetflow.mobile.core.ui.components.AssetFlowCard
import com.assetflow.mobile.core.ui.components.EmptyState
import com.assetflow.mobile.core.ui.theme.AssetFlowSpacing
import com.assetflow.mobile.core.ui.theme.AssetFlowStatusColors

@Composable
fun DashboardAnalyticsSection(
    categoryDistribution: List<CategorySummary>,
    bookingTrend: List<BookingTrendPoint>,
    assetAvailabilityBreakdown: List<OperationalStatusCount>,
    maintenanceStatusCounts: List<MaintenanceStatusCount>,
    totalAssets: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Sm),
    ) {
        AssetAvailabilityCard(
            breakdown = assetAvailabilityBreakdown,
            totalAssets = totalAssets,
        )
        CategoryDistributionCard(
            categories = categoryDistribution,
            totalAssets = totalAssets,
        )
        BookingTrendCard(points = bookingTrend)
        MaintenanceStatusCard(statusCounts = maintenanceStatusCounts)
    }
}

@Composable
private fun AnalyticsCardHeader(
    title: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Sm),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
private fun AssetAvailabilityCard(
    breakdown: List<OperationalStatusCount>,
    totalAssets: Int,
    modifier: Modifier = Modifier,
) {
    AssetFlowCard(modifier = modifier.fillMaxWidth()) {
        AnalyticsCardHeader(
            title = "Asset availability",
            icon = Icons.Outlined.PieChart,
        )
        Text(
            text = "How shared assets are allocated right now.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = AssetFlowSpacing.Sm),
        )
        if (breakdown.isEmpty()) {
            EmptyState(
                title = "No assets tracked",
                description = "Availability breakdown appears once assets are added.",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = AssetFlowSpacing.Md),
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = AssetFlowSpacing.Md),
                verticalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Sm),
            ) {
                breakdown.forEach { item ->
                    AnalyticsBarRow(
                        label = item.status.displayLabel(),
                        value = item.count,
                        maxValue = totalAssets,
                        barColor = operationalStatusColor(item.status),
                        valueLabel = item.count.toString(),
                    )
                }
            }
        }
    }
}

@Composable
private fun CategoryDistributionCard(
    categories: List<CategorySummary>,
    totalAssets: Int,
    modifier: Modifier = Modifier,
) {
    val maxCount = categories.maxOfOrNull { it.assetCount } ?: 0

    AssetFlowCard(modifier = modifier.fillMaxWidth()) {
        AnalyticsCardHeader(
            title = "Category distribution",
            icon = Icons.Outlined.Category,
        )
        Text(
            text = "Asset count by category across the organization.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = AssetFlowSpacing.Sm),
        )
        if (categories.isEmpty()) {
            EmptyState(
                title = "No categories yet",
                description = "Category totals will appear when assets are grouped.",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = AssetFlowSpacing.Md),
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = AssetFlowSpacing.Md),
                verticalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Sm),
            ) {
                categories.forEachIndexed { index, category ->
                    AnalyticsBarRow(
                        label = category.category,
                        value = category.assetCount,
                        maxValue = maxCount.coerceAtLeast(1),
                        barColor = categoryBarColor(index),
                        valueLabel = "${category.assetCount}",
                    )
                }
            }
            Text(
                text = "$totalAssets assets across ${categories.size} categories",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = AssetFlowSpacing.Sm),
            )
        }
    }
}

@Composable
private fun BookingTrendCard(
    points: List<BookingTrendPoint>,
    modifier: Modifier = Modifier,
) {
    val weeklyTotal = points.sumOf { it.count }

    AssetFlowCard(modifier = modifier.fillMaxWidth()) {
        AnalyticsCardHeader(
            title = "Booking trend",
            icon = Icons.Outlined.BarChart,
        )
        Text(
            text = "Bookings started per day this week.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = AssetFlowSpacing.Sm),
        )
        AnalyticsColumnChart(
            points = points,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AssetFlowSpacing.Md),
        )
        if (points.isNotEmpty()) {
            Text(
                text = "$weeklyTotal bookings started this week",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = AssetFlowSpacing.Sm),
            )
        }
    }
}

@Composable
private fun MaintenanceStatusCard(
    statusCounts: List<MaintenanceStatusCount>,
    modifier: Modifier = Modifier,
) {
    val maxCount = statusCounts.maxOfOrNull { it.count }?.coerceAtLeast(1) ?: 1
    val openCount = statusCounts
        .filter { it.status != MaintenanceStatus.Completed }
        .sumOf { it.count }

    AssetFlowCard(modifier = modifier.fillMaxWidth()) {
        AnalyticsCardHeader(
            title = "Maintenance status",
            icon = Icons.Outlined.Construction,
        )
        Text(
            text = "Open and completed maintenance across tracked assets.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = AssetFlowSpacing.Sm),
        )
        if (statusCounts.all { it.count == 0 }) {
            EmptyState(
                title = "No maintenance records",
                description = "Maintenance summaries appear once work orders are logged.",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = AssetFlowSpacing.Md),
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = AssetFlowSpacing.Md),
                verticalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Sm),
            ) {
                statusCounts.forEach { item ->
                    AnalyticsBarRow(
                        label = item.status.displayLabel(),
                        value = item.count,
                        maxValue = maxCount,
                        barColor = maintenanceStatusColor(item.status),
                        valueLabel = item.count.toString(),
                    )
                }
            }
            Text(
                text = "$openCount open maintenance items",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = AssetFlowSpacing.Sm),
            )
        }
    }
}

@Composable
private fun categoryBarColor(index: Int): Color {
    val colors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.tertiary,
        MaterialTheme.colorScheme.primaryContainer,
        MaterialTheme.colorScheme.secondaryContainer,
        MaterialTheme.colorScheme.tertiaryContainer,
    )
    return colors[index % colors.size]
}

private fun operationalStatusColor(status: OperationalStatus): Color = when (status) {
    OperationalStatus.Available -> AssetFlowStatusColors.Available
    OperationalStatus.Booked -> AssetFlowStatusColors.Booked
    OperationalStatus.MaintenanceDue -> AssetFlowStatusColors.MaintenanceDue
    OperationalStatus.Unavailable -> AssetFlowStatusColors.Unavailable
    OperationalStatus.Pending -> AssetFlowStatusColors.Pending
    OperationalStatus.Approved -> AssetFlowStatusColors.Approved
    OperationalStatus.Active -> AssetFlowStatusColors.Active
    OperationalStatus.Completed -> AssetFlowStatusColors.Completed
    OperationalStatus.Cancelled -> AssetFlowStatusColors.Cancelled
    OperationalStatus.Overdue -> AssetFlowStatusColors.Overdue
}

private fun maintenanceStatusColor(status: MaintenanceStatus): Color =
    operationalStatusColor(status.toOperationalStatus())
