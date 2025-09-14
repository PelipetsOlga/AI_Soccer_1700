package com.manager1700.soccer.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.manager1700.soccer.Screen
import com.manager1700.soccer.ui.feature_analytics.AnalyticsScreen
import com.manager1700.soccer.ui.feature_home.HomeScreen
import com.manager1700.soccer.ui.feature_match.MatchScreen
import com.manager1700.soccer.ui.feature_team.TeamScreen
import com.manager1700.soccer.ui.feature_training.TrainingScreen
import com.manager1700.soccer.ui.theme.SoccerManagerTheme
import com.manager1700.soccer.ui.utils.PreviewApp

data class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeWrapperScreen(
    mainNavController: NavController
) {
    val bottomNavController = rememberNavController()
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomNavItems = listOf(
        BottomNavItem(Screen.Team.route, Icons.Filled.Person, "Team"),
        BottomNavItem(Screen.Training.route, Icons.Filled.Settings, "Training"),
        BottomNavItem(Screen.Home.route, Icons.Filled.Home, "Home"),
        BottomNavItem(Screen.Match.route, Icons.Filled.Star, "Match"),
        BottomNavItem(Screen.Analytics.route, Icons.Filled.Info, "Analytics")
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                bottomNavItems.forEach { item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label
                            )
                        },
                        label = {
                            Text(text = item.label)
                        },
                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                        onClick = {
                            bottomNavController.navigate(item.route) {
                                popUpTo(bottomNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = bottomNavController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Team.route) {
                TeamScreen(
                    mainNavController = mainNavController,
                    bottomNavController = bottomNavController,
                )
            }
            composable(Screen.Training.route) {
                TrainingScreen(
                    mainNavController = mainNavController,
                    bottomNavController = bottomNavController,
                )
            }
            composable(Screen.Home.route) {
                HomeScreen(
                    mainNavController = mainNavController,
                    bottomNavController = bottomNavController,
                )
            }
            composable(Screen.Match.route) {
                MatchScreen(
                    mainNavController = mainNavController,
                    bottomNavController = bottomNavController,
                )
            }
            composable(Screen.Analytics.route) {
                AnalyticsScreen(
                    mainNavController = mainNavController,
                    bottomNavController = bottomNavController,
                )
            }
        }
    }
}

@PreviewApp
@Composable
fun HomeWrapperScreenPreview() {
    SoccerManagerTheme {
        val bottomNavController = rememberNavController()
        val bottomNavItems = listOf(
            BottomNavItem(Screen.Team.route, Icons.Filled.Person, "Team"),
            BottomNavItem(Screen.Training.route, Icons.Filled.Settings, "Training"),
            BottomNavItem(Screen.Home.route, Icons.Filled.Home, "Home"),
            BottomNavItem(Screen.Match.route, Icons.Filled.Star, "Match"),
            BottomNavItem(Screen.Analytics.route, Icons.Filled.Info, "Analytics")
        )

        Scaffold(
            bottomBar = {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.label
                                )
                            },
                            label = {
                                Text(text = item.label)
                            },
                            selected = item.route == Screen.Home.route,
                            onClick = { }
                        )
                    }
                }
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                HomeScreen(
                    mainNavController = rememberNavController(),
                    bottomNavController = rememberNavController(),
                    )
            }
        }
    }
}
