package one.vitaliy.whatscooking.homepage

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import kotlinx.serialization.Serializable
import one.vitaliy.whatscooking.compose.PreviewTheme
import one.vitaliy.whatscooking.networking.Meal
import one.vitaliy.whatscooking.ui.Strings
import one.vitaliy.whatscooking.ui.theme.WhatsCookingTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider
import org.koin.compose.viewmodel.koinViewModel

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
        onMealClicked = navigateToMealDetail,
        modifier = modifier.fillMaxSize().padding(paddingValues),
    )
}

@Composable
private fun HomepageContainer(
    uiState: HomepageUiState,
    onRefreshClick: () -> Unit,
    onMealClicked: (String) -> Unit,
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
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        RecentlyAddedRow(recentlyAdded = uiState.latestMeals, onMealClicked = onMealClicked)
    }
}

@Composable
private fun RecentlyAddedRow(
    recentlyAdded: List<Meal>,
    onMealClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = Strings.HOMEPAGE_RECENTLY_ADDED,
            style = WhatsCookingTheme.typography.headline.medium,
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            items(items = recentlyAdded, key = { it.id.orEmpty() }) {
                MealCard(it, modifier = Modifier.animateItem(), onMealClicked = onMealClicked)
            }
        }
    }
}

@Composable
private fun MealCard(
    meal: Meal,
    onMealClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        onClick = { onMealClicked(meal.id.orEmpty()) },
    ) {
        Column(
            modifier = Modifier.padding(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AsyncImage(
                model = meal.imageUrl,
                contentDescription = meal.name,
                contentScale = ContentScale.Crop,
            )
            Text(
                text = "${meal.name}",
                style = WhatsCookingTheme.typography.body.medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            ) {
                Text(
                    text = "${meal.areaOfOrigin}",
                    style = WhatsCookingTheme.typography.label.medium,
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "${meal.category}",
                    style = WhatsCookingTheme.typography.label.medium,
                    textAlign = TextAlign.Center,
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
            text = "${Strings.ERROR_GENERIC}\n${uiState.throwable.message}",
            style = WhatsCookingTheme.typography.body.medium,
            textAlign = TextAlign.Center,
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onRefreshClick,
        ) {
            Text(Strings.ERROR_RETRY)
        }
    }
}

@Preview
@Composable
private fun HomepagePreview(
    @PreviewParameter(HomepagePreviewProvider::class) uiState: HomepageUiState,
) {
    PreviewTheme {
        HomepageContainer(uiState, onRefreshClick = {}, onMealClicked = {})
    }
}

private class HomepagePreviewProvider : PreviewParameterProvider<HomepageUiState> {
    override val values: Sequence<HomepageUiState> = sequenceOf(
        HomepageUiState.Loading,
        HomepageUiState.Content(
            listOf(
                Meal(
                    id = "52940",
                    name = "Brown Stew Chicken",
                    category = "Chicken",
                    areaOfOrigin = "Jamaican",
                    imageUrl = "https://www.themealdb.com/images/media/meals/sypxpx1515365095.jpg",
                    strIngredient1 = "Chicken",
                    strIngredient2 = "Tomato",
                    strIngredient3 = "Onions",
                    strMeasure1 = "1 whole",
                    strMeasure2 = "2 chopped",
                    strMeasure3 = "2 sliced",
                    instructions = "Prepare the chicken by cutting it into pieces...",
                ),
                Meal(
                    id = "52772",
                    name = "Teriyaki Chicken Casserole",
                    category = "Chicken",
                    areaOfOrigin = "Japanese",
                    imageUrl = "https://www.themealdb.com/images/media/meals/wvpsxx1468256321.jpg",
                    strIngredient1 = "Chicken",
                    strIngredient2 = "Soy Sauce",
                    strIngredient3 = "Ginger",
                    strMeasure1 = "750g",
                    strMeasure2 = "3 tbsp",
                    strMeasure3 = "1 tsp",
                    instructions = "Mix the soy sauce, ginger and garlic...",
                ),
                Meal(
                    id = "52804",
                    name = "Poutine",
                    category = "Miscellaneous",
                    areaOfOrigin = "Canadian",
                    imageUrl = "https://www.themealdb.com/images/media/meals/uuyrrx1487327597.jpg",
                    strIngredient1 = "Fries",
                    strIngredient2 = "Cheese Curds",
                    strIngredient3 = "Gravy",
                    strMeasure1 = "500g",
                    strMeasure2 = "200g",
                    strMeasure3 = "200ml",
                    instructions = "Heat the fries until crispy...",
                ),
            ),
        ),
        HomepageUiState.Error(Throwable("Error")),
    )
}
