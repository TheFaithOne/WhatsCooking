package one.vitaliy.whatscooking.categories

import one.vitaliy.whatscooking.categories.api.CategoriesResponse
import one.vitaliy.whatscooking.networking.MealDbApiServices
import one.vitaliy.whatscooking.networking.MealsResponse

/**
 * Repository for meal categories data.
 * Provides access to categories and meals within categories.
 */
class CategoriesRepository(
    private val mealDbApiServices: MealDbApiServices,
) {
    /**
     * Fetches all available meal categories.
     * @return CategoriesResponse containing all categories
     */
    suspend fun getAllCategories(): CategoriesResponse = mealDbApiServices.getCategories()

    /**
     * Fetches meals filtered by a specific category.
     * @param category The category name to filter by
     * @return MealsResponse containing meals in the specified category
     */
    suspend fun getMealsByCategory(category: String): MealsResponse =
        mealDbApiServices.getMealsByCategory(category)
}
