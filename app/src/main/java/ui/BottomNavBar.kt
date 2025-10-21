package com.example.gymfix.ui

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.gymfix.ui.theme.Orange

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Logout : BottomNavItem("login", Icons.Default.ExitToApp, "Salir")
    object Diet : BottomNavItem("diet", Icons.Default.Restaurant, "Dieta")
    object Home : BottomNavItem("home", Icons.Default.Home, "Home")
    object Exercise : BottomNavItem("exercise", Icons.Default.FitnessCenter, "Ejercicio")
    object Profile : BottomNavItem("profile", Icons.Default.Person, "Perfil")
}

@Composable
fun BottomNavBar(navController: NavController, currentRoute: String) {
    val items = listOf(
        BottomNavItem.Logout,
        BottomNavItem.Diet,
        BottomNavItem.Home,
        BottomNavItem.Exercise,
        BottomNavItem.Profile
    )

    NavigationBar(containerColor = Orange) {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (item is BottomNavItem.Logout) {
                        //Cierra sesión y limpia backstack
                        navController.navigate("login") {
                            popUpTo(0) { inclusive = true }
                        }
                    } else if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo("home") { inclusive = false }
                            launchSingleTop = true
                        }
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}
