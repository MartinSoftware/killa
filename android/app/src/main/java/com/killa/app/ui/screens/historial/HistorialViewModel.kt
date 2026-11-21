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
        cargarMovimientos(FiltroHistorial.TODOS)
    }

    fun onFiltroChange(filtro: FiltroHistorial) {
        cargarMovimientos(filtro)
    }

    private fun cargarMovimientos(filtro: FiltroHistorial) {
        viewModelScope.launch {
            _uiState.value = HistorialUiState.Loading
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

            // Agrupar por fecha (en FakeData la fecha ya es un string legible:
            // "Hoy, 10:24", "Ayer, 18:12", "15 nov, 09:00").
            // Tomamos la parte antes de la coma como "grupo".
            val grupos = movimientosFiltrados
                .groupBy { it.fecha.substringBefore(",").trim() }
                .map { (fecha, movs) ->
                    GrupoMovimientos(fecha = fecha, movimientos = movs)
                }

            _uiState.value = HistorialUiState.Success(
                filtroActual = filtro,
                grupos = grupos
            )
        }
    }
}