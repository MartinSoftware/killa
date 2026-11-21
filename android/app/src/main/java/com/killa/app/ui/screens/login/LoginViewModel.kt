package com.killa.app.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.killa.app.data.fake.FakeData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel de Login.
 * Por ahora usa FakeData + delay para simular la llamada a POST /auth/login.
 * Cuando conectemos Retrofit, solo cambia el cuerpo de [onLoginClick].
 */
@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onLoginClick(correo: String, pin: String) {
        if (correo.isBlank() || pin.length != 6) {
            _uiState.value = LoginUiState.Error("Ingresa un correo y PIN de 6 dígitos")
            return
        }

        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            delay(1500) // simula llamada de red
            // Simula validación: cualquier correo válido + PIN de 6 dígitos funciona
            _uiState.value = LoginUiState.Success(FakeData.usuario.nombres)
        }
    }

    fun onErrorConsumido() {
        _uiState.value = LoginUiState.Idle
    }
}