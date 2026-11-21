package com.killa.app.ui.screens.perfil

import com.killa.app.domain.model.Cuenta
import com.killa.app.domain.model.Usuario

/**
 * Estado de la pantalla Perfil.
 */
sealed interface PerfilUiState {
    data object Loading : PerfilUiState
    data class Success(
        val usuario: Usuario,
        val cuenta: Cuenta,
        val logoutEnProgreso: Boolean = false
    ) : PerfilUiState
    data object LogoutCompletado : PerfilUiState
}