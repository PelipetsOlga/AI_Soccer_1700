package com.manager1700.soccer.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.manager1700.soccer.R
import com.manager1700.soccer.Screen
import com.manager1700.soccer.ui.feature_analytics.AnalyticsScreen
import com.manager1700.soccer.ui.feature_home.HomeScreen
import com.manager1700.soccer.ui.feature_match.MatchScreen
import com.manager1700.soccer.ui.feature_team.TeamScreen
import com.manager1700.soccer.ui.feature_training.TrainingScreen
import com.manager1700.soccer.ui.theme.SoccerManagerTheme
import com.manager1700.soccer.ui.utils.PreviewApp
import com.manager1700.soccer.ui.utils.cardBrushDarkGradient
import com.manager1700.soccer.ui.utils.cardBrushLightGradient

data class BottomNavItem(
    val route: String,
    val iconUnselected: Int,
    val iconSelected: Int,
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
        BottomNavItem(Screen.Team.route, R.mipmap.menu_team, R.mipmap.menu_team_selected, "Team"),
        BottomNavItem(
            Screen.Training.route,
            R.mipmap.menu_training,
            R.mipmap.menu_training_selected,
            "Training"
        ),
        BottomNavItem(Screen.Home.route, R.mipmap.menu_home, R.mipmap.menu_home_selected, "Home"),
        BottomNavItem(
            Screen.Match.route,
            R.mipmap.menu_match,
            R.mipmap.menu_match_selected,
            "Match"
        ),
        BottomNavItem(
            Screen.Analytics.route,
            R.mipmap.menu_analytics,
            R.mipmap.menu_analytics_selected,
            "Analytics"
        )
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier
//                    .background(brush = cardBrushLightGradient)
//                    .padding(all = 1.dp)
                    .background(brush = cardBrushDarkGradient),
                containerColor = Color.Transparent,
                tonalElevation = 0.dp,
                contentColor = Color.Transparent,
            ) {
                bottomNavItems.forEach { item ->
                    val isSelected =
                        currentDestination?.hierarchy?.any { it.route == item.route } == true
                    NavigationBarItem(
                        icon = {
                            Image(
                                painter = painterResource(
                                    id = if (isSelected) item.iconSelected else item.iconUnselected
                                ),
                                contentDescription = item.label,
                                modifier = Modifier.size(if (item.route == Screen.Home.route) 64.dp else 48.dp)
                            )
                        },
                        selected = isSelected,
                        colors = NavigationBarItemDefaults.colors().copy(
                            selectedIconColor = Color.Transparent,
                            unselectedIconColor = Color.Transparent,
                            selectedIndicatorColor = Color.Transparent,
                            selectedTextColor = Color.Transparent,
                            unselectedTextColor = Color.Transparent,
                            disabledIconColor = Color.Transparent,
                            disabledTextColor = Color.Transparent,
                        ),
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
            BottomNavItem(
                Screen.Team.route,
                R.mipmap.menu_team,
                R.mipmap.menu_team_selected,
                "Team"
            ),
            BottomNavItem(
                Screen.Training.route,
                R.mipmap.menu_training,
                R.mipmap.menu_training_selected,
                "Training"
            ),
            BottomNavItem(
                Screen.Home.route,
                R.mipmap.menu_home,
                R.mipmap.menu_home_selected,
                "Home"
            ),
            BottomNavItem(
                Screen.Match.route,
                R.mipmap.menu_match,
                R.mipmap.menu_match_selected,
                "Match"
            ),
            BottomNavItem(
                Screen.Analytics.route,
                R.mipmap.menu_analytics,
                R.mipmap.menu_analytics_selected,
                "Analytics"
            )
        )

        Scaffold(
            bottomBar = {
                NavigationBar(
                    modifier = Modifier
//                        .padding(horizontal = 16.dp)
//                        .navBarBottomPadding()
//                        .clip(cardVeryBigClipShape)
//                        .background(brush = cardBrushLightGradient)
//                        .padding(all = 5.dp)
                        .background(brush = cardBrushDarkGradient),
                    containerColor = Color.Transparent,
                    tonalElevation = 0.dp,
                    contentColor = Color.Transparent,
                ) {
                    bottomNavItems.forEach { item ->
                        val isSelected = item.route == Screen.Home.route
                        NavigationBarItem(
                            icon = {
                                Image(
                                    painter = painterResource(
                                        id = if (isSelected) item.iconSelected else item.iconUnselected
                                    ),
                                    contentDescription = item.label,
                                    modifier = Modifier.size(if (item.route == Screen.Home.route) 64.dp else 48.dp)
                                )
                            },
                            selected = isSelected,
//                            colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
//                                selectedIconColor = Color.Transparent,
//                                unselectedIconColor = Color.Transparent,
//                                selectedIndicatorColor = Color.Transparent,
//                                indicatorColor = Color.Transparent
//                            ),
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
