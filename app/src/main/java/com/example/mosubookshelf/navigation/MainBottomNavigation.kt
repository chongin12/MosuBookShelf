package com.example.mosubookshelf.navigation

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun MainBottomNavigation(navController: NavController) {
    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.Magenta,
        modifier = Modifier
            .navigationBarsPadding()
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        topLevelRoutes.forEach { topLevelRouteType ->
            BottomNavigationItem(
                icon = { Icon(topLevelRouteType.icon, contentDescription = topLevelRouteType.name) },
                label = { Text(topLevelRouteType.navName) },
                selected = currentRoute == topLevelRouteType.routeID,
                onClick = {
                    navController.navigate(topLevelRouteType.routeID) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                },
                alwaysShowLabel = false,
            )
        }
    }
}