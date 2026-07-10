package com.assetflow.mobile.features.maintenance.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.assetflow.mobile.core.data.mock.MockDataRepository
import com.assetflow.mobile.core.data.mock.MockMaintenanceStore
import com.assetflow.mobile.core.domain.model.Asset
import com.assetflow.mobile.core.domain.model.MaintenanceHistoryEntry
import com.assetflow.mobile.core.domain.model.MaintenanceStatus
import com.assetflow.mobile.core.domain.model.toOperationalStatus
import com.assetflow.mobile.core.ui.components.AssetCategoryIcon
import com.assetflow.mobile.core.ui.components.AssetFlowButton
import com.assetflow.mobile.core.ui.components.AssetFlowButtonVariant
import com.assetflow.mobile.core.ui.components.AssetFlowCard
import com.assetflow.mobile.core.ui.components.EmptyState
import com.assetflow.mobile.core.ui.components.SectionHeader
import com.assetflow.mobile.core.ui.components.StatusBadge
import com.assetflow.mobile.core.ui.theme.AssetFlowSpacing
import com.assetflow.mobile.core.ui.theme.AssetFlowTheme
import kotlinx.coroutines.launch

@Composable
fun MaintenanceDetailsRoute(
    maintenanceId: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val records by MockMaintenanceStore::records
    val history by MockMaintenanceStore::history
    val uiState = remember(maintenanceId, records, history) {
        loadMaintenanceDetailsUiState(maintenanceId)
    }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var scheduleMessageShown by remember { mutableStateOf(false) }

    if (uiState == null) {
        EmptyState(
            title = "Maintenance not found",
            description = "This record may have been removed or the link is invalid.",
            actionLabel = "Go back",
            onAction = onBackClick,
            modifier = modifier
                .fillMaxSize()
                .padding(AssetFlowSpacing.MarginMobile),
        )
        return
    }

    androidx.compose.material3.Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { paddingValues ->
        MaintenanceDetailsScreen(
            uiState = uiState,
            onMarkCompleteClick = {
                MockMaintenanceStore.markComplete(maintenanceId)
            },
            onScheduleMaintenanceClick = {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Maintenance scheduling will connect after backend integration.",
                    )
                }
                scheduleMessageShown = true
            },
            showScheduleConfirmation = scheduleMessageShown,
            modifier = Modifier.padding(paddingValues),
        )
    }
}

@Composable
fun MaintenanceDetailsScreen(
    uiState: MaintenanceDetailsUiState,
    onMarkCompleteClick: () -> Unit,
    onScheduleMaintenanceClick: () -> Unit,
    modifier: Modifier = Modifier,
    showScheduleConfirmation: Boolean = false,
) {
    val record = uiState.record

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = AssetFlowSpacing.MarginMobile),
        contentPadding = PaddingValues(vertical = AssetFlowSpacing.MarginMobile),
        verticalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Md),
    ) {
        item {
            MaintenanceDetailsHeader(
                title = record.title,
                maintenanceType = record.maintenanceType,
                status = record.status,
            )
        }

        item {
            SectionHeader(title = "Asset")
        }

        item {
            if (uiState.asset != null) {
                AssetSummaryCard(asset = uiState.asset)
            } else {
                AssetFlowCard {
                    Text(
                        text = "Linked asset unavailable",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }

        item {
            SectionHeader(title = "Work order")
        }

        item {
            AssetFlowCard {
                DetailLine(label = "Due date", value = record.dueDate)
                DetailLine(label = "Priority", value = record.priority)
                DetailLine(label = "Assigned to", value = record.assignedTo)
                Text(
                    text = record.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(top = AssetFlowSpacing.Sm),
                )
            }
        }

        item {
            SectionHeader(title = "Maintenance history")
        }

        if (uiState.historyEntries.isEmpty()) {
            item {
                AssetFlowCard {
                    Text(
                        text = "No history entries yet",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        } else {
            items(
                items = uiState.historyEntries,
                key = { entry -> entry.id },
            ) { entry ->
                HistoryTimelineItem(
                    entry = entry,
                    isLast = entry == uiState.historyEntries.last(),
                )
            }
        }

        if (showScheduleConfirmation) {
            item {
                Text(
                    text = "Scheduling request saved locally for prototype review.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }

        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Sm),
            ) {
                AssetFlowButton(
                    text = "Mark complete",
                    onClick = onMarkCompleteClick,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = uiState.canMarkComplete,
                )
                if (!uiState.canMarkComplete) {
                    Text(
                        text = "This maintenance item is already completed.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                AssetFlowButton(
                    text = "Schedule maintenance",
                    onClick = onScheduleMaintenanceClick,
                    modifier = Modifier.fillMaxWidth(),
                    variant = AssetFlowButtonVariant.Secondary,
                )
            }
        }
    }
}

@Composable
private fun MaintenanceDetailsHeader(
    title: String,
    maintenanceType: String,
    status: MaintenanceStatus,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Sm),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            text = maintenanceType,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        StatusBadge(status = status.toOperationalStatus())
    }
}

@Composable
private fun AssetSummaryCard(
    asset: Asset,
    modifier: Modifier = Modifier,
) {
    AssetFlowCard(modifier = modifier.fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Md),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AssetCategoryIcon(category = asset.category)
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = asset.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = "${asset.category} · ${asset.location}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = AssetFlowSpacing.Xs),
                )
                Text(
                    text = asset.tagNumber,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = AssetFlowSpacing.Xs),
                )
            }
            StatusBadge(status = asset.status)
        }
    }
}

@Composable
private fun DetailLine(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = AssetFlowSpacing.Xs),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = AssetFlowSpacing.Xs),
        )
    }
}

@Composable
private fun HistoryTimelineItem(
    entry: MaintenanceHistoryEntry,
    isLast: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Md),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            Surface(
                modifier = Modifier.size(12.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary,
            ) {}
            if (!isLast) {
                Surface(
                    modifier = Modifier
                        .size(width = 2.dp, height = 48.dp),
                    color = MaterialTheme.colorScheme.outlineVariant,
                ) {}
            }
        }
        AssetFlowCard(modifier = Modifier.weight(1f)) {
            Text(
                text = entry.date,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = entry.summary,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = AssetFlowSpacing.Xs),
            )
            Text(
                text = "Performed by ${entry.performedBy}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = AssetFlowSpacing.Xs),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MaintenanceDetailsScreenPreview() {
    AssetFlowTheme {
        loadMaintenanceDetailsUiState(MockDataRepository.maintenanceRecords.first().id)?.let { uiState ->
            MaintenanceDetailsScreen(
                uiState = uiState,
                onMarkCompleteClick = {},
                onScheduleMaintenanceClick = {},
            )
        }
    }
}
