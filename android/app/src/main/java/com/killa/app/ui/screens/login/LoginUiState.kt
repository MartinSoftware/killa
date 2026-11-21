package com.killa.app.ui.screens.login

/**
 * Estado de la pantalla Login.
 */
sealed interface LoginUiState {
    data object Idle : LoginUiState
    data object Loading : LoginUiState
    data class Success(val nombre: String) : LoginUiState
    data class Error(val mensaje: String) : LoginUiState
}