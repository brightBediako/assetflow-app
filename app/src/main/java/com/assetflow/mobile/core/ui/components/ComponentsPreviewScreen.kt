package com.assetflow.mobile.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.assetflow.mobile.core.domain.model.OperationalStatus
import com.assetflow.mobile.core.data.mock.MockDataRepository
import com.assetflow.mobile.core.ui.theme.AssetFlowSpacing
import com.assetflow.mobile.core.ui.theme.AssetFlowTheme

@Composable
fun ComponentsPreviewScreen(
    modifier: Modifier = Modifier,
) {
    var searchQuery by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(AssetFlowSpacing.MarginMobile),
        verticalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Md),
    ) {
        Text(
            text = "Shared Components",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            text = "Reusable UI building blocks for AssetFlow screens.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        SectionHeader(title = "Mock data snapshot")
        val summary = MockDataRepository.dashboardSummary
        AssetFlowCard {
            Text(
                text = "${summary.totalAssets} assets · ${summary.availableAssets} available",
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = "${summary.activeBookings} active bookings · ${summary.maintenanceDue} maintenance due",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = AssetFlowSpacing.Xs),
            )
            Text(
                text = "Utilization ${summary.utilizationPercent}%",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = AssetFlowSpacing.Sm),
            )
        }

        val sampleAsset = MockDataRepository.assets.first()
        AssetFlowCard(onClick = {}) {
            Text(
                text = sampleAsset.name,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = "${sampleAsset.category} · ${sampleAsset.location}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = AssetFlowSpacing.Xs),
            )
            StatusBadge(
                status = sampleAsset.status,
                modifier = Modifier.padding(top = AssetFlowSpacing.Sm),
            )
        }

        SectionHeader(title = "Buttons")
        Column(verticalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Sm)) {
            AssetFlowButton(text = "Primary action", onClick = {})
            AssetFlowButton(
                text = "Secondary action",
                onClick = {},
                variant = AssetFlowButtonVariant.Secondary,
            )
            AssetFlowButton(
                text = "Loading",
                onClick = {},
                isLoading = true,
            )
            AssetFlowButton(
                text = "Reject",
                onClick = {},
                variant = AssetFlowButtonVariant.Destructive,
            )
        }

        SectionHeader(title = "Inputs")
        AssetFlowSearchField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = "Search assets by name or tag",
        )
        AssetFlowTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email",
            placeholder = "name@organization.com",
        )
        AssetFlowTextField(
            value = "",
            onValueChange = {},
            label = "Password",
            visualTransformation = PasswordVisualTransformation(),
            isError = true,
            supportingText = "Password is required",
        )

        SectionHeader(title = "Status badges")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Sm),
        ) {
            StatusBadge(status = OperationalStatus.Available)
            StatusBadge(status = OperationalStatus.Booked)
            StatusBadge(status = OperationalStatus.Pending)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Sm),
        ) {
            StatusBadge(status = OperationalStatus.MaintenanceDue)
            StatusBadge(status = OperationalStatus.Overdue)
            StatusBadge(status = OperationalStatus.Unavailable)
        }

        SectionHeader(title = "Cards")
        AssetFlowCard(onClick = {}) {
            Text(
                text = "Canon EOS R6 Camera",
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = "Media Lab · Building B",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = AssetFlowSpacing.Xs),
            )
            StatusBadge(
                status = OperationalStatus.Available,
                modifier = Modifier.padding(top = AssetFlowSpacing.Sm),
            )
        }

        SectionHeader(title = "Loading placeholder")
        LoadingListPlaceholder(itemCount = 3)

        SectionHeader(title = "Empty state")
        EmptyState(
            title = "No bookings yet",
            description = "Book an available asset to see your schedule here.",
            actionLabel = "Browse assets",
            onAction = {},
        )

        SectionHeader(title = "Error state")
        ErrorState(
            title = "Could not load data",
            description = "Something went wrong while loading this screen.",
            onAction = {},
        )

        SectionHeader(title = "Network unavailable")
        NetworkUnavailableState(onRetry = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun ComponentsPreviewScreenPreview() {
    AssetFlowTheme {
        ComponentsPreviewScreen()
    }
}
