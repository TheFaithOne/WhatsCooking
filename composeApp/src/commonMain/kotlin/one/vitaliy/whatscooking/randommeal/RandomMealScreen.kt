package one.vitaliy.whatscooking.randommeal

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.serialization.Serializable
import one.vitaliy.whatscooking.data.RecipeDomain
import one.vitaliy.whatscooking.ui.theme.WhatsCookingTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import whatscooking.composeapp.generated.resources.Res
import whatscooking.composeapp.generated.resources.error_generic
import whatscooking.composeapp.generated.resources.error_retry
import whatscooking.composeapp.generated.resources.random_meal_go_to_categories

private const val RANDOM_RECIPE_HERO_ICON_COUNT = 3

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
                recipe = state.recipe,
                onRefresh = viewModel::getRandomMeal,
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
    recipe: RecipeDomain,
    onRefresh: () -> Unit,
    onNavigateToCategories: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        RandomRecipeHero()
        Text(
            text = recipe.title,
            style = WhatsCookingTheme.typography.headline.medium,
        )
        if (recipe.servings.isNotBlank()) {
            Text(
                text = recipe.servings,
                style = WhatsCookingTheme.typography.body.medium,
            )
        }
        RecipeSection(title = "Ingredients") {
            recipe.ingredients.forEach { ingredient ->
                Text(
                    text = "• $ingredient",
                    style = WhatsCookingTheme.typography.body.medium,
                )
            }
        }
        RecipeSection(title = "Instructions") {
            Text(
                text = recipe.instructions,
                style = WhatsCookingTheme.typography.body.medium,
            )
        }
        OutlinedButton(
            onClick = onRefresh,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Surprise me again")
        }
        Button(
            onClick = onNavigateToCategories,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(stringResource(Res.string.random_meal_go_to_categories))
        }
    }
}

@Composable
private fun RandomRecipeHero(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.large,
            )
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            repeat(RANDOM_RECIPE_HERO_ICON_COUNT) {
                Text(
                    text = "🍽️",
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                            shape = CircleShape,
                        )
                        .wrapContentSize(),
                    textAlign = TextAlign.Center,
                )
            }
        }
        Text(
            text = "Random recipe",
            style = WhatsCookingTheme.typography.headline.medium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
        Text(
            text = "A fresh idea picked from API Ninjas recipe searches.",
            style = WhatsCookingTheme.typography.body.medium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}

@Composable
private fun RecipeSection(
    title: String,
    content: @Composable () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = title,
            style = WhatsCookingTheme.typography.headline.medium,
        )
        content()
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
            text = stringResource(Res.string.error_generic, throwable.message.orEmpty()),
            style = WhatsCookingTheme.typography.body.medium,
            textAlign = TextAlign.Center,
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onRetry,
        ) {
            Text(stringResource(Res.string.error_retry))
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
