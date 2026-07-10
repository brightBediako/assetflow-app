package com.assetflow.mobile.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.assetflow.mobile.core.ui.theme.AssetFlowSpacing

@Composable
fun PlaceholderScreen(
    title: String,
    modifier: Modifier = Modifier,
    message: String = "This screen will be built in a later feature.",
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(AssetFlowSpacing.MarginMobile),
        contentAlignment = Alignment.Center,
    ) {
        androidx.compose.foundation.layout.Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = AssetFlowSpacing.Sm),
            )
        }
    }
}
