package one.vitaliy.whatscooking.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import one.vitaliy.whatscooking.compose.PreviewTheme
import one.vitaliy.whatscooking.data.MealDomain
import one.vitaliy.whatscooking.ui.theme.WhatsCookingTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider
import whatscooking.composeapp.generated.resources.Res
import whatscooking.composeapp.generated.resources.ic_chef_hat
import whatscooking.composeapp.generated.resources.ic_favourite_filled
import whatscooking.composeapp.generated.resources.ic_favourite_outline
import whatscooking.composeapp.generated.resources.ingredient_count

@Composable
fun MealCard(
    meal: MealDomain,
    onMealClicked: (String) -> Unit,
    onAddToFavouriteCLick: (MealDomain) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        onClick = { onMealClicked(meal.id) },
    ) {
        Box {
            Column(
                modifier = Modifier.padding(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            ) {
                AsyncImage(
                    model = meal.thumbnailUrl,
                    contentDescription = meal.name,
                    contentScale = ContentScale.Crop,
                )
                Text(
                    text = meal.category.orEmpty(),
                    style = WhatsCookingTheme.typography.label.medium,
                    modifier = Modifier.padding(horizontal = 16.dp).padding(top = 16.dp),
                    color = WhatsCookingTheme.colors.typography.headlineHighlighted,
                )
                Text(
                    text = meal.name,
                    style = WhatsCookingTheme.typography.body.large,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = WhatsCookingTheme.colors.typography.bodySecondary,
                    fontWeight = FontWeight.Bold,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(horizontal = 16.dp),
                ) {
                    Icon(painterResource(Res.drawable.ic_chef_hat), contentDescription = null)
                    Text(
                        text = stringResource(
                            Res.string.ingredient_count,
                            meal.ingredientsWithMeasures.keys.size,
                        ),
                        style = WhatsCookingTheme.typography.label.small
                    )
                }
            }
            IconButton(
                onClick = { onAddToFavouriteCLick(meal) },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    painterResource(
                        resource = if (meal.isFavourite) {
                            Res.drawable.ic_favourite_filled
                        } else {
                            Res.drawable.ic_favourite_outline
                        }
                    ),
                    // FIXME: Add proper contentDescription
                    contentDescription = null,
                    modifier = Modifier.background(
                        color = WhatsCookingTheme.colors.background.card,
                        shape = CircleShape
                    ).padding(4.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun MealCardPreview(
    @PreviewParameter(MealPreviewProvider::class) meal: MealDomain
) {
    PreviewTheme {
        MealCard(
            meal = meal,
            onAddToFavouriteCLick = {},
            onMealClicked = {},
        )
    }
}

private class MealPreviewProvider : PreviewParameterProvider<MealDomain> {
    override val values = sequenceOf(
        MealDomain(
            id = "voluptaria",
            name = "Pizza",
            instructions = "iuvaret",
            thumbnailUrl = "https://www.google.com/#q=ludus",
            youtubeUrl = "https://www.google.com/#q=quot",
            category = "Pizza",
            origin = "Italian",
            ingredientsWithMeasures = mapOf(),
            isFavourite = false,
        ),
        MealDomain(
            id = "voluptaria",
            name = "Bruger",
            instructions = "neco, neco",
            thumbnailUrl = "https://www.google.com/#q=ludus",
            youtubeUrl = "https://www.google.com/#q=quot",
            category = "Fast food",
            origin = "America",
            ingredientsWithMeasures = mapOf(),
            isFavourite = true,
        ),
    )
}