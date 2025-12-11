package one.vitaliy.whatscooking.data

data class MealDomain(
    val id: String,
    val name: String,
    val instructions: String,
    val thumbnailUrl: String?,
    val youtubeUrl: String?,
    val category: String?,
    val origin: String?,
    val ingredientsWithMeasures: Map<String, String>,
    val isFavourite: Boolean = false
)