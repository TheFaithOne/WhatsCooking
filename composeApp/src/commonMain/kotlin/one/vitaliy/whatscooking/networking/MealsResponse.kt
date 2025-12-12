package one.vitaliy.whatscooking.networking

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Response wrapper for meal-related API endpoints.
 * @property meals List of meals returned by the API, null if no meals found
 */
@Serializable
data class MealsResponse(
    @SerialName("meals")
    val meals: List<MealDto>?,
)
