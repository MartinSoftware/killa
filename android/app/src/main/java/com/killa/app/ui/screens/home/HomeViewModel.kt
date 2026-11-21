package com.killa.app.ui.screens.home

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
 * ViewModel de Home.
 * Carga FakeData. Cuando conectemos Retrofit, reemplaza por
 * cuentaRepository.obtenerSaldo() + cuentaRepository.obtenerMovimientos().
 */
@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        cargarDatos()
    }

    private fun cargarDatos() {
        viewModelScope.launch {
            delay(800) // simula llamada de red
            _uiState.value = HomeUiState.Success(
                usuario = FakeData.usuario,
                cuenta = FakeData.cuenta,
                movimientosRecientes = FakeData.movimientos.take(3)
            )
        }
    }
}