package com.assetflow.mobile.core.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.assetflow.mobile.core.navigation.MainDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScaffold(
    screenTitle: String,
    organizationName: String,
    selectedDestination: MainDestination,
    onDestinationSelected: (MainDestination) -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier,
    userName: String? = null,
    notificationBadgeCount: Int = 0,
    onBackClick: (() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            AssetFlowTopAppBar(
                title = screenTitle,
                organizationName = organizationName,
                userName = userName,
                onProfileClick = onProfileClick,
                onBackClick = onBackClick,
            )
        },
        bottomBar = {
            AssetFlowBottomNavigation(
                selectedDestination = selectedDestination,
                onDestinationSelected = onDestinationSelected,
                notificationBadgeCount = notificationBadgeCount,
            )
        },
        containerColor = androidx.compose.material3.MaterialTheme.colorScheme.background,
        content = { paddingValues ->
            content(paddingValues)
        },
    )
}

@Composable
fun MainScaffoldContent(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    androidx.compose.foundation.layout.Box(
        modifier = modifier.padding(paddingValues),
    ) {
        content()
    }
}
