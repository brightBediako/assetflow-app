package com.assetflow.mobile.core.data.mock

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.assetflow.mobile.core.domain.model.MaintenanceHistoryEntry
import com.assetflow.mobile.core.domain.model.MaintenanceRecord
import com.assetflow.mobile.core.domain.model.MaintenanceStatus

/**
 * Mutable maintenance store for UI prototype interactions (mark complete, etc.).
 */
object MockMaintenanceStore {
    var records by mutableStateOf(MockMaintenance.records)
        private set

    var history by mutableStateOf(MockMaintenance.history)
        private set

    fun markComplete(maintenanceId: String) {
        val record = records.firstOrNull { it.id == maintenanceId } ?: return
        if (record.status == MaintenanceStatus.Completed) return

        records = records.map { item ->
            if (item.id == maintenanceId) {
                item.copy(status = MaintenanceStatus.Completed)
            } else {
                item
            }
        }

        val entry = MaintenanceHistoryEntry(
            id = nextHistoryId(),
            maintenanceId = maintenanceId,
            date = "2026-07-10",
            summary = "${record.title} marked complete.",
            performedBy = MockUsers.currentUser.fullName,
        )
        history = history + entry
    }

    private fun nextHistoryId(): String {
        val maxId = history.mapNotNull { entry ->
            entry.id.removePrefix("hist-").toIntOrNull()
        }.maxOrNull() ?: 0
        return "hist-${maxId + 1}"
    }
}
