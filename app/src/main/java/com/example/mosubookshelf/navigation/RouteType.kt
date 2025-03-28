package com.example.mosubookshelf.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

val topLevelRoutes = TopLevelRouteType.entries

enum class TopLevelRouteType(val navName: String, val icon: ImageVector, val routeID: String) {
    NAV_NEW_BOOKS(
        navName = "New Books",
        icon = Icons.Rounded.ShoppingCart,
        routeID = "NavNewBooks"
    ),
    NAV_SEARCH_BOOKS(
        navName = "Search Books",
        icon = Icons.Rounded.Search,
        routeID = "NavSearchBooks"
    ),
}

enum class RouteType(private val routeID: String, vararg val args: String) {

    BOOK_DETAIL("BookDetail", "isbn13");

    fun valuedPath(vararg values: String): String {
        return listOf(routeID, *values).joinToString("/")
    }
    val route: String
        get() {
            var realPath = routeID
            args.forEach {
                realPath += "/{$it}"
            }
            return realPath
        }
}