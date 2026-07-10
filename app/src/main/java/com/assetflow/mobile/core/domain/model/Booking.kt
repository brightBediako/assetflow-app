package com.assetflow.mobile.core.domain.model

data class Booking(
    val id: String,
    val assetId: String,
    val requesterId: String,
    val requesterName: String,
    val status: OperationalStatus,
    val startDate: String,
    val endDate: String,
    val startTime: String,
    val endTime: String,
    val purpose: String,
    val notes: String = "",
)
