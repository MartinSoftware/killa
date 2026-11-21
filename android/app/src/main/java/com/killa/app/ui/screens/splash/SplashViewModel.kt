package com.killa.app.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.killa.app.data.local.SessionDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel de Splash.
 * Verifica si hay sesión guardada en DataStore y decide a dónde navegar.
 * Por ahora usa un delay de 2s para simular la verificación del token.
 */
@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sessionDataStore: SessionDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow<SplashUiState>(SplashUiState.Verificando)
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    init {
        verificarSesion()
    }

    private fun verificarSesion() {
        viewModelScope.launch {
            // Simula verificación de token (2s como en el mockup)
            delay(2000)
            val token = sessionDataStore.leerAccessToken()
            _uiState.value = SplashUiState.Listo(haySesion = token != null)
        }
    }
}