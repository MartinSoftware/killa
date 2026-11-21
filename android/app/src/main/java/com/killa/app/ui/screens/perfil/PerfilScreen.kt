package com.killa.app.ui.screens.perfil

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.killa.app.ui.components.KillaButton
import com.killa.app.ui.components.KillaButtonVariant
import com.killa.app.ui.components.KillaTopBar
import com.killa.app.ui.theme.KillaRadius
import com.killa.app.ui.theme.KillaSizes
import com.killa.app.ui.theme.KillaSpacing

@Composable
fun PerfilScreen(
    onVolver: () -> Unit,
    onLogout: () -> Unit,
    viewModel: PerfilViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var mostrarDialogoLogout by remember { mutableStateOf(false) }

    LaunchedEffect(uiState) {
        if (uiState is PerfilUiState.LogoutCompletado) {
            onLogout()
        }
    }

    when (val estado = uiState) {
        is PerfilUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }
        is PerfilUiState.LogoutCompletado -> {
            // LaunchedEffect ya navegó; este estado es transitorio
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }
        is PerfilUiState.Success -> {
            PerfilContent(
                uiState = estado,
                onVolver = onVolver,
                onLogoutClick = { mostrarDialogoLogout = true }
            )

            if (mostrarDialogoLogout) {
                AlertDialog(
                    onDismissRequest = { mostrarDialogoLogout = false },
                    title = { Text("¿Cerrar sesión?") },
                    text = { Text("Tendrás que volver a ingresar con tu correo y PIN.") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                mostrarDialogoLogout = false
                                viewModel.onLogoutConfirmado()
                            }
                        ) {
                            Text("Cerrar sesión", color = MaterialTheme.colorScheme.error)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { mostrarDialogoLogout = false }) {
                            Text("Cancelar")
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun PerfilContent(
    uiState: PerfilUiState.Success,
    onVolver: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            KillaTopBar(
                title = "Mi perfil",
                onBackClick = onVolver
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = KillaSpacing.lg)
            ) {
                // Avatar grande
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = KillaSpacing.lg),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        modifier = Modifier.size(KillaSizes.avatarLarge),
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = uiState.usuario.iniciales,
                                style = MaterialTheme.typography.displayMedium,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(KillaSpacing.md))
                    Text(
                        text = "${uiState.usuario.nombres} ${uiState.usuario.apellidos}",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = uiState.usuario.correo,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(KillaSpacing.md))

                // Datos
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.surface,
                    shape = KillaRadius.lgShape,
                    shadowElevation = 1.dp
                ) {
                    Column(modifier = Modifier.padding(KillaSpacing.md)) {
                        PerfilRow(
                            label = "DNI",
                            value = "•••• ${uiState.usuario.dni.takeLast(4)}"
                        )
                        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                        PerfilRow(label = "Correo", value = uiState.usuario.correo)
                        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                        PerfilRow(label = "Cuenta", value = "KIL-••••-${uiState.cuenta.numeroEnmascarado.takeLast(4)}")
                        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                        PerfilRow(label = "Miembro desde", value = "Marzo 2025")
                    }
                }

                Spacer(modifier = Modifier.height(KillaSpacing.lg))

                KillaButton(
                    text = "Cerrar sesión",
                    onClick = onLogoutClick,
                    variant = KillaButtonVariant.DANGER,
                    loading = uiState.logoutEnProgreso,
                    enabled = !uiState.logoutEnProgreso
                )

                Spacer(modifier = Modifier.height(KillaSpacing.lg))
            }
        }
    }
}

@Composable
private fun PerfilRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = KillaSpacing.md),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}