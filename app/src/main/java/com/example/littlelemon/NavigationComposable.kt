package com.example.littlelemon

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavigationComposable(navController: NavHostController) {
    val context = LocalContext.current
    val sharedPreferences = remember(context) { context.getSharedPreferences("LittleLemon", Context.MODE_PRIVATE) }
    val isUserLoggedIn = remember(sharedPreferences) { sharedPreferences.getBoolean("user_logged_in", false) }
    val startDestination = remember(isUserLoggedIn) { if (isUserLoggedIn) Home.route else Onboarding.route }

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(Onboarding.route) {
            Onboarding(navController = navController)
        }
        composable(Home.route) {
            Home(navController = navController)
        }
        composable(Profile.route) {
            Profile(navController = navController)
        }
    }
}

@Composable
fun MyNavigation(navController: NavHostController) {
    NavigationComposable(navController = navController)
}
