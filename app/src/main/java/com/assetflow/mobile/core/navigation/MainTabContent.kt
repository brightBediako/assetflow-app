package com.assetflow.mobile.core.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.assetflow.mobile.core.data.mock.MockDataRepository
import com.assetflow.mobile.core.ui.components.AssetFlowCard
import com.assetflow.mobile.core.ui.components.SectionHeader
import com.assetflow.mobile.core.ui.theme.AssetFlowSpacing

@Composable
fun NotificationsTabContent(
    modifier: Modifier = Modifier,
) {
    val unread = MockDataRepository.getUnreadNotificationCount()

    MainTabScrollContainer(modifier = modifier) {
        SectionHeader(title = "Notifications")
        AssetFlowCard {
            MetricLine("${MockDataRepository.notifications.size} total notifications")
            MetricLine("$unread unread")
        }
        Text(
            text = "Notification center will be built in Phase 8.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = AssetFlowSpacing.Sm),
        )
    }
}

@Composable
private fun MainTabScrollContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(AssetFlowSpacing.MarginMobile),
        verticalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Sm),
        content = { content() },
    )
}

@Composable
private fun MetricLine(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = AssetFlowSpacing.Xs),
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurface,
    )
}
