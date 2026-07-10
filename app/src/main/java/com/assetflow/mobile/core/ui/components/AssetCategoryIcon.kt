package com.assetflow.mobile.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Chair
import androidx.compose.material.icons.outlined.Computer
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.Science
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.assetflow.mobile.core.ui.theme.AssetFlowCornerRadius
import com.assetflow.mobile.core.ui.theme.AssetFlowSpacing
import com.assetflow.mobile.core.ui.theme.AssetFlowTheme

@Composable
fun AssetCategoryIcon(
    category: String,
    modifier: Modifier = Modifier,
    containerSize: Dp = 48.dp,
    iconSize: Dp = AssetFlowSpacing.Lg,
) {
    Box(
        modifier = modifier
            .size(containerSize)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(AssetFlowCornerRadius.Md),
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = categoryIcon(category),
            contentDescription = category,
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.size(iconSize),
        )
    }
}

fun categoryIcon(category: String): ImageVector = when (category) {
    "Electronics" -> Icons.Outlined.Computer
    "Media" -> Icons.Outlined.CameraAlt
    "Tools" -> Icons.Outlined.Build
    "Vehicles" -> Icons.Outlined.DirectionsCar
    "Laboratory" -> Icons.Outlined.Science
    "Events" -> Icons.Outlined.Event
    "Furniture" -> Icons.Outlined.Chair
    else -> Icons.Outlined.Inventory2
}

@Preview(showBackground = true)
@Composable
private fun AssetCategoryIconPreview() {
    AssetFlowTheme {
        AssetCategoryIcon(category = "Electronics")
    }
}
