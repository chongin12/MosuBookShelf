package com.example.mosubookshelfimport android.app.Applicationimport android.os.Bundleimport androidx.activity.ComponentActivityimport androidx.activity.compose.setContentimport androidx.activity.enableEdgeToEdgeimport androidx.compose.foundation.layout.*import androidx.compose.material.BottomNavigationimport androidx.compose.material.BottomNavigationItemimport androidx.compose.material.icons.Iconsimport androidx.compose.material.icons.rounded.Searchimport androidx.compose.material.icons.rounded.ShoppingCartimport androidx.compose.material3.*import androidx.compose.runtime.*import androidx.compose.ui.Modifierimport androidx.compose.ui.graphics.Colorimport androidx.compose.ui.graphics.vector.ImageVectorimport androidx.compose.ui.tooling.preview.Previewimport androidx.hilt.navigation.compose.hiltViewModelimport androidx.navigation.NavGraph.Companion.findStartDestinationimport androidx.navigation.compose.*import com.example.mosubookshelf.bookDetail.*import com.example.mosubookshelf.models.BottomNavigationItemTypeimport com.example.mosubookshelf.newbooks.*import com.example.mosubookshelf.searchBooks.SearchBooksScreenimport com.example.mosubookshelf.ui.theme.MosuBookShelfThemeimport dagger.hilt.android.AndroidEntryPointimport dagger.hilt.android.HiltAndroidApp@HiltAndroidAppclass MosuBookShelfApplication : Application()@AndroidEntryPointclass MainActivity : ComponentActivity() {    override fun onCreate(savedInstanceState: Bundle?) {        super.onCreate(savedInstanceState)        enableEdgeToEdge()        setContent {            MosuBookShelfTheme {                val navController = rememberNavController()                Scaffold(                    modifier = Modifier.fillMaxSize(),                    bottomBar = {                        BottomNavigation(                            backgroundColor = Color.White,                            contentColor = Color.Magenta,                        ) {                            val navBackStackEntry by navController.currentBackStackEntryAsState()                            val currentRoute = navBackStackEntry?.destination?.route                            topLevelRoutes.forEach { topLevelRouteType ->                                BottomNavigationItem(                                    icon = { Icon(topLevelRouteType.icon, contentDescription = topLevelRouteType.name) },                                    label = { Text(topLevelRouteType.navName) },                                    selected = currentRoute == topLevelRouteType.routeID,                                    onClick = {                                        navController.navigate(topLevelRouteType.routeID) {                                            popUpTo(navController.graph.findStartDestination().id) {                                                saveState = true                                            }                                            launchSingleTop = true                                            restoreState = true                                        }                                    },                                    alwaysShowLabel = false,                                )                            }                        }                    },                ) { innerPadding ->                    NavHost(navController, startDestination = TopLevelRouteType.NAV_NEW_BOOKS.routeID, Modifier.padding(innerPadding)) {                        composable(                            route = TopLevelRouteType.NAV_NEW_BOOKS.routeID,                        ) {                            NewBooksScreen(                                navigateToDetail = { isbn13 ->                                    navController.navigate(RouteType.BOOK_DETAIL.valuedPath(isbn13))                                },                                viewModel = hiltViewModel(this@MainActivity),                            )                        }                        composable(                            route = TopLevelRouteType.NAV_SEARCH_BOOKS.routeID                        ) {                            SearchBooksScreen(                                navigateToDetail = { isbn13 ->                                    navController.navigate(RouteType.BOOK_DETAIL.valuedPath(isbn13))                                },                                viewModel = hiltViewModel(this@MainActivity),                            )                        }                        composable(                            route = RouteType.BOOK_DETAIL.path                        ) { backStackEntry ->                            val arg = RouteType.BOOK_DETAIL.args.first()                            val isbn13 = backStackEntry.arguments?.getString(arg) ?: return@composable                            BookDetailScreen(isbn13 = isbn13)                        }                    }                }            }        }    }}val topLevelRoutes = TopLevelRouteType.entriesenum class TopLevelRouteType(val navName: String, val icon: ImageVector, val routeID: String) {    NAV_NEW_BOOKS(        navName = "New Books",        icon = Icons.Rounded.ShoppingCart,        routeID = "NavNewBooks"    ),    NAV_SEARCH_BOOKS(        navName = "Search Books",        icon = Icons.Rounded.Search,        routeID = "NavSearchBooks"    ),}enum class RouteType(private val routeID: String, vararg val args: String) {    BOOK_DETAIL("BookDetail", "isbn13");    fun valuedPath(vararg values: String): String {        return listOf(routeID, *values).joinToString("/")    }    val path: String        get() {            var realPath = routeID            args.forEach {                realPath += "/{$it}"            }            return realPath        }}@Preview(showBackground = true)@Composablefun NewBooksScreenPreview() {    val bottomNavigationItems = BottomNavigationItemType.entries    val selectedItemState = remember { mutableStateOf(BottomNavigationItemType.NEW) }    BottomNavigationBar(bottomNavigationItems, selectedItemState)}