package com.killa.app.domain.model

enum class TipoMovimiento {
    INGRESO,
    EGRESO
}

data class Movimiento(
    val id: String,
    val titulo: String,
    val descripcion: String,
    val monto: Double,
    val tipo: TipoMovimiento,
    val fecha: String
)