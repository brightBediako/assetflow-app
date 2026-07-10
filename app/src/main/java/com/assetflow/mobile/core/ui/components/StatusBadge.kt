package com.assetflow.mobile.core.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.assetflow.mobile.core.domain.model.OperationalStatus
import com.assetflow.mobile.core.domain.model.displayLabel
import com.assetflow.mobile.core.ui.theme.AssetFlowCornerRadius
import com.assetflow.mobile.core.ui.theme.AssetFlowSpacing
import com.assetflow.mobile.core.ui.theme.AssetFlowStatusColors
import com.assetflow.mobile.core.ui.theme.AssetFlowTheme

@Composable
fun StatusBadge(
    status: OperationalStatus,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(AssetFlowCornerRadius.Full),
) {
    val (containerColor, contentColor) = statusColors(status)

    Surface(
        modifier = modifier,
        color = containerColor,
        shape = shape,
    ) {
        Text(
            text = status.displayLabel(),
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = contentColor,
        )
    }
}

private fun statusColors(status: OperationalStatus): Pair<Color, Color> = when (status) {
    OperationalStatus.Available -> AssetFlowStatusColors.Available to AssetFlowStatusColors.OnAvailable
    OperationalStatus.Booked -> AssetFlowStatusColors.Booked to AssetFlowStatusColors.OnBooked
    OperationalStatus.MaintenanceDue -> AssetFlowStatusColors.MaintenanceDue to AssetFlowStatusColors.OnMaintenanceDue
    OperationalStatus.Unavailable -> AssetFlowStatusColors.Unavailable to AssetFlowStatusColors.OnUnavailable
    OperationalStatus.Pending -> AssetFlowStatusColors.Pending to AssetFlowStatusColors.OnPending
    OperationalStatus.Approved -> AssetFlowStatusColors.Approved to AssetFlowStatusColors.OnApproved
    OperationalStatus.Active -> AssetFlowStatusColors.Active to AssetFlowStatusColors.OnActive
    OperationalStatus.Completed -> AssetFlowStatusColors.Completed to AssetFlowStatusColors.OnCompleted
    OperationalStatus.Cancelled -> AssetFlowStatusColors.Cancelled to AssetFlowStatusColors.OnCancelled
    OperationalStatus.Overdue -> AssetFlowStatusColors.Overdue to AssetFlowStatusColors.OnOverdue
}

@Preview(showBackground = true)
@Composable
private fun StatusBadgePreview() {
    AssetFlowTheme {
        StatusBadge(status = OperationalStatus.Available)
    }
}
