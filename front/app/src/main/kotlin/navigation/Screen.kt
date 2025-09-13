package com.manager1700.soccer
/**
 * Sealed class representing all possible screens in the app
 */
sealed class Screen(val route: String) {
    data object Splash : Screen("splash_screen")
    data object Welcome : Screen("welcome_screen")
    data object HomeWrapper : Screen("home_wrapper_screen")
    
    // Bottom navigation routes
    data object Team : Screen("team_screen")
    data object Training : Screen("training_screen")
    data object Home : Screen("home_screen")
    data object Match : Screen("match_screen")
    data object Analytics : Screen("analytics_screen")
    
    // Legacy screens (can be removed if not needed)
    data object PlayerList : Screen("player_list_screen")
    data object TrainingList : Screen("training_list_screen")
    data object MatchList : Screen("match_list_screen")
}
