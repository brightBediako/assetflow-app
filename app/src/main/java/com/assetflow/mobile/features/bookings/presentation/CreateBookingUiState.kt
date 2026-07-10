package com.assetflow.mobile.features.bookings.presentation

import com.assetflow.mobile.core.domain.model.Asset

enum class CreateBookingSubmissionState {
    Idle,
    Conflict,
    Success,
}

data class CreateBookingUiState(
    val asset: Asset?,
    val date: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val purpose: String = "",
    val notes: String = "",
    val dateError: String? = null,
    val startTimeError: String? = null,
    val endTimeError: String? = null,
    val purposeError: String? = null,
    val submissionState: CreateBookingSubmissionState = CreateBookingSubmissionState.Idle,
    val isSubmitting: Boolean = false,
)
