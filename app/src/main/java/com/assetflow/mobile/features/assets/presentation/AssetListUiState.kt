package com.assetflow.mobile.features.assets.presentation

import com.assetflow.mobile.core.domain.model.Asset
import com.assetflow.mobile.core.domain.model.OperationalStatus

data class AssetListUiState(
    val searchQuery: String = "",
    val selectedCategory: String = "All",
    val selectedStatusFilter: AssetStatusFilter = AssetStatusFilter.All,
    val allAssets: List<Asset> = emptyList(),
    val categories: List<String> = emptyList(),
) {
    val filteredAssets: List<Asset> = filterAssets(
        assets = allAssets,
        query = searchQuery,
        category = selectedCategory,
        statusFilter = selectedStatusFilter,
    )

    val hasActiveFilters: Boolean =
        searchQuery.isNotBlank() ||
            selectedCategory != "All" ||
            selectedStatusFilter != AssetStatusFilter.All
}

enum class AssetStatusFilter(val label: String) {
    All("All"),
    Available("Available"),
    Booked("Booked"),
    MaintenanceDue("Maintenance Due"),
    Unavailable("Unavailable"),
    ;

    fun toOperationalStatus(): OperationalStatus? = when (this) {
        All -> null
        Available -> OperationalStatus.Available
        Booked -> OperationalStatus.Booked
        MaintenanceDue -> OperationalStatus.MaintenanceDue
        Unavailable -> OperationalStatus.Unavailable
    }
}

fun filterAssets(
    assets: List<Asset>,
    query: String,
    category: String,
    statusFilter: AssetStatusFilter,
): List<Asset> {
    val normalizedQuery = query.trim()

    return assets.filter { asset ->
        val matchesCategory = category == "All" || asset.category == category
        val status = statusFilter.toOperationalStatus()
        val matchesStatus = status == null || asset.status == status
        val matchesQuery = normalizedQuery.isBlank() ||
            asset.name.contains(normalizedQuery, ignoreCase = true) ||
            asset.tagNumber.contains(normalizedQuery, ignoreCase = true) ||
            asset.location.contains(normalizedQuery, ignoreCase = true) ||
            asset.category.contains(normalizedQuery, ignoreCase = true)

        matchesCategory && matchesStatus && matchesQuery
    }
}
