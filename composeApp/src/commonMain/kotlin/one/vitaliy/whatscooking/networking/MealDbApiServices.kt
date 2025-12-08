package one.vitaliy.whatscooking.networking

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import one.vitaliy.whatscooking.categories.api.CategoriesResponse

/**
 * Service class for interacting with TheMealDB API.
 * Provides methods to fetch meals, categories, and other cooking-related data.
 */
class MealDbApiServices {
    private val baseUrl = "https://www.themealdb.com/api/json/v2/"

    // Note: In production, this should be stored securely (e.g., BuildConfig, environment variables)
    private val apiKey = "65232507"

    private val json = Json {
        coerceInputValues = true
        explicitNulls = false
        ignoreUnknownKeys = true
        useAlternativeNames = false
        encodeDefaults = true
    }

    private val httpClient = HttpClient {
        expectSuccess = true
        defaultRequest {
            contentType(ContentType.Application.Json)
            url(baseUrl)
        }
        install(ContentNegotiation) {
            json(json)
        }
    }

    /**
     * Fetches a random meal from the API.
     * @return MealsResponse containing a single random meal
     */
    suspend fun loadRandomMeal(): MealsResponse {
        return httpClient.get("$apiKey/random.php").body()
    }

    /**
     * Fetches all available meal categories.
     * @return CategoriesResponse containing list of categories
     */
    suspend fun getCategories(): CategoriesResponse {
        return httpClient.get("$apiKey/categories.php").body()
    }

    /**
     * Fetches meals filtered by category.
     * @param category The category name to filter by
     * @return MealsResponse containing meals in the specified category
     */
    suspend fun getMealsByCategory(category: String): MealsResponse {
        return httpClient.get("$apiKey/filter.php?c=$category").body()
    }

    /**
     * Fetches the latest meals added to the database.
     * @return MealsResponse containing recently added meals
     */
    suspend fun getLatestMeals(): MealsResponse {
        return httpClient.get("$apiKey/latest.php").body()
    }

    /**
     * Fetches detailed information about a specific meal by ID.
     * @param id The meal ID to fetch
     * @return MealsResponse containing the meal details
     */
    suspend fun getMealById(id: String): MealsResponse {
        return httpClient.get("$apiKey/lookup.php?i=$id").body()
    }
}
