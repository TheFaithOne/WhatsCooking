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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import kotlinx.serialization.Serializable
import one.vitaliy.whatscooking.compose.PreviewTheme
import one.vitaliy.whatscooking.networking.MealDto
import one.vitaliy.whatscooking.ui.theme.WhatsCookingTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import whatscooking.composeapp.generated.resources.Res
import whatscooking.composeapp.generated.resources.meal_detail_error_title
import whatscooking.composeapp.generated.resources.meal_detail_refresh
import whatscooking.composeapp.generated.resources.meal_detail_watch_youtube

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
        AsyncImage(
            model = uiState.mealDto.imageUrl,
            contentDescription = uiState.mealDto.name,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            text = uiState.mealDto.name.orEmpty(),
            style = WhatsCookingTheme.typography.headline.medium,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        )
        MealDetails(uiState.mealDto, modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp))
        Text(
            text = uiState.mealDto.instructions.orEmpty(),
            style = WhatsCookingTheme.typography.body.medium,
        )
        if (!uiState.mealDto.youtubeUrl.isNullOrBlank()) {
            val uriHandler = LocalUriHandler.current
            Button(
                onClick = {
                    uriHandler.openUri(uiState.mealDto.youtubeUrl)
                },
            ) {
                Text(
                    stringResource(Res.string.meal_detail_watch_youtube),
                    style = WhatsCookingTheme.typography.label.medium,
                )
            }
        }
    }
}

@Composable
private fun MealDetails(
    mealDto: MealDto,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = mealDto.tags.orEmpty(),
                style = WhatsCookingTheme.typography.body.medium,
            )
            Text(
                text = mealDto.areaOfOrigin.orEmpty(),
                style = WhatsCookingTheme.typography.body.medium,
            )
        }
        Spacer(Modifier.height(16.dp))
        mealDto.getIngredientsWithMeasures().forEach { (ingredient, measure) ->
            Text(
                text = "• $ingredient: $measure",
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
