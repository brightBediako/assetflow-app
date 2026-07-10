package com.assetflow.mobile.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.assetflow.mobile.core.domain.model.BookingTrendPoint
import com.assetflow.mobile.core.ui.theme.AssetFlowCornerRadius
import com.assetflow.mobile.core.ui.theme.AssetFlowSpacing
import com.assetflow.mobile.core.ui.theme.AssetFlowTheme

@Composable
fun AnalyticsBarRow(
    label: String,
    value: Int,
    maxValue: Int,
    barColor: Color,
    modifier: Modifier = Modifier,
    valueLabel: String = value.toString(),
) {
    val fraction = if (maxValue > 0) value.toFloat() / maxValue.toFloat() else 0f

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Sm),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.widthIn(min = 72.dp, max = 96.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .height(10.dp)
                .clip(RoundedCornerShape(AssetFlowCornerRadius.Full))
                .background(MaterialTheme.colorScheme.surfaceContainerHigh),
        ) {
            if (fraction > 0f) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(fraction)
                        .clip(RoundedCornerShape(AssetFlowCornerRadius.Full))
                        .background(barColor),
                )
            }
        }
        Text(
            text = valueLabel,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.widthIn(min = 24.dp),
            textAlign = TextAlign.End,
        )
    }
}

@Composable
fun AnalyticsColumnChart(
    points: List<BookingTrendPoint>,
    modifier: Modifier = Modifier,
    barColor: Color = MaterialTheme.colorScheme.primary,
) {
    if (points.isEmpty()) {
        EmptyState(
            title = "No trend data",
            description = "Booking activity will appear here once records are available.",
            modifier = modifier.fillMaxWidth(),
        )
        return
    }

    val maxValue = points.maxOf { it.count }.coerceAtLeast(1)
    val chartHeight = 96.dp

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(140.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
    ) {
        points.forEach { point ->
            val barHeight = chartHeight * (point.count.toFloat() / maxValue.toFloat())

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Xs),
            ) {
                Text(
                    text = point.count.toString(),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .fillMaxWidth()
                        .height(barHeight)
                        .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                        .background(barColor),
                )
                Text(
                    text = point.label,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AnalyticsChartsPreview() {
    AssetFlowTheme {
        Column(
            modifier = Modifier.padding(AssetFlowSpacing.Md),
            verticalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Md),
        ) {
            AnalyticsBarRow(
                label = "Electronics",
                value = 4,
                maxValue = 14,
                barColor = MaterialTheme.colorScheme.primary,
            )
            AnalyticsColumnChart(
                points = listOf(
                    BookingTrendPoint("Mon", 3),
                    BookingTrendPoint("Tue", 5),
                    BookingTrendPoint("Wed", 4),
                ),
            )
        }
    }
}
