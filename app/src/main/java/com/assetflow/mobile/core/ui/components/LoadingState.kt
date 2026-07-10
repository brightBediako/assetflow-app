package com.assetflow.mobile.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.assetflow.mobile.core.ui.theme.AssetFlowSpacing
import com.assetflow.mobile.core.ui.theme.AssetFlowTheme

@Composable
fun LoadingState(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
fun LoadingListPlaceholder(
    modifier: Modifier = Modifier,
    itemCount: Int = 4,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Sm),
    ) {
        repeat(itemCount) {
            LoadingRowPlaceholder()
        }
    }
}

@Composable
private fun LoadingRowPlaceholder() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(AssetFlowSpacing.MinTouchTarget + AssetFlowSpacing.Md),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
    ) {}
}

@Preview(showBackground = true)
@Composable
private fun LoadingListPlaceholderPreview() {
    AssetFlowTheme {
        LoadingListPlaceholder(
            modifier = Modifier.padding(AssetFlowSpacing.Md),
        )
    }
}
