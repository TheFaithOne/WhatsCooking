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
import androidx.compose.material3.MaterialTheme
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
import one.vitaliy.whatscooking.networking.Meal
import one.vitaliy.whatscooking.ui.theme.WhatsCookingTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Serializable
object HomepageScreen

@Composable
internal fun HomepageContainer(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    val viewModel = koinViewModel<HomepageViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    AnimatedContent(
        uiState,
        modifier = modifier.fillMaxSize().padding(paddingValues)
    ) {
        when (it) {
            is HomepageUiState.Content -> Homepage(it)
            is HomepageUiState.Error -> HomepageError(
                onRefreshClick = viewModel::refresh,
                uiState = it
            )

            HomepageUiState.Loading -> CircularProgressIndicator(
                modifier = Modifier.fillMaxSize().wrapContentSize()
            )
        }
    }
}

@Composable
private fun Homepage(
    uiState: HomepageUiState.Content,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        RecentlyAddedRow(recentlyAdded = uiState.latestMeals)
    }
}

@Composable
private fun RecentlyAddedRow(
    recentlyAdded: List<Meal>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = "Recently added recipes",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier.fillMaxWidth(),
        ) {
            items(items = recentlyAdded, key = { it.idMeal.orEmpty() }) {
                MealCard(it, modifier = Modifier.animateItem())
            }
        }

    }
}

@Composable
private fun MealCard(
    meal: Meal,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier.padding(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AsyncImage(
                model = meal.strMealThumb,
                contentDescription = meal.strMeal,
                contentScale = ContentScale.Crop,
            )
            Text(
                text = "${meal.strMeal}",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
            ) {
                Text(
                    text = "${meal.strArea}",
                    style = MaterialTheme.typography.labelMedium,
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "${meal.strCategory}",
                    style = MaterialTheme.typography.labelMedium,
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
        // FIXME: Add a proper error screen
        Text("Ooops, something went wrong!\n${uiState.throwable.message}")
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onRefreshClick,
        ) {
            Text("Retry")
        }
    }
}

@Preview
@Composable
private fun HomepagePreview() {
    WhatsCookingTheme {
        HomepageContainer(PaddingValues())
    }
}