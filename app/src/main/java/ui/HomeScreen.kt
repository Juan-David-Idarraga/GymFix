package com.example.gymfix.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gymfix.ui.theme.GymFixTheme
import com.example.gymfix.ui.theme.Orange
import com.example.gymfix.ui.theme.White
import com.example.gymfix.data.ApiClient
import com.example.gymfix.data.FormClientResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    currentRoute: String = "home",
    onContinueClick: () -> Unit = {}
) {
    var genero by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    var altura by remember { mutableStateOf("") }
    var objetivo by remember { mutableStateOf("") }

    val generos = listOf("Hombre", "Mujer")
    val objetivos = listOf("Bajar de peso", "Mantenerme", "definicion")

    // Controladores expandido o no
    var generoExpanded by remember { mutableStateOf(false) }
    var objetivoExpanded by remember { mutableStateOf(false) }



    Scaffold(
        bottomBar = { BottomNavBar(navController, currentRoute) }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
                .padding(paddingValues)
        ) {

            // Franja diagonal naranja
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .align(Alignment.TopStart)
            ) {
                val path = Path().apply {
                    moveTo(0f, 0f)
                    lineTo(size.width, 0f)
                    lineTo(size.width, size.height * 0.8f)
                    lineTo(0f, size.height)
                    close()
                }
                drawPath(path = path, color = Orange)
            }

            // Caja central
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFFFE0B2))
                    .padding(24.dp)
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    "¡COMIENZA EL CAMBIO HOY!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    "Ingresa tus datos para que podamos entregarte tu índice de masa corporal (IMC)",
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(bottom = 16.dp),
                    lineHeight = 18.sp
                )

                // ------------------
                // COMBOBOX GÉNERO
                // ------------------
                ExposedDropdownMenuBox(
                    expanded = generoExpanded,
                    onExpandedChange = { generoExpanded = !generoExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = genero,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Selecciona tu género") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = generoExpanded)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ExposedDropdownMenuDefaults.textFieldColors()
                    )

                    ExposedDropdownMenu(
                        expanded = generoExpanded,
                        onDismissRequest = { generoExpanded = false }
                    ) {
                        generos.forEach { opcion ->
                            DropdownMenuItem(
                                text = { Text(opcion) },
                                onClick = {
                                    genero = opcion
                                    generoExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Edad input
                OutlinedTextField(
                    value = edad,
                    onValueChange = { edad = it },
                    label = { Text("Ingresa tu edad") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(Modifier.height(12.dp))

                // Peso
                OutlinedTextField(
                    value = peso,
                    onValueChange = { peso = it },
                    label = { Text("Ingresa tu peso (kg)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(Modifier.height(12.dp))

                // Altura
                OutlinedTextField(
                    value = altura,
                    onValueChange = { altura = it },
                    label = { Text("Ingresa tu altura (m)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(Modifier.height(12.dp))

                // ------------------
                // COMBOBOX OBJETIVO
                // ------------------
                ExposedDropdownMenuBox(
                    expanded = objetivoExpanded,
                    onExpandedChange = { objetivoExpanded = !objetivoExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = objetivo,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Selecciona tu objetivo") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = objetivoExpanded)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ExposedDropdownMenuDefaults.textFieldColors()
                    )

                    ExposedDropdownMenu(
                        expanded = objetivoExpanded,
                        onDismissRequest = { objetivoExpanded = false }
                    ) {
                        objetivos.forEach { opcion ->
                            DropdownMenuItem(
                                text = { Text(opcion) },
                                onClick = {
                                    objetivo = opcion
                                    objetivoExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))

                // Botón guardar
                Button(
                    onClick = { onContinueClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Orange)
                ) {
                    Text("guardar", color = White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    GymFixTheme {
        HomeScreen(navController = navController)
    }
}
