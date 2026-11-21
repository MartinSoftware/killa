package com.killa.app.ui.screens.historial

import com.killa.app.domain.model.Movimiento
import com.killa.app.domain.model.TipoMovimiento

/**
 * Filtros disponibles en Historial.
 */
enum class FiltroHistorial(val label: String) {
    TODOS("Todos"),
    INGRESOS("Ingresos"),
    EGRESOS("Egresos")
}

/**
 * Grupo de movimientos por fecha.
 */
data class GrupoMovimientos(
    val fecha: String,
    val movimientos: List<Movimiento>
)

/**
 * Estado de la pantalla Historial.
 */
sealed interface HistorialUiState {
    data object Loading : HistorialUiState
    data class Success(
        val filtroActual: FiltroHistorial,
        val grupos: List<GrupoMovimientos>
    ) : HistorialUiState
    data class Error(val mensaje: String) : HistorialUiState
}