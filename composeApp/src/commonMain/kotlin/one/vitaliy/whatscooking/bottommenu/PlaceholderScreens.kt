@file:Suppress("ForbiddenComment")

package one.vitaliy.whatscooking.bottommenu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.serialization.Serializable
import one.vitaliy.whatscooking.ui.theme.WhatsCookingTheme

@Serializable
object FavouritesScreen

@Serializable
object SearchScreen

@Composable
internal fun FavouritesPlaceholderScreen(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
) {
    // TODO: Replace with the favourites meal list once that section is implemented.
    MissingSectionPlaceholder(
        title = "Favourites",
        message = "Your saved meals will appear here.",
        modifier = modifier.padding(paddingValues),
    )
}

@Composable
internal fun SearchPlaceholderScreen(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
) {
    // TODO: Replace with meal search UI once that section is implemented.
    MissingSectionPlaceholder(
        title = "Search",
        message = "Search recipes by name, ingredient, or category.",
        modifier = modifier.padding(paddingValues),
    )
}

@Composable
private fun MissingSectionPlaceholder(
    title: String,
    message: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = title,
            style = WhatsCookingTheme.typography.headline.medium,
            color = WhatsCookingTheme.colors.typography.headline,
            textAlign = TextAlign.Center,
        )
        Text(
            text = message,
            style = WhatsCookingTheme.typography.body.medium,
            color = WhatsCookingTheme.colors.typography.bodySecondary,
            textAlign = TextAlign.Center,
        )
    }
}
