package one.vitaliy.whatscooking

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import one.vitaliy.whatscooking.categories.list.CategoriesPage
import one.vitaliy.whatscooking.randommeal.RandomMealScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        Scaffold { paddingValues ->
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = RandomMealScreen) {
                composable<RandomMealScreen> {
                    RandomMealScreen(
                        paddingValues = paddingValues,
                        onNavigateToCategories = { navController.navigate(CategoriesPage) },
                    )
                }
                composable<CategoriesPage> {
                    CategoriesPage(
                        paddingValues = paddingValues,
//                        onCategoryClick = { navController.navigate(it) }
                    )
                }
            }
        }
    }
}
