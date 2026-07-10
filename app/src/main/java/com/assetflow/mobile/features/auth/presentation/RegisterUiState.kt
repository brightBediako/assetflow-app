package com.assetflow.mobile.features.auth.presentation

import com.assetflow.mobile.core.domain.model.UserRole

data class RegisterUiState(
    val fullName: String = "",
    val organization: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val selectedRole: UserRole = UserRole.Staff,
    val isLoading: Boolean = false,
    val fullNameError: String? = null,
    val organizationError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
)
