package com.killa.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.killa.app.ui.screens.HomePlaceholderScreen

@Composable
fun KillaNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Rutas.HOME   // temporal; cambiará a SPLASH
    ) {
        composable(Rutas.HOME) {
            HomePlaceholderScreen()
        }
    }
}