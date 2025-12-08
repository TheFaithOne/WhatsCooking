package one.vitaliy.whatscooking.categories.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a meal category.
 * @property idCategory Unique identifier for the category
 * @property strCategory Category name
 * @property strCategoryDescription Description of the category
 * @property strCategoryThumb URL to the category thumbnail image
 */
@Serializable
data class Category(
    @SerialName("idCategory")
    val idCategory: String?,
    @SerialName("strCategory")
    val strCategory: String?,
    @SerialName("strCategoryDescription")
    val strCategoryDescription: String?,
    @SerialName("strCategoryThumb")
    val strCategoryThumb: String?,
)
