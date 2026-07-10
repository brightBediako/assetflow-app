package com.assetflow.mobile.core.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.assetflow.mobile.core.ui.theme.AssetFlowSpacing
import com.assetflow.mobile.core.ui.theme.AssetFlowTheme

enum class AssetFlowButtonVariant {
    Primary,
    Secondary,
    Text,
    Destructive,
}

@Composable
fun AssetFlowButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: AssetFlowButtonVariant = AssetFlowButtonVariant.Primary,
    enabled: Boolean = true,
    isLoading: Boolean = false,
) {
    val isEnabled = enabled && !isLoading
    val content: @Composable () -> Unit = {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(AssetFlowSpacing.Md),
                strokeWidth = AssetFlowSpacing.Xs / 2,
                color = when (variant) {
                    AssetFlowButtonVariant.Primary,
                    AssetFlowButtonVariant.Destructive,
                    -> MaterialTheme.colorScheme.onPrimary

                    AssetFlowButtonVariant.Secondary,
                    AssetFlowButtonVariant.Text,
                    -> MaterialTheme.colorScheme.primary
                },
            )
        } else {
            Text(text = text)
        }
    }

    val buttonModifier = modifier.heightIn(min = AssetFlowSpacing.MinTouchTarget)

    when (variant) {
        AssetFlowButtonVariant.Primary -> {
            Button(
                onClick = onClick,
                modifier = buttonModifier,
                enabled = isEnabled,
                contentPadding = PaddingValues(
                    horizontal = AssetFlowSpacing.Lg,
                    vertical = AssetFlowSpacing.Sm,
                ),
                content = { content() },
            )
        }

        AssetFlowButtonVariant.Secondary -> {
            OutlinedButton(
                onClick = onClick,
                modifier = buttonModifier,
                enabled = isEnabled,
                contentPadding = PaddingValues(
                    horizontal = AssetFlowSpacing.Lg,
                    vertical = AssetFlowSpacing.Sm,
                ),
                content = { content() },
            )
        }

        AssetFlowButtonVariant.Text -> {
            TextButton(
                onClick = onClick,
                modifier = buttonModifier,
                enabled = isEnabled,
                contentPadding = PaddingValues(
                    horizontal = AssetFlowSpacing.Md,
                    vertical = AssetFlowSpacing.Sm,
                ),
                content = { content() },
            )
        }

        AssetFlowButtonVariant.Destructive -> {
            Button(
                onClick = onClick,
                modifier = buttonModifier,
                enabled = isEnabled,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError,
                ),
                contentPadding = PaddingValues(
                    horizontal = AssetFlowSpacing.Lg,
                    vertical = AssetFlowSpacing.Sm,
                ),
                content = { content() },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AssetFlowButtonPreview() {
    AssetFlowTheme {
        AssetFlowButton(text = "Book Asset", onClick = {})
    }
}
