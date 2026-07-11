package com.assetflow.mobile.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.assetflow.mobile.core.ui.theme.AssetFlowSpacing
import com.assetflow.mobile.core.ui.theme.AssetFlowTheme

@Composable
fun ErrorState(
    title: String,
    modifier: Modifier = Modifier,
    description: String? = null,
    icon: ImageVector = Icons.Outlined.ErrorOutline,
    actionLabel: String? = "Try again",
    onAction: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(AssetFlowSpacing.Lg),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Sm),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(AssetFlowSpacing.Xl),
            tint = MaterialTheme.colorScheme.error,
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
        )
        if (!description.isNullOrBlank()) {
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        if (actionLabel != null && onAction != null) {
            AssetFlowButton(
                text = actionLabel,
                onClick = onAction,
                modifier = Modifier.padding(top = AssetFlowSpacing.Sm),
                variant = AssetFlowButtonVariant.Secondary,
            )
        }
    }
}

@Composable
fun NetworkUnavailableState(
    modifier: Modifier = Modifier,
    onRetry: (() -> Unit)? = null,
) {
    ErrorState(
        title = "Network unavailable",
        description = "Check your connection and try again.",
        icon = Icons.Outlined.CloudOff,
        actionLabel = if (onRetry != null) "Try again" else null,
        onAction = onRetry,
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
private fun ErrorStatePreview() {
    AssetFlowTheme {
        ErrorState(
            title = "Could not load data",
            description = "Something went wrong while loading this screen.",
            onAction = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NetworkUnavailableStatePreview() {
    AssetFlowTheme {
        NetworkUnavailableState(onRetry = {})
    }
}
