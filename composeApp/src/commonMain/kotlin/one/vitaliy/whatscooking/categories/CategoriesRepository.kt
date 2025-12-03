package one.vitaliy.whatscooking.categories

import one.vitaliy.whatscooking.networking.MealDbApiServices

class CategoriesRepository(
    private val mealDbApiServices: MealDbApiServices
) {
    suspend fun getAllCategories() = mealDbApiServices.getCategories()

    suspend fun getMealsByCategory(category: String) =
        mealDbApiServices.getMealsByCategory(category)
}