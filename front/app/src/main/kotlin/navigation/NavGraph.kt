package com.manager1700.soccer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.manager1700.soccer.Screen
import com.manager1700.soccer.ui.feature_splash.SplashScreen
import com.manager1700.soccer.ui.feature_welcome.WelcomeScreen
import com.manager1700.soccer.ui.feature_settings.SettingsScreen
import com.manager1700.soccer.ui.feature_add_edit_player.AddEditPlayerScreen
import com.manager1700.soccer.ui.feature_add_edit_training.AddEditTrainingScreen
import com.manager1700.soccer.ui.screens.HomeWrapperScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(navController = navController)
        }
        
        composable(route = Screen.HomeWrapper.route) {
            HomeWrapperScreen(mainNavController = navController)
        }
        
        composable(route = Screen.Settings.route) {
            SettingsScreen(navController = navController)
        }
        
        composable(route = Screen.AddPlayer.route) {
            AddEditPlayerScreen(
                isEditMode = false,
                navController = navController,
                player = null // For add player flow
            )
        }

        composable(route = Screen.EditPlayer.route) {
            AddEditPlayerScreen(
                isEditMode = true,
                navController = navController,
                player = null // For add player flow
            )
        }

        composable(route = Screen.AddTraining.route) {
            AddEditTrainingScreen(
                training = null, // For add training flow
                navController = navController
            )
        }

        composable(route = Screen.EditTraining.route) {
            AddEditTrainingScreen(
                training = null, // For edit training flow - would need to pass actual training
                navController = navController
            )
        }
        
        // Legacy screens (can be removed if not needed)
        // composable(route = Screen.PlayerList.route) {
        //     PlayerListScreen(navController = navController)
        // }
    }
}
