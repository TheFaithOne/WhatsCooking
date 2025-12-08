package one.vitaliy.whatscooking.mealdetail

import one.vitaliy.whatscooking.networking.MealDbApiServices
import one.vitaliy.whatscooking.networking.MealsResponse

/**
 * Repository for fetching meal detail data.
 * Abstracts the data source from the ViewModel.
 */
class MealDetailRepository(
    private val apiServices: MealDbApiServices,
) {
    /**
     * Fetches detailed information about a meal by its ID.
     * @param mealId The unique identifier of the meal
     * @return MealsResponse containing the meal details
     */
    suspend fun getMealById(mealId: String): MealsResponse = apiServices.getMealById(mealId)
}
