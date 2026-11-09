package com.killa.app.data.remote.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * Loguea respuestas HTTP no exitosas de forma centralizada.
 * Solo se añade en debug (ver NetworkModule).
 */
class ErrorInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (!response.isSuccessful) {
            Log.w("KillaNetwork", "HTTP ${response.code} en ${chain.request().url.encodedPath}")
        }
        return response
    }
}