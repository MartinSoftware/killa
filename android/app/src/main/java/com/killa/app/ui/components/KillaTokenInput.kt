package com.killa.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.killa.app.ui.theme.KillaRadius
import com.killa.app.ui.theme.KillaSizes
import com.killa.app.ui.theme.KillaSpacing

/**
 * Estado visual del token de seguridad.
 */
enum class TokenEstado {
    ESPERANDO,   // spinner + "Enviando token..."
    RECIBIDO,    // check verde + "✓ Token recibido"
    EXPIRADO     // texto rojo + "Token expirado — reenvía"
}

/**
 * Campo de token de seguridad (no editable).
 * Nunca muestra el valor del token: solo indica si llegó o no.
 *
 * @param estado Estado actual (ESPERANDO, RECIBIDO, EXPIRADO)
 * @param segundosRestantes Segundos restantes para expirar (null si aún esperando)
 */
@Composable
fun KillaTokenInput(
    estado: TokenEstado,
    modifier: Modifier = Modifier,
    segundosRestantes: Int? = null
) {
    Column(modifier = modifier.fillMaxWidth()) {
        // Caja de estado del token
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = KillaRadius.mdShape
                )
                .border(
                    width = 1.5.dp,
                    color = when (estado) {
                        TokenEstado.RECIBIDO -> MaterialTheme.colorScheme.secondary
                        TokenEstado.EXPIRADO -> MaterialTheme.colorScheme.error
                        TokenEstado.ESPERANDO -> MaterialTheme.colorScheme.outline
                    },
                    shape = KillaRadius.mdShape
                )
                .padding(KillaSpacing.md),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Token de seguridad",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(KillaSpacing.sm))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                when (estado) {
                    TokenEstado.ESPERANDO -> {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(KillaSpacing.xs))
                        Text(
                            text = "Enviando token a tu correo...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    TokenEstado.RECIBIDO -> {
                        Text(
                            text = "✓ Token recibido",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
                        )
                    }
                    TokenEstado.EXPIRADO -> {
                        Text(
                            text = "Token expirado — reenvía",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
                        )
                    }
                }
            }

            if (segundosRestantes != null && estado == TokenEstado.RECIBIDO) {
                Text(
                    text = "Expira en ${segundosRestantes}s",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = KillaSpacing.xs)
                )
            }
        }

        androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(KillaSpacing.md))

        // Campo de token (no editable, solo visual)
        Text(
            text = "Token de 6 dígitos",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = KillaSpacing.xs)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(KillaSizes.tokenBoxHeight)
                .background(
                    color = if (estado == TokenEstado.RECIBIDO) MaterialTheme.colorScheme.secondaryContainer
                    else MaterialTheme.colorScheme.surfaceVariant,
                    shape = KillaRadius.mdShape
                )
                .border(
                    width = 1.5.dp,
                    color = if (estado == TokenEstado.RECIBIDO) MaterialTheme.colorScheme.secondary
                    else MaterialTheme.colorScheme.outline,
                    shape = KillaRadius.mdShape
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (estado == TokenEstado.RECIBIDO) "••••••" else "",
                style = MaterialTheme.typography.headlineMedium,
                fontSize = 24.sp,
                letterSpacing = 8.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}