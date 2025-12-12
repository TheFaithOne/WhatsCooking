package one.vitaliy.whatscooking.homepage

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import kotlinx.serialization.Serializable
import one.vitaliy.whatscooking.compose.PreviewTheme
import one.vitaliy.whatscooking.data.MealDomain
import one.vitaliy.whatscooking.ui.composables.MealCard
import one.vitaliy.whatscooking.ui.theme.WhatsCookingTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider
import org.koin.compose.viewmodel.koinViewModel
import whatscooking.composeapp.generated.resources.Res
import whatscooking.composeapp.generated.resources.error_generic
import whatscooking.composeapp.generated.resources.error_retry
import whatscooking.composeapp.generated.resources.homepage_recently_added_recipes_subtitle
import whatscooking.composeapp.generated.resources.homepage_recently_added_recipes_title

@Serializable
object HomepageScreen

@Composable
internal fun HomepageScreen(
    navigateToMealDetail: (String) -> Unit,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
) {
    val viewModel = koinViewModel<HomepageViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomepageContainer(
        uiState,
        onRefreshClick = viewModel::refresh,
        onAddToFavouriteCLick = viewModel::addMealToFavourites,
        onMealClicked = navigateToMealDetail,
        modifier = modifier.fillMaxSize().padding(paddingValues),
    )
}

@Composable
private fun HomepageContainer(
    uiState: HomepageUiState,
    onRefreshClick: () -> Unit,
    onMealClicked: (String) -> Unit,
    onAddToFavouriteCLick: (MealDomain) -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedContent(
        uiState,
        modifier = modifier,
    ) {
        when (it) {
            is HomepageUiState.Content -> HomepageContent(
                uiState = it,
                onMealClicked = onMealClicked,
                onAddToFavouriteCLick = onAddToFavouriteCLick,
            )

            is HomepageUiState.Error -> HomepageError(
                onRefreshClick = onRefreshClick,
                uiState = it,
            )

            HomepageUiState.Loading -> CircularProgressIndicator(
                modifier = Modifier.fillMaxSize().wrapContentSize(),
            )
        }
    }
}

@Composable
private fun HomepageContent(
    uiState: HomepageUiState.Content,
    onMealClicked: (String) -> Unit,
    onAddToFavouriteCLick: (MealDomain) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        RecentlyAddedRow(
            recentlyAdded = uiState.latestMeals,
            onMealClicked = onMealClicked,
            onAddToFavouriteCLick = onAddToFavouriteCLick,
        )
    }
}

@Composable
private fun RecentlyAddedRow(
    recentlyAdded: List<MealDomain>,
    onMealClicked: (String) -> Unit,
    onAddToFavouriteCLick: (MealDomain) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = stringResource(Res.string.homepage_recently_added_recipes_title),
            style = WhatsCookingTheme.typography.headline.medium,
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        Text(
            text = stringResource(Res.string.homepage_recently_added_recipes_subtitle),
            style = WhatsCookingTheme.typography.label.medium,
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            items(items = recentlyAdded, key = { it.id }) {
                MealCard(
                    it,
                    modifier = Modifier.animateItem(),
                    onMealClicked = onMealClicked,
                    onAddToFavouriteCLick = onAddToFavouriteCLick,
                )
            }
        }
    }
}

@Composable
private fun HomepageError(
    onRefreshClick: () -> Unit,
    uiState: HomepageUiState.Error,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(Res.string.error_generic, uiState.throwable.message.orEmpty()),
            style = WhatsCookingTheme.typography.body.medium,
            textAlign = TextAlign.Center,
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onRefreshClick,
        ) {
            Text(stringResource(Res.string.error_retry))
        }
    }
}

@Preview
@Composable
private fun HomepagePreview(
    @PreviewParameter(HomepagePreviewProvider::class) uiState: HomepageUiState,
) {
    PreviewTheme {
        HomepageContainer(
            uiState,
            onRefreshClick = {},
            onMealClicked = {},
            onAddToFavouriteCLick = {},
        )
    }
}

private class HomepagePreviewProvider : PreviewParameterProvider<HomepageUiState> {
    override val values: Sequence<HomepageUiState> = sequenceOf(
        HomepageUiState.Loading,
        HomepageUiState.Content(
            listOf(
                MealDomain(
                    id = "52940",
                    name = "Brown Stew Chicken",
                    category = "Chicken",
                    origin = "Jamaican",
                    thumbnailUrl = "https://www.themealdb.com/images/media/meals/sypxpx1515365095.jpg",
                    youtubeUrl = null,
                    instructions = "Prepare the chicken by cutting it into pieces...",
                    ingredientsWithMeasures = mapOf(
                        "Chicken" to "1 whole",
                        "Tomato" to "2 chopped",
                        "Onions" to "2 sliced",
                    ),
                    isFavourite = false,
                ),
                MealDomain(
                    id = "52772",
                    name = "Teriyaki Chicken Casserole",
                    category = "Chicken",
                    origin = "Japanese",
                    thumbnailUrl = "https://www.themealdb.com/images/media/meals/wvpsxx1468256321.jpg",
                    youtubeUrl = null,
                    instructions = "Mix the soy sauce, ginger and garlic...",
                    ingredientsWithMeasures = mapOf(
                        "Chicken" to "750g",
                        "Soy Sauce" to "3 tbsp",
                        "Ginger" to "1 tsp",
                    ),
                    isFavourite = false,
                ),
                MealDomain(
                    id = "52804",
                    name = "Poutine",
                    category = "Miscellaneous",
                    origin = "Canadian",
                    thumbnailUrl = "https://www.themealdb.com/images/media/meals/uuyrrx1487327597.jpg",
                    youtubeUrl = null,
                    instructions = "Heat the fries until crispy...",
                    ingredientsWithMeasures = mapOf(
                        "Fries" to "500g",
                        "Cheese Curds" to "200g",
                        "Gravy" to "200ml",
                    ),
                    isFavourite = false,
                ),
            ),
        ),
        HomepageUiState.Error(Throwable("Error")),
    )
}
