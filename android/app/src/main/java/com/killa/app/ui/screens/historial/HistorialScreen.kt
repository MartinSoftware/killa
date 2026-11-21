package com.killa.app.ui.screens.historial

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.killa.app.ui.components.KillaTopBar
import com.killa.app.ui.components.MovimientoItem
import com.killa.app.ui.theme.KillaRadius
import com.killa.app.ui.theme.KillaSizes
import com.killa.app.ui.theme.KillaSpacing

@Composable
fun HistorialScreen(
    onVolver: () -> Unit,
    viewModel: HistorialViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val estado = uiState) {
        is HistorialUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }
        is HistorialUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = estado.mensaje, color = MaterialTheme.colorScheme.error)
            }
        }
        is HistorialUiState.Success -> {
            HistorialContent(
                estado = estado,
                onVolver = onVolver,
                onFiltroChange = viewModel::onFiltroChange
            )
        }
    }
}

@Composable
private fun HistorialContent(
    estado: HistorialUiState.Success,
    onVolver: () -> Unit,
    onFiltroChange: (FiltroHistorial) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.fillMaxSize().systemBarsPadding()) {
            KillaTopBar(title = "Historial", onBackClick = onVolver)

            FiltrosRow(
                filtroActual = estado.filtroActual,
                onFiltroChange = onFiltroChange,
                modifier = Modifier.padding(
                    horizontal = KillaSpacing.lg,
                    vertical = KillaSpacing.md
                )
            )

            // Barra fina de progreso solo cuando se está aplicando un filtro
            if (estado.cargando) {
                androidx.compose.material3.LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = KillaSpacing.lg)
                        .height(2.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            if (estado.grupos.isEmpty() && !estado.cargando) {
                EstadoVacio(modifier = Modifier.weight(1f).fillMaxWidth())
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentPadding = PaddingValues(
                        start = KillaSpacing.lg,
                        end = KillaSpacing.lg,
                        top = if (estado.cargando) KillaSpacing.sm else KillaSpacing.md,
                        bottom = KillaSpacing.lg
                    ),
                    verticalArrangement = Arrangement.spacedBy(KillaSpacing.lg)
                ) {
                    items(estado.grupos, key = { it.fecha }) { grupo ->
                        GrupoSection(grupo = grupo)
                    }
                }
            }
        }
    }
}

@Composable
private fun FiltrosRow(
    filtroActual: FiltroHistorial,
    onFiltroChange: (FiltroHistorial) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(KillaSpacing.sm)
    ) {
        FiltroHistorial.entries.forEach { filtro ->
            FiltroChip(
                label = filtro.label,
                selected = filtro == filtroActual,
                onClick = { onFiltroChange(filtro) }
            )
        }
    }
}

@Composable
private fun FiltroChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = CircleShape,
        color = if (selected) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.surfaceVariant
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = if (selected) MaterialTheme.colorScheme.onPrimary
            else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(
                horizontal = KillaSpacing.md,
                vertical = KillaSpacing.sm
            )
        )
    }
}

@Composable
private fun GrupoSection(grupo: GrupoMovimientos) {
    Column {
        Text(
            text = grupo.fecha,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(KillaSpacing.sm))
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface,
            shape = KillaRadius.lgShape,
            shadowElevation = 1.dp
        ) {
            Column(modifier = Modifier.padding(horizontal = KillaSpacing.md)) {
                grupo.movimientos.forEachIndexed { index, movimiento ->
                    MovimientoItem(movimiento = movimiento)
                    if (index < grupo.movimientos.lastIndex) {
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EstadoVacio(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(KillaSpacing.lg),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(KillaSizes.iconLarge)
        )
        Spacer(modifier = Modifier.height(KillaSpacing.sm))
        Text(
            text = "No hay movimientos para este filtro",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}