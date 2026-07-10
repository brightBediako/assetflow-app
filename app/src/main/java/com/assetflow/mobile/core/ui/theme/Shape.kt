package com.assetflow.mobile.core.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * Shape tokens from ui-tokens.md rounded scale.
 */
object AssetFlowCornerRadius {
    val Sm = 4.dp
    val Default = 8.dp
    val Md = 12.dp
    val Lg = 16.dp
    val Xl = 24.dp
    val Full = 9999.dp

    // Component-specific
    val SearchBar = 28.dp
    val BottomSheet = 28.dp
    val Fab = 28.dp
}

val AssetFlowShapes = Shapes(
    extraSmall = RoundedCornerShape(AssetFlowCornerRadius.Sm),
    small = RoundedCornerShape(AssetFlowCornerRadius.Default),
    medium = RoundedCornerShape(AssetFlowCornerRadius.Md),
    large = RoundedCornerShape(AssetFlowCornerRadius.Lg),
    extraLarge = RoundedCornerShape(AssetFlowCornerRadius.Xl),
)
