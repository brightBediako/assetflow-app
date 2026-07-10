package com.assetflow.mobile.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.assetflow.mobile.core.data.mock.MockDataRepository
import com.assetflow.mobile.core.domain.model.MaintenanceRecord
import com.assetflow.mobile.core.domain.model.toOperationalStatus
import com.assetflow.mobile.core.ui.theme.AssetFlowSpacing
import com.assetflow.mobile.core.ui.theme.AssetFlowTheme

@Composable
fun MaintenanceCard(
    assetName: String,
    assetLocation: String,
    record: MaintenanceRecord,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    AssetFlowCard(
        modifier = modifier,
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Xs),
            ) {
                Text(
                    text = record.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = assetName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = assetLocation,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = "${record.maintenanceType} · Due ${record.dueDate}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = "Assigned to ${record.assignedTo}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Xs),
            ) {
                StatusBadge(status = record.status.toOperationalStatus())
                PriorityLabel(priority = record.priority)
            }
        }
    }
}

@Composable
private fun PriorityLabel(
    priority: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = "$priority priority",
        modifier = modifier,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

@Preview(showBackground = true)
@Composable
private fun MaintenanceCardPreview() {
    AssetFlowTheme {
        MaintenanceCard(
            assetName = "Epson PowerLite Projector",
            assetLocation = "Conference Room Store",
            record = MockDataRepository.maintenanceRecords.first(),
            modifier = Modifier.padding(AssetFlowSpacing.Md),
        )
    }
}
