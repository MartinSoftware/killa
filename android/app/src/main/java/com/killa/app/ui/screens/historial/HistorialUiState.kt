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
 *
 * - Loading: carga inicial (pantalla completa con spinner).
 * - Success: lista cargada. `cargando = true` indica que se está
 *   aplicando un filtro nuevo (solo se muestra un progress bar debajo
 *   de los chips, sin desmontar la lista).
 * - Error: fallo de carga.
 */
sealed interface HistorialUiState {
    data object Loading : HistorialUiState
    data class Success(
        val filtroActual: FiltroHistorial,
        val grupos: List<GrupoMovimientos>,
        val cargando: Boolean = false
    ) : HistorialUiState
    data class Error(val mensaje: String) : HistorialUiState
}