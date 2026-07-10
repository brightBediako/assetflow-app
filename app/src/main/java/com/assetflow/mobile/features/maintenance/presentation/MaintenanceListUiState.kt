package com.assetflow.mobile.features.maintenance.presentation

import com.assetflow.mobile.core.data.mock.MockDataRepository
import com.assetflow.mobile.core.domain.model.MaintenanceRecord
import com.assetflow.mobile.core.domain.model.MaintenanceStatus

data class MaintenanceListItem(
    val record: MaintenanceRecord,
    val assetName: String,
    val assetLocation: String,
)

fun MaintenanceRecord.toListItem(): MaintenanceListItem {
    val asset = MockDataRepository.getAssetById(assetId)
    return MaintenanceListItem(
        record = this,
        assetName = asset?.name ?: "Unknown asset",
        assetLocation = asset?.location ?: "Unknown location",
    )
}

fun List<MaintenanceRecord>.toListItems(): List<MaintenanceListItem> = map { it.toListItem() }

data class MaintenanceListUiState(
    val overdueItems: List<MaintenanceListItem>,
    val dueSoonItems: List<MaintenanceListItem>,
    val scheduledItems: List<MaintenanceListItem>,
    val completedItems: List<MaintenanceListItem>,
) {
    val totalOpenItems: Int = overdueItems.size + dueSoonItems.size + scheduledItems.size
}

fun loadMaintenanceListUiState(): MaintenanceListUiState {
    val records = MockDataRepository.maintenanceRecords

    return MaintenanceListUiState(
        overdueItems = records
            .filter { it.status == MaintenanceStatus.Overdue }
            .toListItems(),
        dueSoonItems = records
            .filter { it.status == MaintenanceStatus.DueSoon }
            .toListItems(),
        scheduledItems = records
            .filter { it.status == MaintenanceStatus.Scheduled }
            .toListItems(),
        completedItems = records
            .filter { it.status == MaintenanceStatus.Completed }
            .toListItems(),
    )
}
