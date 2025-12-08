package one.vitaliy.whatscooking.homepage.api

import one.vitaliy.whatscooking.networking.MealDbApiServices
import one.vitaliy.whatscooking.networking.MealsResponse

/**
 * Repository for homepage data.
 * Provides access to latest meals and featured content.
 */
class HomepageRepository(
    private val apiServices: MealDbApiServices,
) {
    /**
     * Fetches the latest meals added to the database.
     * @return MealsResponse containing the latest meals
     */
    suspend fun getLatestMeals(): MealsResponse = apiServices.getLatestMeals()
}
