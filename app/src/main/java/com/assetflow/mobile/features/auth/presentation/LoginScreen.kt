package com.assetflow.mobile.features.auth.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.assetflow.mobile.core.data.mock.MockAuthSession
import com.assetflow.mobile.core.data.mock.MockUsers
import com.assetflow.mobile.core.ui.components.AssetFlowButton
import com.assetflow.mobile.core.ui.components.AssetFlowButtonVariant
import com.assetflow.mobile.core.ui.components.AssetFlowTextField
import com.assetflow.mobile.core.ui.theme.AssetFlowSpacing
import com.assetflow.mobile.core.ui.theme.AssetFlowTheme
import kotlinx.coroutines.launch

@Composable
fun LoginRoute(
    onLoginSuccess: () -> Unit,
    onCreateAccountClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var uiState by remember {
        mutableStateOf(
            LoginUiState(
                email = MockUsers.brightBediako.email,
            ),
        )
    }

    LoginScreen(
        uiState = uiState,
        onEmailChange = { email ->
            uiState = uiState.copy(email = email, emailError = null)
        },
        onPasswordChange = { password ->
            uiState = uiState.copy(password = password, passwordError = null)
        },
        onLoginClick = {
            val trimmedEmail = uiState.email.trim()
            val emailError = when {
                trimmedEmail.isBlank() -> "Email is required"
                MockUsers.getUserByEmail(trimmedEmail) == null ->
                    "No demo account found for this email. Use bright@gmail.com, ama@gmail.com, or kwesi@gmail.com."
                else -> null
            }
            val passwordError = if (uiState.password.isBlank()) "Password is required" else null

            if (emailError != null || passwordError != null) {
                uiState = uiState.copy(
                    emailError = emailError,
                    passwordError = passwordError,
                )
                return@LoginScreen
            }

            val user = MockUsers.getUserByEmail(trimmedEmail) ?: return@LoginScreen
            uiState = uiState.copy(isLoading = true)
            MockAuthSession.signIn(user)
            onLoginSuccess()
        },
        onForgotPasswordClick = {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = "Password reset isn’t available in this demo. Contact your admin for help.",
                )
            }
        },
        onCreateAccountClick = onCreateAccountClick,
        snackbarHostState = snackbarHostState,
        modifier = modifier,
    )
}

@Composable
fun LoginScreen(
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onCreateAccountClick: () -> Unit,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = MaterialTheme.colorScheme.background,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = AssetFlowSpacing.MarginMobile,
                    vertical = AssetFlowSpacing.Xl,
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            AuthBrandHeader()

            Spacer(modifier = Modifier.height(AssetFlowSpacing.Xl))

            Text(
                text = "Manage shared assets, bookings, and maintenance from one mobile workspace.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(AssetFlowSpacing.Lg))

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
                placeholder = "Enter your password",
                visualTransformation = PasswordVisualTransformation(),
                isError = uiState.passwordError != null,
                supportingText = uiState.passwordError,
            )

            Spacer(modifier = Modifier.height(AssetFlowSpacing.Lg))

            AssetFlowButton(
                text = "Log in",
                onClick = onLoginClick,
                modifier = Modifier.fillMaxWidth(),
                isLoading = uiState.isLoading,
            )

            Spacer(modifier = Modifier.height(AssetFlowSpacing.Sm))

            AssetFlowButton(
                text = "Forgot password?",
                onClick = onForgotPasswordClick,
                variant = AssetFlowButtonVariant.Text,
            )

            Spacer(modifier = Modifier.height(AssetFlowSpacing.Md))

            Text(
                text = "New to AssetFlow?",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            AssetFlowButton(
                text = "Create account",
                onClick = onCreateAccountClick,
                variant = AssetFlowButtonVariant.Text,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    AssetFlowTheme {
        LoginScreen(
            uiState = LoginUiState(
                email = "bright@gmail.com",
            ),
            onEmailChange = {},
            onPasswordChange = {},
            onLoginClick = {},
            onForgotPasswordClick = {},
            onCreateAccountClick = {},
            snackbarHostState = remember { SnackbarHostState() },
        )
    }
}
