package one.vitaliy.whatscooking.bottommenu

import one.vitaliy.whatscooking.homepage.HomepageScreen
import one.vitaliy.whatscooking.randommeal.RandomMealScreen
import org.jetbrains.compose.resources.DrawableResource
import whatscooking.composeapp.generated.resources.Res
import whatscooking.composeapp.generated.resources.ic_favourite_filled
import whatscooking.composeapp.generated.resources.ic_favourite_outline
import whatscooking.composeapp.generated.resources.ic_home_outline
import whatscooking.composeapp.generated.resources.ic_random_filled
import whatscooking.composeapp.generated.resources.ic_random_outline
import whatscooking.composeapp.generated.resources.ic_search

internal enum class BottomBarDestination(
    val outlineIconRes: DrawableResource,
    val filledIconRes: DrawableResource,
    val title: String,
    val route: Any,
) {
    HOME(
        outlineIconRes = Res.drawable.ic_home_outline,
        filledIconRes = Res.drawable.ic_home_outline,
        title = "Home",
        route = HomepageScreen,
    ),
    FAVOURITES(
        outlineIconRes = Res.drawable.ic_favourite_outline,
        filledIconRes = Res.drawable.ic_favourite_filled,
        title = "Favourites",
        route = FavouritesScreen,
    ),
    SEARCH(
        outlineIconRes = Res.drawable.ic_search,
        filledIconRes = Res.drawable.ic_search,
        title = "Search",
        route = SearchScreen,
    ),
    RANDOM(
        outlineIconRes = Res.drawable.ic_random_outline,
        filledIconRes = Res.drawable.ic_random_filled,
        title = "Random",
        route = RandomMealScreen,
    ),
    ;

    val isSeparateAction: Boolean
        get() = this == RANDOM

    companion object {
        val mainDestinations: List<BottomBarDestination>
            get() = entries.filterNot { it.isSeparateAction }
    }
}
