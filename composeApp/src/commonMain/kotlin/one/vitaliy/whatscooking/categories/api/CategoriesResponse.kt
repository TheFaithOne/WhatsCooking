package one.vitaliy.whatscooking.categories.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoriesResponse(
    @SerialName("categories")
    val categories: List<Category>?
)