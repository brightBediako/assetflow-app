package com.assetflow.mobile.core.domain.model

data class Asset(
    val id: String,
    val name: String,
    val category: String,
    val location: String,
    val tagNumber: String,
    val status: OperationalStatus,
    val description: String,
    val lastMaintenanceHint: String? = null,
    val activeBookingId: String? = null,
)
