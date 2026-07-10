package com.assetflow.mobile.features.auth.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.assetflow.mobile.core.data.mock.MockAuthSession
import com.assetflow.mobile.core.data.mock.MockDataRepository
import com.assetflow.mobile.core.domain.model.UserRole
import com.assetflow.mobile.core.domain.model.displayLabel
import com.assetflow.mobile.core.ui.components.AssetFlowButton
import com.assetflow.mobile.core.ui.components.AssetFlowButtonVariant
import com.assetflow.mobile.core.ui.components.AssetFlowTextField
import com.assetflow.mobile.core.ui.theme.AssetFlowSpacing
import com.assetflow.mobile.core.ui.theme.AssetFlowTheme

@Composable
fun RegisterRoute(
    onRegisterSuccess: () -> Unit,
    onBackToLoginClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var uiState by remember {
        mutableStateOf(
            RegisterUiState(
                organization = MockDataRepository.organization.name,
            ),
        )
    }

    RegisterScreen(
        uiState = uiState,
        onFullNameChange = { fullName ->
            uiState = uiState.copy(fullName = fullName, fullNameError = null)
        },
        onOrganizationChange = { organization ->
            uiState = uiState.copy(organization = organization, organizationError = null)
        },
        onEmailChange = { email ->
            uiState = uiState.copy(email = email, emailError = null)
        },
        onPasswordChange = { password ->
            uiState = uiState.copy(password = password, passwordError = null)
        },
        onConfirmPasswordChange = { confirmPassword ->
            uiState = uiState.copy(confirmPassword = confirmPassword, confirmPasswordError = null)
        },
        onRoleSelected = { role ->
            uiState = uiState.copy(selectedRole = role)
        },
        onCreateAccountClick = {
            val fullNameError = if (uiState.fullName.isBlank()) "Full name is required" else null
            val organizationError = if (uiState.organization.isBlank()) "Organization is required" else null
            val emailError = if (uiState.email.isBlank()) "Email is required" else null
            val passwordError = when {
                uiState.password.isBlank() -> "Password is required"
                uiState.password.length < 6 -> "Password must be at least 6 characters"
                else -> null
            }
            val confirmPasswordError = when {
                uiState.confirmPassword.isBlank() -> "Please confirm your password"
                uiState.confirmPassword != uiState.password -> "Passwords do not match"
                else -> null
            }

            if (
                fullNameError != null ||
                organizationError != null ||
                emailError != null ||
                passwordError != null ||
                confirmPasswordError != null
            ) {
                uiState = uiState.copy(
                    fullNameError = fullNameError,
                    organizationError = organizationError,
                    emailError = emailError,
                    passwordError = passwordError,
                    confirmPasswordError = confirmPasswordError,
                )
                return@RegisterScreen
            }

            uiState = uiState.copy(isLoading = true)
            MockAuthSession.signIn()
            onRegisterSuccess()
        },
        onBackToLoginClick = onBackToLoginClick,
        modifier = modifier,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RegisterScreen(
    uiState: RegisterUiState,
    onFullNameChange: (String) -> Unit,
    onOrganizationChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onRoleSelected: (UserRole) -> Unit,
    onCreateAccountClick: () -> Unit,
    onBackToLoginClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = AssetFlowSpacing.MarginMobile,
                    vertical = AssetFlowSpacing.Lg,
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AuthBrandHeader(subtitle = "Create your account")

            Spacer(modifier = Modifier.height(AssetFlowSpacing.Lg))

            Text(
                text = "Join your organization on AssetFlow to manage shared equipment and resources.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(AssetFlowSpacing.Lg))

            AssetFlowTextField(
                value = uiState.fullName,
                onValueChange = onFullNameChange,
                label = "Full name",
                placeholder = "Ama Mensah",
                isError = uiState.fullNameError != null,
                supportingText = uiState.fullNameError,
            )

            Spacer(modifier = Modifier.height(AssetFlowSpacing.Md))

            AssetFlowTextField(
                value = uiState.organization,
                onValueChange = onOrganizationChange,
                label = "Organization",
                placeholder = "Northbridge University",
                isError = uiState.organizationError != null,
                supportingText = uiState.organizationError,
            )

            Spacer(modifier = Modifier.height(AssetFlowSpacing.Md))

            AssetFlowTextField(
                value = uiState.email,
                onValueChange = onEmailChange,
                label = "Email",
                placeholder = "name@organization.com",
                isError = uiState.emailError != null,
                supportingText = uiState.emailError,
            )

            Spacer(modifier = Modifier.height(AssetFlowSpacing.Md))

            AssetFlowTextField(
                value = uiState.password,
                onValueChange = onPasswordChange,
                label = "Password",
                placeholder = "Create a password",
                visualTransformation = PasswordVisualTransformation(),
                isError = uiState.passwordError != null,
                supportingText = uiState.passwordError,
            )

            Spacer(modifier = Modifier.height(AssetFlowSpacing.Md))

            AssetFlowTextField(
                value = uiState.confirmPassword,
                onValueChange = onConfirmPasswordChange,
                label = "Confirm password",
                placeholder = "Re-enter your password",
                visualTransformation = PasswordVisualTransformation(),
                isError = uiState.confirmPasswordError != null,
                supportingText = uiState.confirmPasswordError,
            )

            Spacer(modifier = Modifier.height(AssetFlowSpacing.Md))

            RoleSelector(
                selectedRole = uiState.selectedRole,
                onRoleSelected = onRoleSelected,
            )

            Spacer(modifier = Modifier.height(AssetFlowSpacing.Lg))

            AssetFlowButton(
                text = "Create account",
                onClick = onCreateAccountClick,
                modifier = Modifier.fillMaxWidth(),
                isLoading = uiState.isLoading,
            )

            Spacer(modifier = Modifier.height(AssetFlowSpacing.Md))

            Text(
                text = "Already have an account?",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            AssetFlowButton(
                text = "Back to login",
                onClick = onBackToLoginClick,
                variant = AssetFlowButtonVariant.Text,
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun RoleSelector(
    selectedRole: UserRole,
    onRoleSelected: (UserRole) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Sm),
    ) {
        Text(
            text = "Role",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            text = "Choose how you will use AssetFlow in your organization.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Sm),
            verticalArrangement = Arrangement.spacedBy(AssetFlowSpacing.Sm),
        ) {
            UserRole.entries.forEach { role ->
                FilterChip(
                    selected = selectedRole == role,
                    onClick = { onRoleSelected(role) },
                    label = { Text(text = role.displayLabel()) },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    AssetFlowTheme {
        RegisterScreen(
            uiState = RegisterUiState(
                fullName = "Kwesi Boateng",
                organization = "Northbridge University",
                email = "kwesi.boateng@northbridge.edu",
            ),
            onFullNameChange = {},
            onOrganizationChange = {},
            onEmailChange = {},
            onPasswordChange = {},
            onConfirmPasswordChange = {},
            onRoleSelected = {},
            onCreateAccountClick = {},
            onBackToLoginClick = {},
        )
    }
}
