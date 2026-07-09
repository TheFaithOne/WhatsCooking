package one.vitaliy.whatscooking.mealdetail

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.serialization.Serializable
import one.vitaliy.whatscooking.compose.PreviewTheme
import one.vitaliy.whatscooking.data.RecipeDomain
import one.vitaliy.whatscooking.ui.theme.WhatsCookingTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import whatscooking.composeapp.generated.resources.Res
import whatscooking.composeapp.generated.resources.meal_detail_error_title
import whatscooking.composeapp.generated.resources.meal_detail_refresh

@Composable
internal fun MealDetailScreen(
    mealId: String,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
) {
    val viewModel = koinViewModel<MealDetailViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.getMealDetails(mealId)
    }
    MealDetail(
        uiState = uiState,
        onRefresh = { viewModel.refresh(mealId) },
        modifier = modifier.padding(paddingValues),
    )
}

@Composable
private fun MealDetail(
    uiState: MealDetailUiState,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedContent(
        targetState = uiState,
        modifier = modifier.fillMaxSize(),
    ) {
        when (it) {
            is MealDetailUiState.Loading -> CircularProgressIndicator(
                modifier = Modifier.fillMaxSize().wrapContentHeight(),
            )

            is MealDetailUiState.Content -> MealDetailContent(uiState = it)
            is MealDetailUiState.Error -> MealDetailError(uiState = it, onRefresh = onRefresh)
        }
    }
}

@Composable
private fun MealDetailContent(
    uiState: MealDetailUiState.Content,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()).fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = uiState.recipe.title,
            style = WhatsCookingTheme.typography.headline.medium,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        )
        MealDetails(uiState.recipe, modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp))
        Text(
            text = uiState.recipe.instructions,
            style = WhatsCookingTheme.typography.body.medium,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        )
    }
}

@Composable
private fun MealDetails(
    recipe: RecipeDomain,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = recipe.servings,
                style = WhatsCookingTheme.typography.body.medium,
            )
        }
        Spacer(Modifier.height(16.dp))
        recipe.ingredients.forEach { ingredient ->
            Text(
                text = "• $ingredient",
                style = WhatsCookingTheme.typography.body.medium,
            )
        }
    }
}

@Composable
private fun MealDetailError(
    uiState: MealDetailUiState.Error,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(Res.string.meal_detail_error_title),
            style = WhatsCookingTheme.typography.headline.large,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
        )
        Text(
            text = uiState.throwable.message.orEmpty(),
            style = WhatsCookingTheme.typography.body.medium,
            textAlign = TextAlign.Center,
        )
        Button(
            onClick = onRefresh,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(stringResource(Res.string.meal_detail_refresh))
        }
    }
}

@Preview
@Composable
private fun MealDetailPreview() {
    PreviewTheme {
        MealDetailScreen(paddingValues = PaddingValues(), mealId = "")
    }
}

@Serializable
data class MealDetailScreenRoute(val mealId: String)
