package com.example.gymfix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.gymfix.ui.AppNavigation   // 👈 import necesario
import com.example.gymfix.ui.theme.GymFixTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GymFixTheme {
                AppNavigation() // ahora sí la reconoce
            }
        }
    }
}
