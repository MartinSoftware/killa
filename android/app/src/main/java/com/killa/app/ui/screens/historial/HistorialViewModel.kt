package com.killa.app.ui.screens.historial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.killa.app.data.fake.FakeData
import com.killa.app.domain.model.TipoMovimiento
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel de Historial.
 * Carga FakeData y agrupa movimientos por fecha. Filtra por tipo.
 */
@HiltViewModel
class HistorialViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<HistorialUiState>(HistorialUiState.Loading)
    val uiState: StateFlow<HistorialUiState> = _uiState.asStateFlow()

    init {
        // Carga inicial: pantalla completa con spinner
        cargarMovimientos(FiltroHistorial.TODOS, esCargaInicial = true)
    }

    fun onFiltroChange(filtro: FiltroHistorial) {
        val actual = _uiState.value
        // Si ya estamos en el mismo filtro o ya hay una carga en curso, ignorar
        if (actual is HistorialUiState.Success && actual.filtroActual == filtro) return
        if (actual is HistorialUiState.Success && actual.cargando) return

        cargarMovimientos(filtro, esCargaInicial = false)
    }

    private fun cargarMovimientos(filtro: FiltroHistorial, esCargaInicial: Boolean) {
        viewModelScope.launch {
            if (esCargaInicial) {
                _uiState.value = HistorialUiState.Loading
            } else {
                val actual = _uiState.value as? HistorialUiState.Success
                _uiState.value = actual?.copy(cargando = true)
                    ?: HistorialUiState.Loading
            }

            delay(500) // simula llamada de red

            val movimientosFiltrados = when (filtro) {
                FiltroHistorial.TODOS -> FakeData.movimientos
                FiltroHistorial.INGRESOS -> FakeData.movimientos.filter {
                    it.tipo == TipoMovimiento.INGRESO
                }
                FiltroHistorial.EGRESOS -> FakeData.movimientos.filter {
                    it.tipo == TipoMovimiento.EGRESO
                }
            }

            val grupos = movimientosFiltrados
                .groupBy { it.fecha.substringBefore(",").trim() }
                .map { (fecha, movs) ->
                    GrupoMovimientos(fecha = fecha, movimientos = movs)
                }

            _uiState.value = HistorialUiState.Success(
                filtroActual = filtro,
                grupos = grupos,
                cargando = false
            )
        }
    }
}