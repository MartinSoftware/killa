package com.killa.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.killa.app.ui.theme.KillaSizes
import com.killa.app.ui.theme.KillaSpacing

/**
 * Indicador de pasos reutilizable.
 *
 * @param totalSteps Número total de pasos (ej. 4 para registro)
 * @param currentStep Paso actual (1-based). Si currentStep > totalSteps, todos están done.
 */
@Composable
fun KillaStepIndicator(
    totalSteps: Int,
    currentStep: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..totalSteps) {
            val isDone = i < currentStep
            val isActive = i == currentStep

            Box(
                modifier = Modifier
                    .size(KillaSizes.stepDot)
                    .background(
                        color = when {
                            isDone -> MaterialTheme.colorScheme.secondary
                            isActive -> MaterialTheme.colorScheme.primary
                            else -> MaterialTheme.colorScheme.surfaceVariant
                        },
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isDone) "✓" else i.toString(),
                    style = MaterialTheme.typography.labelMedium,
                    color = when {
                        isDone -> MaterialTheme.colorScheme.onSecondary
                        isActive -> MaterialTheme.colorScheme.onPrimary
                        else -> MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }

            if (i < totalSteps) {
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(2.dp)
                        .padding(horizontal = 0.dp)
                        .background(
                            color = if (isDone) MaterialTheme.colorScheme.secondary
                            else MaterialTheme.colorScheme.outline,
                            shape = RoundedCornerShape(1.dp)
                        )
                )
            }
        }
    }
}