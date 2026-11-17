package com.example.littlelemon

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun Profile(
    navController: NavHostController = rememberNavController(),
) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("LittleLemon", Context.MODE_PRIVATE)

    val firstName = sharedPreferences.getString("firstName", "") ?: ""
    val lastName = sharedPreferences.getString("lastName", "") ?: ""
    val email = sharedPreferences.getString("email", "") ?: ""

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Little Lemon Logo",
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(vertical = 12.dp),
        )

        Text(
            text = "Profile information:",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 40.dp, end = 16.dp, bottom = 20.dp),
        )

        Text(
            text = "First name",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 4.dp),
        )
        OutlinedTextField(
            value = firstName,
            onValueChange = {},
            readOnly = true,
            label = { Text("First name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )

        Text(
            text = "Last name",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 4.dp),
        )
        OutlinedTextField(
            value = lastName,
            onValueChange = {},
            readOnly = true,
            label = { Text("Last name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )

        Text(
            text = "Email",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 4.dp),
        )
        OutlinedTextField(
            value = email,
            onValueChange = {},
            readOnly = true,
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )

        Button(
            onClick = {
                sharedPreferences.edit { clear() }
                navController.navigate(Onboarding.route) {
                    popUpTo(Home.route) { inclusive = true }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 40.dp, end = 16.dp, bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF4CE14),
            ),
        ) {
            Text(
                text = "Log out",
                color = Color(0xFF333333),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    LittleLemonTheme {
        Profile()
    }
}
