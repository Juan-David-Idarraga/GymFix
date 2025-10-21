package com.example.gymfix.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gymfix.ui.theme.Orange
import com.example.gymfix.ui.theme.White

@Composable
fun ProfileScreen(navController: NavController, currentRoute: String = "profile") {
    Scaffold(
        bottomBar = { BottomNavBar(navController, currentRoute) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
                .padding(paddingValues)
        ) {
            // Fondo diagonal naranja
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp) // un poco más grande
                    .align(Alignment.TopStart)
            ) {
                val path = Path().apply {
                    moveTo(0f, 0f)
                    lineTo(size.width, 0f)
                    lineTo(size.width, size.height * 0.6f)
                    lineTo(0f, size.height)
                    close()
                }
                drawPath(path = path, color = Orange)
            }

            // Contenido principal
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título
                Text(
                    "¡COMIENZA EL CAMBIO HOY!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(Modifier.height(20.dp))

                // Foto de perfil con círculo de fondo A MEJORAR
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape)
                        .background(White),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile Icon",
                        modifier = Modifier.size(70.dp),
                        tint = Orange
                    )
                }

                Spacer(Modifier.height(12.dp))
                Text("name.user", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color.DarkGray)

                Spacer(Modifier.height(24.dp))

                // Tarjeta de información IMC
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = White),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ProfileInfoRow("Tu suscripción:", "X")
                        ProfileInfoRow("Tu objetivo:", "X")

                        Divider(
                            modifier = Modifier.padding(vertical = 8.dp),
                            color = Color.LightGray,
                            thickness = 1.dp
                        )

                        ProfileInfoRow("Género:", "X")
                        ProfileInfoRow("Edad:", "X")
                        ProfileInfoRow("Peso:", "X")
                        ProfileInfoRow("Altura:", "X")

                        Spacer(Modifier.height(12.dp))

                        Text(
                            "IMC ACTUAL: X",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Orange
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))

                // Botón para IMC
                Text(
                    "¿Quieres cambiar tu IMC actual?",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Button(
                    onClick = { /* TODO: acción cambiar IMC */ },
                    modifier = Modifier
                        .width(220.dp)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Orange),
                    shape = RoundedCornerShape(50)
                ) {
                    Text("Sí, deseo cambiarlo", color = White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun ProfileInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Medium)
        Text(value, fontSize = 14.sp, color = Color.DarkGray)
    }
}
