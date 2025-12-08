package one.vitaliy.whatscooking.randommeal

import one.vitaliy.whatscooking.networking.MealDbApiServices
import one.vitaliy.whatscooking.networking.MealsResponse

/**
 * Repository for fetching random meal data.
 * Abstracts the data source from the ViewModel.
 */
class RandomMealRepository(
    private val apiServices: MealDbApiServices,
) {
    /**
     * Fetches a random meal from the API.
     * @return MealsResponse containing a random meal
     */
    suspend fun getRandomMeal(): MealsResponse = apiServices.loadRandomMeal()
}
