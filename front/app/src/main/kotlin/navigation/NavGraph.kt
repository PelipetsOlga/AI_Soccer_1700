package com.manager1700.soccer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.manager1700.soccer.Screen
import com.manager1700.soccer.ui.feature_splash.SplashScreen
import com.manager1700.soccer.ui.screens.HomeScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        
        // Future screens can be added here
        // composable(route = Screen.PlayerList.route) {
        //     PlayerListScreen(navController = navController)
        // }
    }
}
