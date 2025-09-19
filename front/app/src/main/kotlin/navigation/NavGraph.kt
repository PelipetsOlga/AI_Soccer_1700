package com.manager1700.soccer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.manager1700.soccer.Screen
import com.manager1700.soccer.ui.feature_splash.SplashScreen
import com.manager1700.soccer.ui.feature_welcome.WelcomeScreen
import com.manager1700.soccer.ui.feature_settings.SettingsScreen
import com.manager1700.soccer.ui.feature_add_edit_player.AddEditPlayerScreen
import com.manager1700.soccer.ui.feature_add_edit_training.AddEditTrainingScreen
import com.manager1700.soccer.ui.feature_training_details.TrainingDetailsScreen
import com.manager1700.soccer.ui.feature_add_edit_match.AddEditMatchScreen
import com.manager1700.soccer.ui.feature_match_details.MatchDetailsScreen
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
            )
        }

        composable(
            route = Screen.EditPlayer.route,
            arguments = listOf(
                navArgument("playerId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val playerId = backStackEntry.arguments?.getInt("playerId") ?: 0
            AddEditPlayerScreen(
                isEditMode = true,
                navController = navController,
                playerId = playerId
            )
        }

        composable(route = Screen.AddTraining.route) {
            AddEditTrainingScreen(
                training = null, // For add training flow
                navController = navController
            )
        }

        composable(
            route = Screen.EditTraining.route,
            arguments = listOf(
                navArgument("trainingId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val trainingId = backStackEntry.arguments?.getInt("trainingId") ?: 0
            AddEditTrainingScreen(
                training = null, // Training will be loaded by the ViewModel using trainingId
                navController = navController,
                trainingId = trainingId
            )
        }

        composable(
            route = Screen.TrainingDetails.route,
            arguments = listOf(
                navArgument("trainingId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val trainingId = backStackEntry.arguments?.getInt("trainingId") ?: 0
            TrainingDetailsScreen(
                trainingId = trainingId,
                navController = navController
            )
        }

        composable(route = Screen.AddMatch.route) {
            AddEditMatchScreen(
                match = null, // For add match flow
                navController = navController
            )
        }

        composable(
            route = Screen.EditMatch.route,
            arguments = listOf(
                navArgument("matchId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val matchId = backStackEntry.arguments?.getInt("matchId") ?: 0
            AddEditMatchScreen(
                match = null, // Match will be loaded by the ViewModel using matchId
                navController = navController,
                matchId = matchId
            )
        }

        composable(
            route = Screen.MatchDetails.route,
            arguments = listOf(
                navArgument("matchId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val matchId = backStackEntry.arguments?.getInt("matchId") ?: 0
            MatchDetailsScreen(
                matchId = matchId,
                navController = navController
            )
        }
        
        // Legacy screens (can be removed if not needed)
        // composable(route = Screen.PlayerList.route) {
        //     PlayerListScreen(navController = navController)
        // }
    }
}
