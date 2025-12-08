package one.vitaliy.whatscooking.randommeal

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import kotlinx.serialization.Serializable
import one.vitaliy.whatscooking.networking.Meal
import one.vitaliy.whatscooking.ui.Strings
import one.vitaliy.whatscooking.ui.theme.WhatsCookingTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun RandomMealScreen(
    paddingValues: PaddingValues,
    onNavigateToCategories: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel = koinViewModel<RandomMealViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AnimatedContent(
        targetState = uiState,
        modifier = modifier.fillMaxSize().padding(paddingValues),
    ) { state ->
        when (state) {
            is RandomMealUiState.Loading -> CircularProgressIndicator(
                modifier = Modifier.fillMaxSize().wrapContentSize(),
            )

            is RandomMealUiState.Content -> RandomMealContent(
                meal = state.meal,
                onNavigateToCategories = onNavigateToCategories,
            )

            is RandomMealUiState.Error -> RandomMealError(
                throwable = state.throwable,
                onRetry = viewModel::getRandomMeal,
            )
        }
    }
}

@Composable
private fun RandomMealContent(
    meal: Meal,
    onNavigateToCategories: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        AsyncImage(
            model = meal.imageUrl,
            contentDescription = Strings.formatImageDescription(meal.name.orEmpty()),
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            text = meal.name.orEmpty(),
            style = WhatsCookingTheme.typography.headline.medium,
        )
        Text(
            text = meal.tags.orEmpty(),
            style = WhatsCookingTheme.typography.body.medium,
        )
        meal.getIngredientsWithMeasures().forEach { (ingredient, measure) ->
            Text(
                text = Strings.formatIngredient(ingredient, measure),
                style = WhatsCookingTheme.typography.body.medium,
            )
        }
        Button(
            onClick = onNavigateToCategories,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(Strings.RANDOM_MEAL_GO_TO_CATEGORIES)
        }
    }
}

@Composable
private fun RandomMealError(
    throwable: Throwable,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "${Strings.ERROR_GENERIC}\n${throwable.message}",
            style = WhatsCookingTheme.typography.body.medium,
            textAlign = TextAlign.Center,
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onRetry,
        ) {
            Text(Strings.ERROR_RETRY)
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
