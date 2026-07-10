package com.assetflow.mobile.features.maintenance.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.assetflow.mobile.core.data.mock.MockMaintenanceStore
import com.assetflow.mobile.core.ui.components.AssetFlowCard
import com.assetflow.mobile.core.ui.components.EmptyState
import com.assetflow.mobile.core.ui.components.MaintenanceCard
import com.assetflow.mobile.core.ui.components.SectionHeader
import com.assetflow.mobile.core.ui.theme.AssetFlowSpacing
import com.assetflow.mobile.core.ui.theme.AssetFlowTheme

@Composable
fun MaintenanceListRoute(
    onMaintenanceClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val records by MockMaintenanceStore::records
    val uiState = remember(records) { loadMaintenanceListUiState() }

    MaintenanceListScreen(
        uiState = uiState,
        onMaintenanceClick = onMaintenanceClick,
        modifier = modifier,
    )
}

@Composable
fun MaintenanceListScreen(
    uiState: MaintenanceListUiState,
    onMaintenanceClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isEmpty = uiState.overdueItems.isEmpty() &&
        uiState.dueSoonItems.isEmpty() &&
        uiState.scheduledItems.isEmpty() &&
        uiState.completedItems.isEmpty()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = AssetFlowSpacing.MarginMobile),
        contentPadding = PaddingValues(vertical = AssetFlowSpacing.MarginMobile),
        verticalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Md),
    ) {
        item {
            MaintenanceSummaryCard(openCount = uiState.totalOpenItems)
        }

        if (isEmpty) {
            item {
                EmptyState(
                    title = "No maintenance records",
                    description = "Scheduled and completed maintenance work will appear here.",
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        } else {
            maintenanceSection(
                title = "Overdue",
                description = "Requires immediate attention.",
                items = uiState.overdueItems,
                emptyMessage = "No overdue maintenance right now.",
                onMaintenanceClick = onMaintenanceClick,
            )
            maintenanceSection(
                title = "Due soon",
                description = "Plan work before these assets are needed.",
                items = uiState.dueSoonItems,
                emptyMessage = "No maintenance due in the near term.",
                onMaintenanceClick = onMaintenanceClick,
            )
            maintenanceSection(
                title = "Scheduled",
                description = "Upcoming maintenance already on the calendar.",
                items = uiState.scheduledItems,
                emptyMessage = "No scheduled maintenance yet.",
                onMaintenanceClick = onMaintenanceClick,
            )
            maintenanceSection(
                title = "Completed",
                description = "Recently finished maintenance work.",
                items = uiState.completedItems,
                emptyMessage = "No completed maintenance records yet.",
                onMaintenanceClick = onMaintenanceClick,
            )
        }
    }
}

private fun androidx.compose.foundation.lazy.LazyListScope.maintenanceSection(
    title: String,
    description: String,
    items: List<MaintenanceListItem>,
    emptyMessage: String,
    onMaintenanceClick: (String) -> Unit,
) {
    item {
        SectionHeader(title = title)
        Text(
            text = description,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }

    if (items.isEmpty()) {
        item(key = "$title-empty") {
            Text(
                text = emptyMessage,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = AssetFlowSpacing.Sm),
            )
        }
    } else {
        items(
            items = items,
            key = { item -> "${title}-${item.record.id}" },
        ) { item ->
            MaintenanceCard(
                assetName = item.assetName,
                assetLocation = item.assetLocation,
                record = item.record,
                onClick = { onMaintenanceClick(item.record.id) },
            )
        }
    }
}

@Composable
private fun MaintenanceSummaryCard(
    openCount: Int,
    modifier: Modifier = Modifier,
) {
    AssetFlowCard(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "$openCount open maintenance items",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = "Review overdue and due soon work first to reduce operational risk.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = AssetFlowSpacing.Xs),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MaintenanceListScreenPreview() {
    AssetFlowTheme {
        MaintenanceListScreen(
            uiState = loadMaintenanceListUiState(),
            onMaintenanceClick = {},
        )
    }
}
