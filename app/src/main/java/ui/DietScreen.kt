package com.example.gymfix.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.gymfix.R
import com.example.gymfix.ui.theme.Orange
import com.example.gymfix.ui.theme.White

@Composable
fun DietScreen(navController: NavController, currentRoute: String = "diet") {
    Scaffold(
        bottomBar = { BottomNavBar(navController, currentRoute) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "¡DIETAS Y ALIMENTOS PARA TI!",
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.height(16.dp))

            DietCard(android.R.drawable.ic_menu_gallery, "Dieta cetogénica (Keto)")
            DietCard(android.R.drawable.ic_menu_gallery, "Dieta DASH")
            DietCard(android.R.drawable.ic_menu_gallery, "Dieta baja en carbohidratos")
        }
    }
}

@Composable
fun DietCard(imageRes: Int, title: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE0B2))
    ) {
        Column {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Crop
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(title, fontSize = 14.sp, color = Color.Black)
                Button(
                    onClick = { /* TODO: acción ver más */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Orange),
                    shape = RoundedCornerShape(50)
                ) {
                    Text("Ver más", color = White, fontSize = 12.sp)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DietScreenPreview() {
    val navController = rememberNavController()
    DietScreen(navController = navController, currentRoute = "diet")
}
