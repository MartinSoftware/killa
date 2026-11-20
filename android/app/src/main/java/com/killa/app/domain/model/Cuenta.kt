package com.killa.app.domain.model

data class Cuenta(
    val id: String,
    val numeroCuenta: String,
    val numeroEnmascarado: String,
    val saldo: Double,
    val moneda: String
)