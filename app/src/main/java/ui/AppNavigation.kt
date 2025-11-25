package com.example.gymfix.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController


@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        //Login
        composable("login") {
            LoginScreen(
                onLoginClick = { navController.navigate("home") },
                onRecoverClick = { navController.navigate("recover") },
                onCrewClick = { /* TODO */ }
            )
        }

        //Recuperar contraseña
        composable("recover") {
            RecoverScreen(
                onSendClick = { navController.popBackStack() }, // vuelve atrás después de enviar
                onBackClick = { navController.popBackStack() }   // botón volver
            )
        }

        //Home
        composable("home") {
            HomeScreen(navController = navController, currentRoute = "home")
        }

        //Dieta
        composable("diet") {
            DietScreen(navController = navController, currentRoute = "diet")
        }

        //Ejercicio
        composable("exercise") {
            ExerciseScreen(navController = navController, currentRoute = "exercise")
        }

        //Perfil
        composable("profile") {
            ProfileScreen(navController = navController, currentRoute = "profile")
        }
    }
}
