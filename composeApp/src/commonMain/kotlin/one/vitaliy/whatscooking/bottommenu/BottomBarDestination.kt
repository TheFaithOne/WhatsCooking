package one.vitaliy.whatscooking.bottommenu

import one.vitaliy.whatscooking.homepage.HomepageScreen
import one.vitaliy.whatscooking.randommeal.RandomMealScreen
import org.jetbrains.compose.resources.DrawableResource
import whatscooking.composeapp.generated.resources.Res
import whatscooking.composeapp.generated.resources.ic_home_outline
import whatscooking.composeapp.generated.resources.ic_random_filled
import whatscooking.composeapp.generated.resources.ic_random_outline

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

    //    FAVORITES(
//        outlineIconRes = Res.drawable.ic_favourite_outline,
//        filledIconRes = Res.drawable.ic_favourite_filled,
//        title = "Favorites",
//        route = HomepageScreen // TODO: Update
//    ),
    RANDOM(
        outlineIconRes = Res.drawable.ic_random_outline,
        filledIconRes = Res.drawable.ic_random_filled,
        title = "Random",
        route = RandomMealScreen,
    ),
//    SEARCH(
//        outlineIconRes = Res.drawable.ic_search,
//        filledIconRes = Res.drawable.ic_search,
//        title = "Search",
//        route = HomepageScreen // TODO: Update
//    ),
}
