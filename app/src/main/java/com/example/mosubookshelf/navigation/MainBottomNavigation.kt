package com.example.mosubookshelf.navigation

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun MainBottomNavigation(navController: NavController) {
    var selectedRoute by remember { mutableStateOf(topLevelRoutes.first().routeID) }
    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.Magenta,
        modifier = Modifier
            .navigationBarsPadding()
    ) {
        topLevelRoutes.forEach { topLevelRouteType ->
            BottomNavigationItem(
                icon = { Icon(topLevelRouteType.icon, contentDescription = topLevelRouteType.name) },
                label = { Text(topLevelRouteType.navName) },
                selected = selectedRoute == topLevelRouteType.routeID,
                onClick = {
                    if (selectedRoute == topLevelRouteType.routeID) {
                        navController.popBackStack(topLevelRouteType.routeID, inclusive = false)
                    } else {
                        selectedRoute = topLevelRouteType.routeID
                        navController.navigate(topLevelRouteType.routeID) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                alwaysShowLabel = false,
            )
        }
    }
}