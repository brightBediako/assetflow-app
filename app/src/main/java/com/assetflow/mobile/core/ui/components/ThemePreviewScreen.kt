package com.assetflow.mobile.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.assetflow.mobile.core.ui.theme.AssetFlowSpacing
import com.assetflow.mobile.core.ui.theme.AssetFlowStatusColors
import com.assetflow.mobile.core.ui.theme.AssetFlowTheme

@Composable
fun ThemePreviewScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(AssetFlowSpacing.MarginMobile),
        verticalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Md),
    ) {
        Text(
            text = "AssetFlow Design Tokens",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            text = "Material 3 theme with calm professional blue palette.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        ColorSwatchCard(
            title = "Primary",
            sampleColor = MaterialTheme.colorScheme.primary,
            label = MaterialTheme.colorScheme.onPrimary,
            sampleLabel = "On Primary",
        )

        Text(
            text = "Status Colors",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = AssetFlowSpacing.Sm),
        )

        StatusSwatchRow("Available", AssetFlowStatusColors.Available)
        StatusSwatchRow("Booked", AssetFlowStatusColors.Booked)
        StatusSwatchRow("Pending", AssetFlowStatusColors.Pending)
        StatusSwatchRow("Maintenance Due", AssetFlowStatusColors.MaintenanceDue)
        StatusSwatchRow("Overdue", AssetFlowStatusColors.Overdue)
        StatusSwatchRow("Unavailable", AssetFlowStatusColors.Unavailable)

        Text(
            text = "Typography",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = AssetFlowSpacing.Sm),
        )
        Text(text = "Headline Large", style = MaterialTheme.typography.headlineLarge)
        Text(text = "Title Large", style = MaterialTheme.typography.titleLarge)
        Text(text = "Title Medium", style = MaterialTheme.typography.titleMedium)
        Text(text = "Body Large", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Body Medium", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Label Large", style = MaterialTheme.typography.labelLarge)
        Text(text = "Label Small", style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
private fun ColorSwatchCard(
    title: String,
    sampleColor: androidx.compose.ui.graphics.Color,
    label: androidx.compose.ui.graphics.Color,
    sampleLabel: String,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
    ) {
        Column(modifier = Modifier.padding(AssetFlowSpacing.Md)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = AssetFlowSpacing.Sm),
                color = sampleColor,
            ) {
                Text(
                    text = sampleLabel,
                    modifier = Modifier.padding(AssetFlowSpacing.Md),
                    color = label,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
    }
}

@Composable
private fun StatusSwatchRow(
    label: String,
    color: androidx.compose.ui.graphics.Color,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Surface(
            color = color,
            shape = MaterialTheme.shapes.small,
        ) {
            Text(
                text = label,
                modifier = Modifier.padding(
                    horizontal = AssetFlowSpacing.Sm,
                    vertical = AssetFlowSpacing.Xs,
                ),
                style = MaterialTheme.typography.labelSmall,
                color = androidx.compose.ui.graphics.Color.White,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ThemePreviewScreenPreview() {
    AssetFlowTheme {
        ThemePreviewScreen()
    }
}
