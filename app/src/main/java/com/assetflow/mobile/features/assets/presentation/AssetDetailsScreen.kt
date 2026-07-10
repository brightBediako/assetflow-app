package com.assetflow.mobile.features.assets.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.assetflow.mobile.core.data.mock.MockDataRepository
import com.assetflow.mobile.core.domain.model.Booking
import com.assetflow.mobile.core.domain.model.MaintenanceRecord
import com.assetflow.mobile.core.domain.model.displayLabel
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

@Composable
fun AssetDetailsRoute(
    assetId: String,
    onBackClick: () -> Unit,
    onBookAssetClick: (String) -> Unit,
    onViewMaintenanceHistoryClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState = remember(assetId) { loadAssetDetailsUiState(assetId) }

    if (uiState == null) {
        EmptyState(
            title = "Asset not found",
            description = "This asset may have been removed or the link is invalid.",
            actionLabel = "Go back",
            onAction = onBackClick,
            modifier = modifier
                .fillMaxSize()
                .padding(AssetFlowSpacing.MarginMobile),
        )
        return
    }

    AssetDetailsScreen(
        uiState = uiState,
        onBookAssetClick = { onBookAssetClick(uiState.asset.id) },
        onViewMaintenanceHistoryClick = { onViewMaintenanceHistoryClick(uiState.asset.id) },
        modifier = modifier,
    )
}

@Composable
fun AssetDetailsScreen(
    uiState: AssetDetailsUiState,
    onBookAssetClick: () -> Unit,
    onViewMaintenanceHistoryClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val asset = uiState.asset

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = AssetFlowSpacing.MarginMobile),
        contentPadding = PaddingValues(vertical = AssetFlowSpacing.MarginMobile),
        verticalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Md),
    ) {
        item {
            AssetDetailsHeader(asset = asset)
        }

        item {
            SectionHeader(title = "Details")
        }

        item {
            AssetFlowCard {
                DetailLine(label = "Location", value = asset.location)
                DetailLine(label = "Tag number", value = asset.tagNumber)
                DetailLine(label = "Asset ID", value = asset.id)
                Text(
                    text = asset.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(top = AssetFlowSpacing.Sm),
                )
            }
        }

        item {
            SectionHeader(title = "Current booking")
        }

        item {
            if (uiState.currentBooking != null) {
                CurrentBookingCard(booking = uiState.currentBooking)
            } else {
                AssetFlowCard {
                    Text(
                        text = "No active booking",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        text = if (uiState.canBook) {
                            "This asset is free to reserve."
                        } else {
                            uiState.bookingUnavailableReason.orEmpty()
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = AssetFlowSpacing.Xs),
                    )
                }
            }
        }

        item {
            SectionHeader(title = "Maintenance")
        }

        if (uiState.openMaintenanceRecords.isEmpty() && uiState.maintenanceRecords.isEmpty()) {
            item {
                AssetFlowCard {
                    Text(
                        text = "No maintenance on record",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        text = "Scheduled or completed maintenance will appear here.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = AssetFlowSpacing.Xs),
                    )
                }
            }
        } else {
            items(
                items = if (uiState.openMaintenanceRecords.isNotEmpty()) {
                    uiState.openMaintenanceRecords
                } else {
                    uiState.maintenanceRecords.take(1)
                },
                key = { record -> record.id },
            ) { record ->
                MaintenanceSummaryCard(record = record)
            }
        }

        asset.lastMaintenanceHint?.let { hint ->
            item {
                Text(
                    text = hint,
                    style = MaterialTheme.typography.labelMedium,
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
                    text = "Book asset",
                    onClick = onBookAssetClick,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = uiState.canBook,
                )
                if (!uiState.canBook && uiState.bookingUnavailableReason != null) {
                    Text(
                        text = uiState.bookingUnavailableReason,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                if (uiState.hasMaintenanceHistory) {
                    AssetFlowButton(
                        text = "View maintenance history",
                        onClick = onViewMaintenanceHistoryClick,
                        modifier = Modifier.fillMaxWidth(),
                        variant = AssetFlowButtonVariant.Secondary,
                    )
                }
            }
        }
    }
}

@Composable
private fun AssetDetailsHeader(
    asset: com.assetflow.mobile.core.domain.model.Asset,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Sm),
    ) {
        AssetCategoryIcon(
            category = asset.category,
            containerSize = 80.dp,
            iconSize = 40.dp,
        )
        Text(
            text = asset.name,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            text = asset.category,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        StatusBadge(status = asset.status)
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
private fun CurrentBookingCard(
    booking: Booking,
    modifier: Modifier = Modifier,
) {
    AssetFlowCard(modifier = modifier.fillMaxWidth()) {
        RowWithBadge(status = booking.status)
        Text(
            text = booking.requesterName,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = AssetFlowSpacing.Sm),
        )
        Text(
            text = "${booking.startDate} to ${booking.endDate}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = AssetFlowSpacing.Xs),
        )
        Text(
            text = "${booking.startTime} – ${booking.endTime}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = booking.purpose,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = AssetFlowSpacing.Sm),
        )
    }
}

@Composable
private fun RowWithBadge(
    status: com.assetflow.mobile.core.domain.model.OperationalStatus,
    modifier: Modifier = Modifier,
) {
    androidx.compose.foundation.layout.Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Booking status",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        StatusBadge(status = status)
    }
}

@Composable
private fun MaintenanceSummaryCard(
    record: MaintenanceRecord,
    modifier: Modifier = Modifier,
) {
    AssetFlowCard(modifier = modifier.fillMaxWidth()) {
        androidx.compose.foundation.layout.Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ) {
            Text(
                text = record.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f),
            )
            StatusBadge(status = record.status.toOperationalStatus())
        }
        Text(
            text = "${record.maintenanceType} · Due ${record.dueDate}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = AssetFlowSpacing.Xs),
        )
        Text(
            text = record.description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = AssetFlowSpacing.Sm),
        )
        Text(
            text = "Assigned to ${record.assignedTo}",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = AssetFlowSpacing.Sm),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AssetDetailsScreenPreview() {
    AssetFlowTheme {
        loadAssetDetailsUiState(MockDataRepository.assets.first().id)?.let { uiState ->
            AssetDetailsScreen(
                uiState = uiState,
                onBookAssetClick = {},
                onViewMaintenanceHistoryClick = {},
            )
        }
    }
}
