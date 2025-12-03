package one.vitaliy.whatscooking.randommeal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun RandomMealScreen(
    paddingValues: PaddingValues,
    onNavigateToCategories: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel = koinViewModel<RandomMealViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    Column(modifier = modifier.fillMaxSize().padding(paddingValues)) {
        state?.meals?.forEach {
            Text("${it.strMeal}")
            Text("${it.strTags}")

            it.getIngredientsWithMeasures().forEach { (ingredient, measure) ->
                Text("$ingredient: $measure")
            }
            Button(
                onClick = onNavigateToCategories,
            ) {
                Text("Go to Categories")
            }
        }
    }
}

@Serializable
object RandomMealScreen

@Preview
@Composable
private fun RandomMealScreenPreview() {
    RandomMealScreen(paddingValues = PaddingValues(), {})
}
