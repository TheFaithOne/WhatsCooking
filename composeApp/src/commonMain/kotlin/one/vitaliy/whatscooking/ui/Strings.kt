package one.vitaliy.whatscooking.ui

/**
 * Centralized string resources for the application.
 * In a production app, these should be moved to proper localized string resources.
 */
object Strings {
    // App
    const val APP_NAME = "What's Cooking"

    // Homepage
    const val HOMEPAGE_RECENTLY_ADDED = "Recently added recipes"

    // Meal Detail
    const val MEAL_DETAIL_WATCH_YOUTUBE = "Watch on YouTube"
    const val MEAL_DETAIL_ERROR_TITLE =
        "Oops, we can't fetch the meal details. Please try again later."
    const val MEAL_DETAIL_REFRESH = "Refresh"

    // Random Meal
    const val RANDOM_MEAL_GO_TO_CATEGORIES = "Go to Categories"

    // Error Messages
    const val ERROR_GENERIC = "Ooops, something went wrong!"
    const val ERROR_RETRY = "Retry"

    // Formatting
    const val INGREDIENT_BULLET = "•"
    fun formatIngredient(ingredient: String, measure: String) =
        "$INGREDIENT_BULLET $ingredient: $measure"

    fun formatImageDescription(mealName: String) = "Image of $mealName"
}
