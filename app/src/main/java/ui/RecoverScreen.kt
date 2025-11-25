package com.example.gymfix.ui

import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymfix.R
import com.example.gymfix.ui.theme.*

@Composable
fun RecoverScreen(
    onSendClick: () -> Unit = {},
    onBackClick: () -> Unit = {}   // callback para volver
) {
    var email by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        //
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .align(Alignment.TopStart)
        ) {
            val path = Path().apply {
                moveTo(0f, 0f)
                lineTo(size.width, 0f)
                lineTo(0f, size.height)
                close()
            }
            drawPath(path = path, color = Orange)
        }

        //
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "Logo SmartFit",
                modifier = Modifier
                    .size(300.dp)
                    .padding(bottom = 40.dp)
            )

            // Input de correo
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Ingresar correo electrónico") },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
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

            Spacer(modifier = Modifier.height(30.dp))

            // Botón enviar
            Button(
                onClick = { onSendClick() },
                modifier = Modifier
                    .width(150.dp)
                    .height(45.dp)
                    .clip(RoundedCornerShape(25.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Orange),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
            ) {
                Text(
                    "Enviar",
                    color = White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // volver (darle diseño)
        Text(
            text = "Volver al login",
            fontSize = 14.sp,
            color = Color.Blue,
            modifier = Modifier
                .align(Alignment.BottomStart) // esquina inferior izquierda
                .padding(16.dp)
                .clickable { onBackClick() }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RecoverScreenPreview() {
    RecoverScreen(
        onSendClick = {},
        onBackClick = {}
    )
}

