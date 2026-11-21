package com.killa.app.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.killa.app.ui.screens.login.LoginScreen
import com.killa.app.ui.screens.splash.SplashScreen

@Composable
fun KillaNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Rutas.SPLASH
    ) {
        composable(Rutas.SPLASH) {
            SplashScreen(
                onIrALogin = {
                    navController.navigate(Rutas.LOGIN) {
                        popUpTo(Rutas.SPLASH) { inclusive = true }
                    }
                },
                onIrAHome = {
                    navController.navigate(Rutas.HOME) {
                        popUpTo(Rutas.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        composable(Rutas.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Rutas.HOME) {
                        popUpTo(Rutas.LOGIN) { inclusive = true }
                    }
                },
                onIrARegistro = { navController.navigate(Rutas.REGISTRO) }
            )
        }

        // Resto de rutas con placeholders (se reemplazan en próximos bloques)
        composable(Rutas.REGISTRO) {
            PlaceholderScreen(
                titulo = "Registro",
                descripcion = "Flujo multi-paso",
                onContinuar = {
                    navController.navigate(Rutas.HOME) {
                        popUpTo(Rutas.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Rutas.HOME) {
            PlaceholderScreen(
                titulo = "Home",
                descripcion = "Saldo + accesos rápidos",
                onContinuar = { navController.navigate(Rutas.HISTORIAL) }
            )
        }

        composable(Rutas.HISTORIAL) {
            PlaceholderScreen(
                titulo = "Historial",
                descripcion = "Lista de movimientos",
                onContinuar = { navController.popBackStack() }
            )
        }

        composable(Rutas.PERFIL) {
            PlaceholderScreen(
                titulo = "Perfil",
                descripcion = "Datos del usuario + logout",
                onContinuar = { navController.popBackStack() }
            )
        }

        composable(Rutas.TRANSFERIR) {
            PlaceholderScreen(
                titulo = "Transferir",
                descripcion = "Interno / externo vía Hub",
                onContinuar = { navController.popBackStack() }
            )
        }
    }
}

@Composable
private fun PlaceholderScreen(
    titulo: String,
    descripcion: String,
    onContinuar: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Killa",
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = titulo,
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = descripcion,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
            )
            Button(onClick = onContinuar) {
                Text("Continuar")
            }
        }
    }
}