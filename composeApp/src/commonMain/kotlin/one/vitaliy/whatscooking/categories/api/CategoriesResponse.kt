package one.vitaliy.whatscooking.categories.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Response wrapper for categories API endpoint.
 * @property categories List of meal categories, null if no categories found
 */
@Serializable
data class CategoriesResponse(
    @SerialName("categories")
    val categories: List<Category>?,
)
