package com.killa.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.killa.app.ui.theme.KillaSizes
import com.killa.app.ui.theme.KillaSpacing

/**
 * Variantes visuales de KillaButton.
 */
enum class KillaButtonVariant {
    PRIMARY,
    SECONDARY,
    GHOST,
    DANGER
}

/**
 * Botón reutilizable de Killa.
 *
 * @param text Texto del botón
 * @param onClick Acción al hacer click
 * @param modifier Modificador opcional
 * @param variant Variante visual (PRIMARY, SECONDARY, GHOST, DANGER)
 * @param enabled Si está habilitado
 * @param loading Si muestra un spinner y se deshabilita (útil para llamadas de red)
 * @param leadingIcon Icono opcional a la izquierda del texto
 */
@Composable
fun KillaButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: KillaButtonVariant = KillaButtonVariant.PRIMARY,
    enabled: Boolean = true,
    loading: Boolean = false,
    leadingIcon: ImageVector? = null
) {
    val isEnabled = enabled && !loading

    val content: @Composable () -> Unit = {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(18.dp),
                    strokeWidth = 2.dp,
                    color = when (variant) {
                        KillaButtonVariant.PRIMARY -> androidx.compose.material3.MaterialTheme.colorScheme.onPrimary
                        KillaButtonVariant.SECONDARY -> androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                        KillaButtonVariant.GHOST -> androidx.compose.material3.MaterialTheme.colorScheme.primary
                        KillaButtonVariant.DANGER -> androidx.compose.material3.MaterialTheme.colorScheme.onError
                    }
                )
                androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(KillaSpacing.sm))
            } else if (leadingIcon != null) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    modifier = Modifier.size(KillaSizes.iconSmall)
                )
                androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(KillaSpacing.sm))
            }
            Text(text = text)
        }
    }

    val buttonModifier = modifier
        .fillMaxWidth()
        .height(KillaSizes.buttonHeight)

    when (variant) {
        KillaButtonVariant.PRIMARY -> Button(
            onClick = onClick,
            modifier = buttonModifier,
            enabled = isEnabled,
            shape = com.killa.app.ui.theme.KillaRadius.mdShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                contentColor = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary
            ),
            content = { content() }
        )
        KillaButtonVariant.SECONDARY -> Button(
            onClick = onClick,
            modifier = buttonModifier,
            enabled = isEnabled,
            shape = com.killa.app.ui.theme.KillaRadius.mdShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surfaceVariant,
                contentColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
            ),
            content = { content() }
        )
        KillaButtonVariant.GHOST -> TextButton(
            onClick = onClick,
            modifier = buttonModifier,
            enabled = isEnabled,
            shape = com.killa.app.ui.theme.KillaRadius.mdShape,
            content = { content() }
        )
        KillaButtonVariant.DANGER -> Button(
            onClick = onClick,
            modifier = buttonModifier,
            enabled = isEnabled,
            shape = com.killa.app.ui.theme.KillaRadius.mdShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = androidx.compose.material3.MaterialTheme.colorScheme.errorContainer,
                contentColor = androidx.compose.material3.MaterialTheme.colorScheme.error
            ),
            content = { content() }
        )
    }
}