package com.killa.app.ui.screens.home

import com.killa.app.domain.model.Cuenta
import com.killa.app.domain.model.Movimiento
import com.killa.app.domain.model.Usuario

/**
 * Estado de la pantalla Home.
 */
sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(
        val usuario: Usuario,
        val cuenta: Cuenta,
        val movimientosRecientes: List<Movimiento>
    ) : HomeUiState
    data class Error(val mensaje: String) : HomeUiState
}