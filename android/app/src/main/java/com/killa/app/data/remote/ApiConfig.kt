package com.killa.app.data.remote

import com.killa.app.BuildConfig

/**
 * Configuración global de la capa de red.
 * La URL viene de BuildConfig (inyectada desde gradle.properties).
 */
object ApiConfig {
    val BASE_URL: String = BuildConfig.API_BASE_URL

    /** Timeout en segundos para conexión y lectura. */
    const val TIMEOUT_SECONDS: Long = 30L
}