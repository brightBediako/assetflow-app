package com.assetflow.mobile.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.assetflow.mobile.core.domain.model.ContentLoadState
import com.assetflow.mobile.core.ui.theme.AssetFlowSpacing
import com.assetflow.mobile.core.ui.theme.AssetFlowTheme

/**
 * Renders loading, empty, error, or network-unavailable support states.
 * Pass [ContentLoadState.Normal] to show [content].
 */
@Composable
fun SupportStateHost(
    state: ContentLoadState,
    emptyTitle: String,
    emptyDescription: String,
    modifier: Modifier = Modifier,
    emptyActionLabel: String? = null,
    onEmptyAction: (() -> Unit)? = null,
    errorTitle: String = "Could not load data",
    errorDescription: String = "Something went wrong while loading this screen. Please try again.",
    onRetry: () -> Unit = {},
    useListPlaceholder: Boolean = true,
    content: @Composable () -> Unit,
) {
    when (state) {
        ContentLoadState.Normal -> content()

        ContentLoadState.Loading -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(AssetFlowSpacing.MarginMobile),
                contentAlignment = Alignment.Center,
            ) {
                if (useListPlaceholder) {
                    LoadingListPlaceholder(
                        modifier = Modifier.fillMaxWidth(),
                        itemCount = 5,
                    )
                } else {
                    LoadingState(modifier = Modifier.fillMaxSize())
                }
            }
        }

        ContentLoadState.Empty -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                EmptyState(
                    title = emptyTitle,
                    description = emptyDescription,
                    actionLabel = emptyActionLabel,
                    onAction = onEmptyAction,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }

        ContentLoadState.Error -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                ErrorState(
                    title = errorTitle,
                    description = errorDescription,
                    onAction = onRetry,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }

        ContentLoadState.NetworkUnavailable -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                NetworkUnavailableState(
                    onRetry = onRetry,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SupportStateHostLoadingPreview() {
    AssetFlowTheme {
        SupportStateHost(
            state = ContentLoadState.Loading,
            emptyTitle = "No assets yet",
            emptyDescription = "Assets will appear here.",
            content = {},
        )
    }
}
