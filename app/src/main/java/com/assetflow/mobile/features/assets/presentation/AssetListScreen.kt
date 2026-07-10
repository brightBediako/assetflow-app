package com.assetflow.mobile.features.assets.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.assetflow.mobile.core.data.mock.MockDataRepository
import com.assetflow.mobile.core.ui.components.AssetFlowSearchField
import com.assetflow.mobile.core.ui.components.AssetListCard
import com.assetflow.mobile.core.ui.components.EmptyState
import com.assetflow.mobile.core.ui.theme.AssetFlowSpacing
import com.assetflow.mobile.core.ui.theme.AssetFlowTheme

@Composable
fun AssetListRoute(
    onAssetClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var uiState by remember {
        mutableStateOf(
            AssetListUiState(
                allAssets = MockDataRepository.assets,
                categories = MockDataRepository.assetCategories,
            ),
        )
    }

    AssetListScreen(
        uiState = uiState,
        onSearchQueryChange = { query ->
            uiState = uiState.copy(searchQuery = query)
        },
        onCategorySelected = { category ->
            uiState = uiState.copy(selectedCategory = category)
        },
        onStatusFilterSelected = { filter ->
            uiState = uiState.copy(selectedStatusFilter = filter)
        },
        onClearFiltersClick = {
            uiState = uiState.copy(
                searchQuery = "",
                selectedCategory = "All",
                selectedStatusFilter = AssetStatusFilter.All,
            )
        },
        onAssetClick = onAssetClick,
        modifier = modifier,
    )
}

@Composable
fun AssetListScreen(
    uiState: AssetListUiState,
    onSearchQueryChange: (String) -> Unit,
    onCategorySelected: (String) -> Unit,
    onStatusFilterSelected: (AssetStatusFilter) -> Unit,
    onClearFiltersClick: () -> Unit,
    onAssetClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = AssetFlowSpacing.MarginMobile),
        contentPadding = PaddingValues(vertical = AssetFlowSpacing.MarginMobile),
        verticalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Md),
    ) {
        item {
            AssetFlowSearchField(
                value = uiState.searchQuery,
                onValueChange = onSearchQueryChange,
                placeholder = "Search by name, tag, or location",
            )
        }

        item {
            FilterChipRow(
                title = "Category",
                items = uiState.categories,
                selectedItem = uiState.selectedCategory,
                onItemSelected = onCategorySelected,
                label = { it },
            )
        }

        item {
            FilterChipRow(
                title = "Availability",
                items = AssetStatusFilter.entries.toList(),
                selectedItem = uiState.selectedStatusFilter,
                onItemSelected = onStatusFilterSelected,
                label = { it.label },
            )
        }

        item {
            AssetListResultsHeader(
                visibleCount = uiState.filteredAssets.size,
                totalCount = uiState.allAssets.size,
                hasActiveFilters = uiState.hasActiveFilters,
                onClearFiltersClick = onClearFiltersClick,
            )
        }

        if (uiState.filteredAssets.isEmpty()) {
            item {
                EmptyState(
                    title = if (uiState.hasActiveFilters) "No assets found" else "No assets yet",
                    description = if (uiState.hasActiveFilters) {
                        "Try adjusting your search or filters to find shared assets."
                    } else {
                        "Assets added to your organization will appear here."
                    },
                    actionLabel = if (uiState.hasActiveFilters) "Clear filters" else null,
                    onAction = if (uiState.hasActiveFilters) onClearFiltersClick else null,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        } else {
            items(
                items = uiState.filteredAssets,
                key = { asset -> asset.id },
            ) { asset ->
                AssetListCard(
                    asset = asset,
                    onClick = { onAssetClick(asset.id) },
                )
            }
        }
    }
}

@Composable
private fun <T> FilterChipRow(
    title: String,
    items: List<T>,
    selectedItem: T,
    onItemSelected: (T) -> Unit,
    label: (T) -> String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Sm),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Sm),
        ) {
            items(
                items = items,
                key = { item -> label(item) },
            ) { item ->
                FilterChip(
                    selected = item == selectedItem,
                    onClick = { onItemSelected(item) },
                    label = { Text(text = label(item)) },
                )
            }
        }
    }
}

@Composable
private fun AssetListResultsHeader(
    visibleCount: Int,
    totalCount: Int,
    hasActiveFilters: Boolean,
    onClearFiltersClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    RowWithClearAction(
        modifier = modifier.fillMaxWidth(),
        showClearAction = hasActiveFilters,
        onClearFiltersClick = onClearFiltersClick,
    ) {
        Text(
            text = if (hasActiveFilters) {
                "Showing $visibleCount of $totalCount assets"
            } else {
                "$totalCount assets"
            },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun RowWithClearAction(
    showClearAction: Boolean,
    onClearFiltersClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    androidx.compose.foundation.layout.Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
    ) {
        content()
        if (showClearAction) {
            TextButton(onClick = onClearFiltersClick) {
                Text(text = "Clear filters")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AssetListScreenPreview() {
    AssetFlowTheme {
        AssetListScreen(
            uiState = AssetListUiState(
                allAssets = MockDataRepository.assets,
                categories = MockDataRepository.assetCategories,
            ),
            onSearchQueryChange = {},
            onCategorySelected = {},
            onStatusFilterSelected = {},
            onClearFiltersClick = {},
            onAssetClick = {},
        )
    }
}
