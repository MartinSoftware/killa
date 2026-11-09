package com.killa.app.data.remote.interceptor

import com.killa.app.data.local.SessionDataStore
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * Añade el header `Authorization: Bearer <token>` a cada request
 * que no lo traiga ya. Usa runBlocking porque los interceptores de OkHttp
 * son síncronos; DataStore lee desde memoria y es rápido (<5ms típico).
 */
class AuthInterceptor @Inject constructor(
    private val sessionDataStore: SessionDataStore
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        if (original.header("Authorization") != null) {
            return chain.proceed(original)
        }

        val token = runBlocking { sessionDataStore.leerAccessToken() }
        val request = if (token != null) {
            original.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
        } else {
            original
        }
        return chain.proceed(request)
    }
}