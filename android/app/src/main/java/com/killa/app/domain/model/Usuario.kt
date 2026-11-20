package com.killa.app.domain.model

data class Usuario(
    val id: String,
    val nombres: String,
    val apellidos: String,
    val correo: String,
    val dni: String,
    val iniciales: String
)