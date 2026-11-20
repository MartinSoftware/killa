package com.killa.app.domain.model

data class Banco(
    val id: String,
    val nombre: String,
    val descripcion: String,
    val disponible: Boolean
)