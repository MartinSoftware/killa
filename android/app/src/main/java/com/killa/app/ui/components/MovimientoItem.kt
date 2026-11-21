package com.killa.app.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.killa.app.domain.model.Movimiento
import com.killa.app.domain.model.TipoMovimiento
import com.killa.app.ui.theme.KillaMontoEgreso
import com.killa.app.ui.theme.KillaMontoIngreso
import com.killa.app.ui.theme.KillaRadius
import com.killa.app.ui.theme.KillaSizes
import com.killa.app.ui.theme.KillaSpacing
import kotlin.math.absoluteValue

/**
 * Fila de un movimiento: ícono circular de ingreso/egreso, título, fecha y monto.
 * No incluye contenedor ni separadores; el padre decide cómo agruparlos.
 *
 * @param movimiento Movimiento a mostrar.
 * @param modifier Modifier opcional.
 */
@Composable
fun MovimientoItem(
    movimiento: Movimiento,
    modifier: Modifier = Modifier
) {
    val isIngreso = movimiento.tipo == TipoMovimiento.INGRESO

    Row(
        modifier = modifier
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