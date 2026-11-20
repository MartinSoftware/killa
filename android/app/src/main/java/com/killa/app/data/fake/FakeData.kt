package com.killa.app.data.fake

import com.killa.app.domain.model.Cuenta
import com.killa.app.domain.model.Movimiento
import com.killa.app.domain.model.TipoMovimiento
import com.killa.app.domain.model.Usuario
import com.killa.app.domain.model.Banco

/**
 * Datos fake para desarrollo de UI.
 * Se reemplazarán por Repository + Retrofit cuando conectemos el backend.
 *
 * ⚠️ NO usar en producción. Solo para que las pantallas se vean con datos
 * mientras el backend de Killa está en desarrollo.
 */
object FakeData {

    val usuario = Usuario(
        id = "usr-001",
        nombres = "Ana Lucía",
        apellidos = "García Mendoza",
        correo = "ana@killa.pe",
        dni = "70458213",
        iniciales = "AG"
    )

    val cuenta = Cuenta(
        id = "cta-001",
        numeroCuenta = "KIL-4821-9032",
        numeroEnmascarado = "•• 4821",
        saldo = 2450.00,
        moneda = "PEN"
    )

    val movimientos = listOf(
        Movimiento(
            id = "mov-001",
            titulo = "Transferencia recibida",
            descripcion = "De Carlos M.",
            monto = 350.00,
            tipo = TipoMovimiento.INGRESO,
            fecha = "Hoy, 10:24"
        ),
        Movimiento(
            id = "mov-002",
            titulo = "Pago a DinoBit",
            descripcion = "Transferencia externa",
            monto = 120.00,
            tipo = TipoMovimiento.EGRESO,
            fecha = "Hoy, 08:15"
        ),
        Movimiento(
            id = "mov-003",
            titulo = "Transferencia a María L.",
            descripcion = "Interna",
            monto = 80.00,
            tipo = TipoMovimiento.EGRESO,
            fecha = "Ayer, 18:12"
        ),
        Movimiento(
            id = "mov-004",
            titulo = "Devolución",
            descripcion = "De DinoBit",
            monto = 45.00,
            tipo = TipoMovimiento.INGRESO,
            fecha = "Ayer, 12:40"
        ),
        Movimiento(
            id = "mov-005",
            titulo = "Depósito",
            descripcion = "Ventanilla",
            monto = 1000.00,
            tipo = TipoMovimiento.INGRESO,
            fecha = "15 nov, 09:00"
        )
    )

    val bancos = listOf(
        Banco(
            id = "dinobit",
            nombre = "DinoBit",
            descripcion = "Transferencia vía Hub Interbancario",
            disponible = true
        ),
        Banco(
            id = "bcp",
            nombre = "BCP",
            descripcion = "Integración pendiente",
            disponible = false
        ),
        Banco(
            id = "interbank",
            nombre = "Interbank",
            descripcion = "Integración pendiente",
            disponible = false
        )
    )
}