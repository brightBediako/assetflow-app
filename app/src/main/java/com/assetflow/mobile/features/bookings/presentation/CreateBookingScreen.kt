package com.assetflow.mobile.features.bookings.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.assetflow.mobile.core.data.mock.MockBookingStore
import com.assetflow.mobile.core.data.mock.MockDataRepository
import com.assetflow.mobile.core.domain.model.Asset
import com.assetflow.mobile.core.domain.model.Booking
import com.assetflow.mobile.core.domain.model.OperationalStatus
import com.assetflow.mobile.core.ui.components.AssetCategoryIcon
import com.assetflow.mobile.core.ui.components.AssetFlowButton
import com.assetflow.mobile.core.ui.components.AssetFlowButtonVariant
import com.assetflow.mobile.core.ui.components.AssetFlowCard
import com.assetflow.mobile.core.ui.components.AssetFlowTextField
import com.assetflow.mobile.core.ui.components.EmptyState
import com.assetflow.mobile.core.ui.components.SectionHeader
import com.assetflow.mobile.core.ui.components.StatusBadge
import com.assetflow.mobile.core.ui.theme.AssetFlowSpacing
import com.assetflow.mobile.core.ui.theme.AssetFlowTheme

@Composable
fun CreateBookingRoute(
    assetId: String,
    onBackClick: () -> Unit,
    onBookingSubmitted: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val asset = remember(assetId) { MockDataRepository.getAssetById(assetId) }
    var uiState by remember(assetId) {
        mutableStateOf(CreateBookingUiState(asset = asset))
    }

    if (asset == null) {
        EmptyState(
            title = "Asset not found",
            description = "Choose another asset to continue booking.",
            actionLabel = "Go back",
            onAction = onBackClick,
            modifier = modifier
                .fillMaxSize()
                .padding(AssetFlowSpacing.MarginMobile),
        )
        return
    }

    CreateBookingScreen(
        uiState = uiState,
        onDateChange = { date ->
            uiState = uiState.copy(date = date, dateError = null, submissionState = CreateBookingSubmissionState.Idle)
        },
        onStartTimeChange = { time ->
            uiState = uiState.copy(startTime = time, startTimeError = null, submissionState = CreateBookingSubmissionState.Idle)
        },
        onEndTimeChange = { time ->
            uiState = uiState.copy(endTime = time, endTimeError = null, submissionState = CreateBookingSubmissionState.Idle)
        },
        onPurposeChange = { purpose ->
            uiState = uiState.copy(purpose = purpose, purposeError = null, submissionState = CreateBookingSubmissionState.Idle)
        },
        onNotesChange = { notes ->
            uiState = uiState.copy(notes = notes, submissionState = CreateBookingSubmissionState.Idle)
        },
        onSubmitClick = {
            val dateError = if (uiState.date.isBlank()) "Date is required" else null
            val startTimeError = if (uiState.startTime.isBlank()) "Start time is required" else null
            val endTimeError = if (uiState.endTime.isBlank()) "End time is required" else null
            val purposeError = if (uiState.purpose.isBlank()) "Purpose is required" else null

            if (dateError != null || startTimeError != null || endTimeError != null || purposeError != null) {
                uiState = uiState.copy(
                    dateError = dateError,
                    startTimeError = startTimeError,
                    endTimeError = endTimeError,
                    purposeError = purposeError,
                )
                return@CreateBookingScreen
            }

            val endDate = uiState.date
            val hasConflict = MockBookingStore.hasConflict(
                assetId = asset.id,
                startDate = uiState.date,
                endDate = endDate,
            )

            if (hasConflict) {
                uiState = uiState.copy(submissionState = CreateBookingSubmissionState.Conflict)
                return@CreateBookingScreen
            }

            uiState = uiState.copy(isSubmitting = true)
            val booking = Booking(
                id = MockBookingStore.nextBookingId(),
                assetId = asset.id,
                requesterId = MockDataRepository.currentUser.id,
                requesterName = MockDataRepository.currentUser.fullName,
                status = OperationalStatus.Pending,
                startDate = uiState.date,
                endDate = endDate,
                startTime = uiState.startTime,
                endTime = uiState.endTime,
                purpose = uiState.purpose,
                notes = uiState.notes,
            )
            MockBookingStore.submitBooking(booking)
            uiState = uiState.copy(
                isSubmitting = false,
                submissionState = CreateBookingSubmissionState.Success,
            )
        },
        onDoneClick = onBookingSubmitted,
        modifier = modifier,
    )
}

@Composable
fun CreateBookingScreen(
    uiState: CreateBookingUiState,
    onDateChange: (String) -> Unit,
    onStartTimeChange: (String) -> Unit,
    onEndTimeChange: (String) -> Unit,
    onPurposeChange: (String) -> Unit,
    onNotesChange: (String) -> Unit,
    onSubmitClick: () -> Unit,
    onDoneClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val asset = uiState.asset ?: return

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(AssetFlowSpacing.MarginMobile),
        verticalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Md),
    ) {
        SelectedAssetSummary(asset = asset)

        when (uiState.submissionState) {
            CreateBookingSubmissionState.Conflict -> ConflictWarningCard()
            CreateBookingSubmissionState.Success -> SuccessConfirmationCard()
            CreateBookingSubmissionState.Idle -> Unit
        }

        if (uiState.submissionState != CreateBookingSubmissionState.Success) {
            SectionHeader(title = "Booking details")

            AssetFlowTextField(
                value = uiState.date,
                onValueChange = onDateChange,
                label = "Date",
                placeholder = "YYYY-MM-DD (e.g. 2026-07-15)",
                isError = uiState.dateError != null,
                supportingText = uiState.dateError ?: "Date picker will connect after backend integration.",
            )
            AssetFlowTextField(
                value = uiState.startTime,
                onValueChange = onStartTimeChange,
                label = "Start time",
                placeholder = "09:00",
                isError = uiState.startTimeError != null,
                supportingText = uiState.startTimeError,
            )
            AssetFlowTextField(
                value = uiState.endTime,
                onValueChange = onEndTimeChange,
                label = "End time",
                placeholder = "17:00",
                isError = uiState.endTimeError != null,
                supportingText = uiState.endTimeError,
            )
            AssetFlowTextField(
                value = uiState.purpose,
                onValueChange = onPurposeChange,
                label = "Purpose",
                placeholder = "Why do you need this asset?",
                isError = uiState.purposeError != null,
                supportingText = uiState.purposeError,
            )
            AssetFlowTextField(
                value = uiState.notes,
                onValueChange = onNotesChange,
                label = "Notes",
                placeholder = "Optional delivery or setup notes",
            )

            Text(
                text = "Tip: booking 2026-07-10 for an already reserved asset shows the conflict warning.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            AssetFlowButton(
                text = "Submit booking",
                onClick = onSubmitClick,
                modifier = Modifier.fillMaxWidth(),
                isLoading = uiState.isSubmitting,
            )
        } else {
            AssetFlowButton(
                text = "Back to bookings",
                onClick = onDoneClick,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun SelectedAssetSummary(
    asset: Asset,
    modifier: Modifier = Modifier,
) {
    AssetFlowCard(modifier = modifier.fillMaxWidth()) {
        androidx.compose.foundation.layout.Row(
            horizontalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Md),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        ) {
            AssetCategoryIcon(category = asset.category)
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = asset.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = "${asset.category} · ${asset.location}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = AssetFlowSpacing.Xs),
                )
                StatusBadge(
                    status = asset.status,
                    modifier = Modifier.padding(top = AssetFlowSpacing.Sm),
                )
            }
        }
    }
}

@Composable
private fun ConflictWarningCard(modifier: Modifier = Modifier) {
    AssetFlowCard(modifier = modifier.fillMaxWidth()) {
        androidx.compose.foundation.layout.Row(
            horizontalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Sm),
            verticalAlignment = androidx.compose.ui.Alignment.Top,
        ) {
            Icon(
                imageVector = Icons.Outlined.WarningAmber,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error,
            )
            Column {
                Text(
                    text = "Booking conflict",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.error,
                )
                Text(
                    text = "Another booking already covers this asset for the selected date. Choose a different date or time.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = AssetFlowSpacing.Xs),
                )
            }
        }
    }
}

@Composable
private fun SuccessConfirmationCard(modifier: Modifier = Modifier) {
    AssetFlowCard(modifier = modifier.fillMaxWidth()) {
        androidx.compose.foundation.layout.Row(
            horizontalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Sm),
            verticalAlignment = androidx.compose.ui.Alignment.Top,
        ) {
            Icon(
                imageVector = Icons.Outlined.CheckCircle,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
            )
            Column {
                Text(
                    text = "Booking submitted",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = "Your request is pending approval. You will see it in your booking history.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = AssetFlowSpacing.Xs),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CreateBookingScreenPreview() {
    AssetFlowTheme {
        CreateBookingScreen(
            uiState = CreateBookingUiState(
                asset = MockDataRepository.assets.first(),
                date = "2026-07-15",
                startTime = "09:00",
                endTime = "12:00",
                purpose = "Department workshop",
            ),
            onDateChange = {},
            onStartTimeChange = {},
            onEndTimeChange = {},
            onPurposeChange = {},
            onNotesChange = {},
            onSubmitClick = {},
            onDoneClick = {},
        )
    }
}
