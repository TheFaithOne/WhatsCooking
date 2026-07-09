package one.vitaliy.whatscooking.data

data class RecipeDomain(
    val id: String,
    val title: String,
    val ingredients: List<String>,
    val servings: String,
    val instructions: String,
)

fun RecipeDomain.toMealDomain(isFavourite: Boolean = false): MealDomain {
    return MealDomain(
        id = id,
        name = title,
        instructions = instructions,
        thumbnailUrl = null,
        youtubeUrl = null,
        category = servings,
        origin = null,
        ingredientsWithMeasures = ingredients.associateWith { "" },
        isFavourite = isFavourite,
    )
}
