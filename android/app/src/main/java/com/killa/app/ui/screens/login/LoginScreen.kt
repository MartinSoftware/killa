package com.killa.app.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.killa.app.ui.components.KillaButton
import com.killa.app.ui.components.KillaButtonVariant
import com.killa.app.ui.components.KillaTextField
import com.killa.app.ui.theme.KillaRadius
import com.killa.app.ui.theme.KillaSizes
import com.killa.app.ui.theme.KillaSpacing

/**
 * Pantalla Login.
 * Solo UI: recibe el estado y emite eventos al ViewModel.
 */
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onIrARegistro: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var correo by remember { mutableStateOf("ana@killa.pe") }
    var pin by remember { mutableStateOf("") }

    LaunchedEffect(uiState) {
        if (uiState is LoginUiState.Success) {
            onLoginSuccess()
        }
    }

    LoginContent(
        correo = correo,
        onCorreoChange = { correo = it },
        pin = pin,
        onPinChange = { if (it.length <= 6) pin = it },
        uiState = uiState,
        onLoginClick = { viewModel.onLoginClick(correo, pin) },
        onIrARegistro = onIrARegistro
    )
}

@Composable
private fun LoginContent(
    correo: String,
    onCorreoChange: (String) -> Unit,
    pin: String,
    onPinChange: (String) -> Unit,
    uiState: LoginUiState,
    onLoginClick: () -> Unit,
    onIrARegistro: () -> Unit
) {
    val isLoading = uiState is LoginUiState.Loading
    val errorMensaje = (uiState as? LoginUiState.Error)?.mensaje

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(KillaSpacing.lg),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(KillaSpacing.lg))

            // Logo pequeño
            Surface(
                modifier = Modifier.size(KillaSizes.logoMarkSmall),
                color = MaterialTheme.colorScheme.primary,
                shape = KillaRadius.mdShape
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "K",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            Text(
                text = "Killa",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(top = KillaSpacing.sm)
            )
            Text(
                text = "BANCO DIGITAL",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(KillaSpacing.xl))

            Text(
                text = "Bienvenido de vuelta",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Ingresa tus datos para continuar",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = KillaSpacing.xs, bottom = KillaSpacing.lg)
            )

            KillaTextField(
                value = correo,
                onValueChange = onCorreoChange,
                label = "Correo electrónico",
                placeholder = "tu@correo.com",
                enabled = !isLoading,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(KillaSpacing.md))

            KillaTextField(
                value = pin,
                onValueChange = onPinChange,
                label = "PIN de 6 dígitos",
                placeholder = "••••••",
                enabled = !isLoading,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                visualTransformation = PasswordVisualTransformation(),
                errorMessage = errorMensaje
            )

            Spacer(modifier = Modifier.height(KillaSpacing.lg))

            KillaButton(
                text = "Ingresar",
                onClick = onLoginClick,
                loading = isLoading,
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.height(KillaSpacing.md))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "¿No tienes cuenta?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                TextButton(onClick = onIrARegistro, enabled = !isLoading) {
                    Text(
                        text = "Regístrate",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}