package com.assetflow.mobile.features.dashboard.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.Percent
import androidx.compose.material.icons.outlined.TrendingUp
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.assetflow.mobile.core.data.mock.MockDataRepository
import com.assetflow.mobile.core.domain.model.DashboardActivity
import com.assetflow.mobile.core.ui.components.AssetFlowButton
import com.assetflow.mobile.core.ui.components.AssetFlowButtonVariant
import com.assetflow.mobile.core.ui.components.AssetFlowCard
import com.assetflow.mobile.core.ui.components.SectionHeader
import com.assetflow.mobile.core.ui.theme.AssetFlowSpacing
import com.assetflow.mobile.core.ui.theme.AssetFlowTheme

@Composable
fun DashboardRoute(
    onViewAssetsClick: () -> Unit,
    onBookAssetClick: () -> Unit,
    onReportMaintenanceClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState = remember {
        DashboardUiState(
            userName = MockDataRepository.currentUser.fullName,
            organizationName = MockDataRepository.organization.name,
            summary = MockDataRepository.dashboardSummary,
            recentActivity = MockDataRepository.recentActivity,
            categoryDistribution = MockDataRepository.categoryDistribution,
            bookingTrend = MockDataRepository.bookingTrend,
            assetAvailabilityBreakdown = MockDataRepository.assetAvailabilityBreakdown,
            maintenanceStatusCounts = MockDataRepository.maintenanceStatusCounts,
        )
    }

    DashboardScreen(
        uiState = uiState,
        onViewAssetsClick = onViewAssetsClick,
        onBookAssetClick = onBookAssetClick,
        onReportMaintenanceClick = onReportMaintenanceClick,
        modifier = modifier,
    )
}

@Composable
fun DashboardScreen(
    uiState: DashboardUiState,
    onViewAssetsClick: () -> Unit,
    onBookAssetClick: () -> Unit,
    onReportMaintenanceClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(AssetFlowSpacing.MarginMobile),
        verticalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Md),
    ) {
        item {
            DashboardGreeting(
                userName = uiState.userName,
                organizationName = uiState.organizationName,
            )
        }

        item {
            SectionHeader(title = "Overview")
        }

        item {
            DashboardMetricsGrid(summary = uiState.summary)
        }

        item {
            UtilizationSummaryCard(utilizationPercent = uiState.summary.utilizationPercent)
        }

        item {
            SectionHeader(title = "Analytics")
        }

        item {
            DashboardAnalyticsSection(
                categoryDistribution = uiState.categoryDistribution,
                bookingTrend = uiState.bookingTrend,
                assetAvailabilityBreakdown = uiState.assetAvailabilityBreakdown,
                maintenanceStatusCounts = uiState.maintenanceStatusCounts,
                totalAssets = uiState.summary.totalAssets,
            )
        }

        item {
            SectionHeader(title = "Quick actions")
        }

        item {
            QuickActionsRow(
                onViewAssetsClick = onViewAssetsClick,
                onBookAssetClick = onBookAssetClick,
                onReportMaintenanceClick = onReportMaintenanceClick,
            )
        }

        item {
            SectionHeader(title = "Recent activity")
        }

        items(
            items = uiState.recentActivity,
            key = { activity -> activity.id },
        ) { activity ->
            RecentActivityCard(activity = activity)
        }
    }
}

@Composable
private fun DashboardGreeting(
    userName: String,
    organizationName: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Hello, $userName",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            text = organizationName,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = AssetFlowSpacing.Xs),
        )
        Text(
            text = "Here is your organization snapshot for today.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = AssetFlowSpacing.Sm),
        )
    }
}

@Composable
private fun DashboardMetricsGrid(
    summary: com.assetflow.mobile.core.domain.model.DashboardSummary,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Sm),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Sm),
        ) {
            DashboardMetricCard(
                label = "Total assets",
                value = summary.totalAssets.toString(),
                icon = Icons.Outlined.Inventory2,
                modifier = Modifier.weight(1f),
            )
            DashboardMetricCard(
                label = "Available",
                value = summary.availableAssets.toString(),
                icon = Icons.Outlined.TrendingUp,
                modifier = Modifier.weight(1f),
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Sm),
        ) {
            DashboardMetricCard(
                label = "Active bookings",
                value = summary.activeBookings.toString(),
                icon = Icons.Outlined.CalendarMonth,
                modifier = Modifier.weight(1f),
            )
            DashboardMetricCard(
                label = "Maintenance due",
                value = summary.maintenanceDue.toString(),
                icon = Icons.Outlined.WarningAmber,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun DashboardMetricCard(
    label: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
) {
    AssetFlowCard(modifier = modifier) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = AssetFlowSpacing.Sm),
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = AssetFlowSpacing.Xs),
        )
    }
}

@Composable
private fun UtilizationSummaryCard(
    utilizationPercent: Int,
    modifier: Modifier = Modifier,
) {
    AssetFlowCard(modifier = modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Sm),
        ) {
            Icon(
                imageVector = Icons.Outlined.Percent,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = "Asset utilization",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
        Text(
            text = "$utilizationPercent% of shared assets are in active use this week.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = AssetFlowSpacing.Sm),
        )
        LinearProgressIndicator(
            progress = { utilizationPercent / 100f },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AssetFlowSpacing.Md),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceContainerHigh,
        )
    }
}

@Composable
private fun QuickActionsRow(
    onViewAssetsClick: () -> Unit,
    onBookAssetClick: () -> Unit,
    onReportMaintenanceClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Sm),
    ) {
        AssetFlowButton(
            text = "View assets",
            onClick = onViewAssetsClick,
            modifier = Modifier.fillMaxWidth(),
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Sm),
        ) {
            AssetFlowButton(
                text = "Book asset",
                onClick = onBookAssetClick,
                modifier = Modifier.weight(1f),
                variant = AssetFlowButtonVariant.Secondary,
            )
            AssetFlowButton(
                text = "Report maintenance",
                onClick = onReportMaintenanceClick,
                modifier = Modifier.weight(1f),
                variant = AssetFlowButtonVariant.Secondary,
            )
        }
    }
}

@Composable
private fun RecentActivityCard(
    activity: DashboardActivity,
    modifier: Modifier = Modifier,
) {
    AssetFlowCard(modifier = modifier.fillMaxWidth()) {
        Text(
            text = activity.message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = activity.timestamp,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = AssetFlowSpacing.Xs),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DashboardScreenPreview() {
    AssetFlowTheme {
        DashboardScreen(
            uiState = DashboardUiState(
                userName = "Ama Mensah",
                organizationName = "Northbridge University",
                summary = MockDataRepository.dashboardSummary,
                recentActivity = MockDataRepository.recentActivity,
                categoryDistribution = MockDataRepository.categoryDistribution,
                bookingTrend = MockDataRepository.bookingTrend,
                assetAvailabilityBreakdown = MockDataRepository.assetAvailabilityBreakdown,
                maintenanceStatusCounts = MockDataRepository.maintenanceStatusCounts,
            ),
            onViewAssetsClick = {},
            onBookAssetClick = {},
            onReportMaintenanceClick = {},
        )
    }
}
