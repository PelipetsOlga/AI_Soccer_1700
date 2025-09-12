package com.application
/**
 * Sealed class representing all possible screens in the app
 */
sealed class Screen(val route: String) {
    data object Splash : Screen("splash_screen")
    data object Home : Screen("home_screen")
    
    // Future screens can be added here
    data object PlayerList : Screen("player_list_screen")
    data object TrainingList : Screen("training_list_screen")
    data object MatchList : Screen("match_list_screen")
}
