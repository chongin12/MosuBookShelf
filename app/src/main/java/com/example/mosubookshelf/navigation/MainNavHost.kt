package com.example.mosubookshelf.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mosubookshelf.view.*

@Composable
fun MainNavHost(navController: NavHostController, modifier: Modifier = Modifier, viewModelStoreOwner: ViewModelStoreOwner) {
    NavHost(navController, startDestination = TopLevelRouteType.NAV_NEW_BOOKS.routeID, modifier) {
        composable(
            route = TopLevelRouteType.NAV_NEW_BOOKS.routeID,
        ) {
            NewBooksScreen(
                navigateToDetail = { isbn13 ->
                    navController.navigate(RouteType.BOOK_DETAIL.valuedPath(isbn13))
                },
                viewModel = hiltViewModel(viewModelStoreOwner),
            )
        }
        composable(
            route = TopLevelRouteType.NAV_SEARCH_BOOKS.routeID
        ) {
            SearchBooksScreen(
                navigateToDetail = { isbn13 ->
                    navController.navigate(RouteType.BOOK_DETAIL.valuedPath(isbn13))
                },
                viewModel = hiltViewModel(viewModelStoreOwner),
            )
        }
        composable(
            route = RouteType.BOOK_DETAIL.path,
            arguments = listOf(
                navArgument("isbn13") { type = NavType.StringType }
            )
        ) {
            BookDetailScreen()
        }
    }
}