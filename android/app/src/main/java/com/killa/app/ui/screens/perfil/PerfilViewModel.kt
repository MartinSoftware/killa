package com.killa.app.ui.screens.perfil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.killa.app.data.fake.FakeData
import com.killa.app.data.local.SessionDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel de Perfil.
 * Carga FakeData y maneja el logout (limpia SessionDataStore).
 */
@HiltViewModel
class PerfilViewModel @Inject constructor(
    private val sessionDataStore: SessionDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow<PerfilUiState>(PerfilUiState.Loading)
    val uiState: StateFlow<PerfilUiState> = _uiState.asStateFlow()

    init {
        cargarPerfil()
    }

    private fun cargarPerfil() {
        viewModelScope.launch {
            delay(500)
            _uiState.value = PerfilUiState.Success(
                usuario = FakeData.usuario,
                cuenta = FakeData.cuenta
            )
        }
    }

    fun onLogoutConfirmado() {
        val estadoActual = _uiState.value
        if (estadoActual !is PerfilUiState.Success) return
        if (estadoActual.logoutEnProgreso) return

        viewModelScope.launch {
            _uiState.value = estadoActual.copy(logoutEnProgreso = true)
            sessionDataStore.limpiarSesion()
            delay(300) // pequeña espera para feedback visual
            _uiState.value = PerfilUiState.LogoutCompletado
        }
    }
}