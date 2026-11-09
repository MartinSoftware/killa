package com.killa.app.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

// Delegado de DataStore a nivel de archivo: una sola instancia por proceso.
private val Context.sessionDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "killa_session"
)

/**
 * Persiste los datos de sesión del usuario: tokens JWT e id de usuario.
 * Los tokens nunca se loguean ni se imprimen.
 */
@Singleton
class SessionDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object Keys {
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val USUARIO_ID = stringPreferencesKey("usuario_id")
    }

    private val dataStore: DataStore<Preferences> = context.sessionDataStore

    private val preferences: Flow<Preferences> = dataStore.data
        .catch { e -> if (e is IOException) emit(emptyPreferences()) else throw e }

    val accessToken: Flow<String?> = preferences.map { it[Keys.ACCESS_TOKEN] }
    val refreshToken: Flow<String?> = preferences.map { it[Keys.REFRESH_TOKEN] }
    val usuarioId: Flow<String?> = preferences.map { it[Keys.USUARIO_ID] }

    suspend fun guardarSesion(
        accessToken: String,
        refreshToken: String,
        usuarioId: String
    ) {
        dataStore.edit { prefs ->
            prefs[Keys.ACCESS_TOKEN] = accessToken
            prefs[Keys.REFRESH_TOKEN] = refreshToken
            prefs[Keys.USUARIO_ID] = usuarioId
        }
    }

    suspend fun actualizarAccessToken(accessToken: String) {
        dataStore.edit { prefs -> prefs[Keys.ACCESS_TOKEN] = accessToken }
    }

    suspend fun limpiarSesion() {
        dataStore.edit { it.clear() }
    }

    suspend fun leerAccessToken(): String? = accessToken.first()
}