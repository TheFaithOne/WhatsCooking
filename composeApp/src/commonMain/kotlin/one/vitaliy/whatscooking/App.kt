package one.vitaliy.whatscooking

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import one.vitaliy.whatscooking.categories.ui.CategoriesPage
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = RandomMeal) {
            composable<RandomMeal> {
                val viewModel = koinViewModel<RandomMealViewModel>()
                val state by viewModel.uiState.collectAsStateWithLifecycle()
                Column(modifier = Modifier.fillMaxSize().safeContentPadding()) {
                    state?.meals?.forEach {
                        Text("${it.strMeal}")
                        Text("${it.strTags}")

                        // Display ingredients
                        it.getIngredientsWithMeasures().forEach { (ingredient, measure) ->
                            Text("$ingredient: $measure")
                        }
                        Button(
                            onClick = { navController.navigate(CategoriesPage) }
                        ) {
                            Text("Go to Categories")
                        }
                    }
                }
            }
            composable<CategoriesPage> {
                CategoriesPage()
            }
        }
    }
}

@Serializable
object RandomMeal