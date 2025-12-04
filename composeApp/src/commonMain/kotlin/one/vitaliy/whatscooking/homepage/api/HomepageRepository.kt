package one.vitaliy.whatscooking.homepage.api

import one.vitaliy.whatscooking.networking.MealDbApiServices
import one.vitaliy.whatscooking.networking.MealsResponse

class HomepageRepository(
    private val apiServices: MealDbApiServices,
) {
    suspend fun getLatestMeals(): MealsResponse = apiServices.getLatestMeals()
}