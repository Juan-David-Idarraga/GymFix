package com.example.gymfix.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymfix.R
import com.example.gymfix.data.ApiClient
import com.example.gymfix.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit = {},
    onRecoverClick: () -> Unit = {},
    onCrewClick: () -> Unit = {}
) {
    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    var cargando by remember { mutableStateOf(false) }
    var msg by remember { mutableStateOf<String?>(null) }

    // Detectar si el Preview está activo
    val isPreview = androidx.compose.ui.platform.LocalInspectionMode.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .clip(RoundedCornerShape(20.dp))
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "Logo SmartFit",
                modifier = Modifier
                    .size(300.dp)
                    .padding(bottom = 32.dp)
            )

            // Usuario
            TextField(
                value = usuario,
                onValueChange = { usuario = it },
                label = { Text("Ingresar usuario") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(25.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Yellow,
                    unfocusedContainerColor = Yellow,
                    disabledContainerColor = Yellow,
                    cursorColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedLabelColor = Color.DarkGray,
                    unfocusedLabelColor = Color.Gray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Contraseña
            TextField(
                value = contrasena,
                onValueChange = { contrasena = it },
                label = { Text("Ingresar contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(25.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Yellow,
                    unfocusedContainerColor = Yellow,
                    disabledContainerColor = Yellow,
                    cursorColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedLabelColor = Color.DarkGray,
                    unfocusedLabelColor = Color.Gray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "recuperar contraseña",
                fontSize = 14.sp,
                color = Color.Blue,
                modifier = Modifier.clickable { onRecoverClick() }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botón Login
            Button(
                onClick = {
                    cargando = true
                    msg = null

                    scope.launch {
                        if (!isPreview) {
                            try {
                                val res = ApiClient.api.login(usuario, contrasena)


                                cargando = false

                                if (res.isSuccessful) {
                                    val body = res.body()

                                    if (body?.status == "success") {
                                        msg = "Bienvenido ${body.data?.name ?: ""}"
                                        onLoginClick()
                                    } else {
                                        msg = body?.message ?: "Error de credenciales"
                                    }

                                } else {
                                    msg = "Error servidor: ${res.code()}"
                                }

                            } catch (e: Exception) {
                                cargando = false
                                msg = "No conecta: ${e.localizedMessage}"
                            }
                        } else {
                            cargando = false
                            msg = "Vista previa (sin conexión)"
                        }
                    }
                },
                modifier = Modifier
                    .height(40.dp)
                    .clip(RoundedCornerShape(25.dp)),
                enabled = !cargando,
                colors = ButtonDefaults.buttonColors(containerColor = Orange)
            ) {
                Text(
                    if (cargando) "Ingresando..." else "Continuar",
                    color = White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Mensaje de estado
            if (!msg.isNullOrBlank()) {
                Spacer(Modifier.height(12.dp))
                Text(
                    msg!!,
                    color = if (msg!!.startsWith("Bienvenido"))
                        Color(0xFF1B5E20) else Color.Red
                )
            }
        }

        Text(
            text = "¿eres crew?",
            fontSize = 14.sp,
            color = Color.Blue,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .clickable { onCrewClick() }
        )
    }
}

// ⭐ PREVIEW FUNCIONAL ⭐
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewLogin() {
    LoginScreen()
}
