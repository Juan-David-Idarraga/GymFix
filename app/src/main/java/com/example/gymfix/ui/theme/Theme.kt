package com.example.gymfix.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.example.gymfix.ui.theme.Yellow
import com.example.gymfix.ui.theme.Orange
import com.example.gymfix.ui.theme.BackgroundGray

private val LightColors = lightColorScheme(
    primary = Yellow,
    secondary = Orange,
    tertiary = BackgroundGray
)

@Composable
fun GymFixTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography,
        content = content
    )
}
