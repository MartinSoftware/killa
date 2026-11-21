package com.killa.app.ui.screens.splash

/**
 * Estado de la pantalla Splash.
 * Solo hay dos estados posibles: verificando o listo para navegar.
 */
sealed interface SplashUiState {
    data object Verificando : SplashUiState
    data class Listo(val haySesion: Boolean) : SplashUiState
}