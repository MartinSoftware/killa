package com.killa.app.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.killa.app.domain.model.Movimiento
import com.killa.app.domain.model.TipoMovimiento
import com.killa.app.ui.theme.KillaMontoEgreso
import com.killa.app.ui.theme.KillaMontoIngreso
import com.killa.app.ui.theme.KillaPrimaryDark
import com.killa.app.ui.theme.KillaPrimaryLight
import com.killa.app.ui.theme.KillaRadius
import com.killa.app.ui.theme.KillaSizes
import com.killa.app.ui.theme.KillaSpacing
import kotlin.math.absoluteValue

@Composable
fun HomeScreen(
    onIrATransferir: () -> Unit,
    onIrAHistorial: () -> Unit,
    onIrAPerfil: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val estado = uiState) {
        is HomeUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }
        is HomeUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = estado.mensaje, color = MaterialTheme.colorScheme.error)
            }
        }
        is HomeUiState.Success -> {
            HomeContent(
                uiState = estado,
                onIrATransferir = onIrATransferir,
                onIrAHistorial = onIrAHistorial,
                onIrAPerfil = onIrAPerfil
            )
        }
    }
}

@Composable
private fun HomeContent(
    uiState: HomeUiState.Success,
    onIrATransferir: () -> Unit,
    onIrAHistorial: () -> Unit,
    onIrAPerfil: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = KillaSpacing.lg)
        ) {
            Spacer(modifier = Modifier.height(KillaSpacing.md))

            // Header: logo + avatar
            HomeHeader(
                iniciales = uiState.usuario.iniciales,
                onAvatarClick = onIrAPerfil
            )

            Spacer(modifier = Modifier.height(KillaSpacing.lg))

            // Balance card
            BalanceCard(
                saldo = uiState.cuenta.saldo,
                numeroEnmascarado = uiState.cuenta.numeroEnmascarado
            )

            Spacer(modifier = Modifier.height(KillaSpacing.lg))

            // Accesos rápidos
            Text(
                text = "Accesos rápidos",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(KillaSpacing.sm))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(KillaSpacing.sm)
            ) {
                QuickActionCard(
                    icon = Icons.AutoMirrored.Filled.Send,
                    label = "Transferir",
                    onClick = onIrATransferir,
                    modifier = Modifier.weight(1f)
                )
                QuickActionCard(
                    icon = Icons.AutoMirrored.Filled.List,
                    label = "Historial",
                    onClick = onIrAHistorial,
                    modifier = Modifier.weight(1f)
                )
                QuickActionCard(
                    icon = Icons.Default.Person,
                    label = "Perfil",
                    onClick = onIrAPerfil,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(KillaSpacing.lg))

            // Últimos movimientos
            Text(
                text = "Últimos movimientos",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(KillaSpacing.sm))
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.surface,
                shape = KillaRadius.lgShape,
                shadowElevation = 1.dp
            ) {
                Column(modifier = Modifier.padding(horizontal = KillaSpacing.md)) {
                    uiState.movimientosRecientes.forEachIndexed { index, movimiento ->
                        MovimientoItem(movimiento = movimiento)
                        if (index < uiState.movimientosRecientes.lastIndex) {
                            HorizontalDivider(
                                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(KillaSpacing.lg))
        }
    }
}

@Composable
private fun HomeHeader(
    iniciales: String,
    onAvatarClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
                modifier = Modifier.size(KillaSizes.logoMarkTiny),
                color = MaterialTheme.colorScheme.primary,
                shape = KillaRadius.smShape
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "K",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.size(KillaSpacing.sm))
            Column {
                Text(
                    text = "Killa",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "BANCO DIGITAL",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Área táctil de 48dp con el avatar de 40dp centrado
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .clickable(onClick = onAvatarClick),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier.size(KillaSizes.avatarSmall),
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = CircleShape
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = iniciales,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun BalanceCard(saldo: Double, numeroEnmascarado: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(KillaRadius.lgShape)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(KillaPrimaryLight, KillaPrimaryDark)
                )
            )
    ) {
        // Círculo decorativo
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 40.dp, y = (-40).dp)
                .size(140.dp)
                .background(
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.1f),
                    shape = CircleShape
                )
        )

        Column(modifier = Modifier.padding(KillaSpacing.lg)) {
            Text(
                text = "Saldo disponible",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
            )
            Spacer(modifier = Modifier.height(KillaSpacing.sm))
            Text(
                text = "S/ %,.2f".format(saldo),
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(KillaSpacing.xs))
            Text(
                text = "Cuenta $numeroEnmascarado",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
            )
        }
    }
}

@Composable
private fun QuickActionCard(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.clickable(onClick = onClick),
        color = MaterialTheme.colorScheme.surface,
        shape = KillaRadius.mdShape,
        shadowElevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = KillaSpacing.md),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier.size(KillaSizes.avatarSmall),
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = KillaRadius.smShape
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(KillaSizes.iconMedium)
                    )
                }
            }
            Spacer(modifier = Modifier.height(KillaSpacing.sm))
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun MovimientoItem(movimiento: Movimiento) {
    val isIngreso = movimiento.tipo == TipoMovimiento.INGRESO

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = KillaSpacing.md),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(KillaSizes.avatarSmall),
            color = if (isIngreso) MaterialTheme.colorScheme.secondaryContainer
            else MaterialTheme.colorScheme.errorContainer,
            shape = KillaRadius.smShape
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = if (isIngreso) Icons.Default.KeyboardArrowDown
                    else Icons.Default.KeyboardArrowUp,
                    contentDescription = if (isIngreso) "Ingreso" else "Egreso",
                    tint = if (isIngreso) MaterialTheme.colorScheme.secondary
                    else MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(KillaSizes.iconMedium)
                )
            }
        }

        Spacer(modifier = Modifier.size(KillaSpacing.md))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = movimiento.titulo,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = movimiento.fecha,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Text(
            text = "${if (isIngreso) "+" else "-"} S/ %,.2f".format(movimiento.monto.absoluteValue),
            style = MaterialTheme.typography.titleSmall,
            color = if (isIngreso) KillaMontoIngreso else KillaMontoEgreso,
            fontWeight = FontWeight.Bold
        )
    }
}