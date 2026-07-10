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
import com.assetflow.mobile.core.domain.model.Booking
import com.assetflow.mobile.core.ui.theme.AssetFlowSpacing
import com.assetflow.mobile.core.ui.theme.AssetFlowTheme

@Composable
fun BookingCard(
    assetName: String,
    assetLocation: String,
    booking: Booking,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onActionClick: (() -> Unit)? = null,
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
                    text = assetName,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = assetLocation,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = "${booking.startDate} · ${booking.startTime} – ${booking.endTime}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = booking.purpose,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            StatusBadge(
                status = booking.status,
                modifier = Modifier.padding(start = AssetFlowSpacing.Sm),
            )
        }
        if (actionLabel != null && onActionClick != null) {
            AssetFlowButton(
                text = actionLabel,
                onClick = onActionClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = AssetFlowSpacing.Sm),
                variant = AssetFlowButtonVariant.Secondary,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BookingCardPreview() {
    AssetFlowTheme {
        BookingCard(
            assetName = "iPad Pro 12.9\"",
            assetLocation = "Library Desk 4",
            booking = MockDataRepository.bookings.first(),
            actionLabel = "Cancel booking",
            onActionClick = {},
            modifier = Modifier.padding(AssetFlowSpacing.Md),
        )
    }
}
