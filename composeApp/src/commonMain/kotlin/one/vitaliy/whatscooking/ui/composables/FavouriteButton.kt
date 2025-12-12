package one.vitaliy.whatscooking.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import one.vitaliy.whatscooking.ui.theme.WhatsCookingTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import whatscooking.composeapp.generated.resources.Res
import whatscooking.composeapp.generated.resources.ic_favourite_filled
import whatscooking.composeapp.generated.resources.ic_favourite_outline
import whatscooking.composeapp.generated.resources.toggle_favourite_content_description

@Composable
fun FavouriteButton(
    onAddToFavouriteClick: () -> Unit,
    isFavourite: Boolean,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onAddToFavouriteClick,
        modifier = modifier,
    ) {
        Icon(
            painterResource(
                resource = if (isFavourite) {
                    Res.drawable.ic_favourite_filled
                } else {
                    Res.drawable.ic_favourite_outline
                }
            ),
            contentDescription = stringResource(Res.string.toggle_favourite_content_description),
            modifier = Modifier.background(
                color = WhatsCookingTheme.colors.background.card,
                shape = CircleShape
            ).padding(4.dp)
        )
    }
}