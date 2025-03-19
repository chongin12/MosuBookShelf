package com.example.mosubookshelfimport android.app.Applicationimport android.os.Bundleimport androidx.activity.ComponentActivityimport androidx.activity.compose.setContentimport androidx.activity.enableEdgeToEdgeimport androidx.compose.foundation.layout.*import androidx.compose.material3.*import androidx.compose.runtime.*import androidx.compose.ui.Alignmentimport androidx.compose.ui.Modifierimport androidx.compose.ui.tooling.preview.Previewimport androidx.hilt.navigation.compose.hiltViewModelimport androidx.navigation.*import androidx.navigation.compose.*import com.example.mosubookshelf.bookDetail.*import com.example.mosubookshelf.models.BottomNavigationItemTypeimport com.example.mosubookshelf.newbooks.*import com.example.mosubookshelf.repository.BookRepositoryimport com.example.mosubookshelf.repository.RemoteBookRepositoryimport com.example.mosubookshelf.ui.theme.MosuBookShelfThemeimport dagger.hilt.android.AndroidEntryPointimport dagger.hilt.android.HiltAndroidApp@HiltAndroidAppclass MosuBookShelfApplication : Application() {    override fun onCreate() {        super.onCreate()    }}@AndroidEntryPointclass MainActivity : ComponentActivity() {    private val bottomNavigationItems = BottomNavigationItemType.entries    val bookRepository: BookRepository = RemoteBookRepository()    override fun onCreate(savedInstanceState: Bundle?) {        super.onCreate(savedInstanceState)        enableEdgeToEdge()        setContent {            MosuBookShelfTheme {                val selectedItemState = remember { mutableStateOf(BottomNavigationItemType.NEW) }                val navController = rememberNavController()                Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {                    BottomAppBar {                        BottomNavigationBar(bottomNavigationItems, selectedItemState)                    }                }) { innerPadding ->                    Box(                        modifier = Modifier.padding(innerPadding),                        contentAlignment = Alignment.Center                    ) {                        when (selectedItemState.value) {                            BottomNavigationItemType.NEW -> NavHost(                                navController = navController,                                startDestination = "/newBooks"                            ) {                                composable(                                    route = "/newBooks"                                ) {                                    NewBooksScreen(                                        navigateToDetail = { isbn13 ->                                            navController.navigate("/book_detail/${isbn13}")                                        },                                    )                                }                                composable(                                    route = "/book_detail/{isbn13}",                                    arguments = listOf(navArgument("isbn13") { type = NavType.StringType }),                                ) { backStackEntry ->                                    val isbn13 = backStackEntry.arguments?.getString("isbn13") ?: return@composable                                    BookDetailScreen(isbn13 = isbn13)                                }                            }                            BottomNavigationItemType.SEARCH -> SearchBooksScreen()                        }                    }                }            }        }    }}@Preview(showBackground = true)@Composablefun NewBooksScreenPreview() {    val bottomNavigationItems = BottomNavigationItemType.entries    val selectedItemState = remember { mutableStateOf(BottomNavigationItemType.NEW) }    BottomNavigationBar(bottomNavigationItems, selectedItemState)}@Composablefun SearchBooksScreen(modifier: Modifier = Modifier) {    Text(        text = "SearchBooks screen",        modifier = modifier,    )}