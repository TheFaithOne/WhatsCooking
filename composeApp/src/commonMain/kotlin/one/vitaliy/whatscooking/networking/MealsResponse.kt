package one.vitaliy.whatscooking.networking


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MealsResponse(
    @SerialName("meals")
    val meals: List<Meal>?
)