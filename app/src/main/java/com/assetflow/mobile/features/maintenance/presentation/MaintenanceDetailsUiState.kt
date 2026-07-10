package com.assetflow.mobile.features.maintenance.presentation

import com.assetflow.mobile.core.data.mock.MockDataRepository
import com.assetflow.mobile.core.domain.model.Asset
import com.assetflow.mobile.core.domain.model.MaintenanceHistoryEntry
import com.assetflow.mobile.core.domain.model.MaintenanceRecord
import com.assetflow.mobile.core.domain.model.MaintenanceStatus

data class MaintenanceDetailsUiState(
    val record: MaintenanceRecord,
    val asset: Asset?,
    val historyEntries: List<MaintenanceHistoryEntry>,
) {
    val canMarkComplete: Boolean = record.status != MaintenanceStatus.Completed
}

fun loadMaintenanceDetailsUiState(maintenanceId: String): MaintenanceDetailsUiState? {
    val record = MockDataRepository.getMaintenanceById(maintenanceId) ?: return null
    val asset = MockDataRepository.getAssetById(record.assetId)
    val historyEntries = MockDataRepository.getMaintenanceHistory(maintenanceId)
        .sortedByDescending { it.date }

    return MaintenanceDetailsUiState(
        record = record,
        asset = asset,
        historyEntries = historyEntries,
    )
}
