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

class MealDbApiServices {
    private val baseUrl = "https://www.themealdb.com/api/json/v2/"

    // TODO: Replace this with an actual key and store it securely
    private val tempApiKey = "65232507"
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

    suspend fun loadRandomMeal(): MealsResponse {
        return httpClient.get("$tempApiKey/random.php").body()
    }

    suspend fun getCategories(): CategoriesResponse {
        return httpClient.get("$tempApiKey/categories.php").body()
    }

    suspend fun getMealsByCategory(category: String): MealsResponse {
        return httpClient.get("$tempApiKey/filter.php?c=$category").body()
    }

    suspend fun getLatestMeals(): MealsResponse {
        return httpClient.get("$tempApiKey/latest.php").body()
    }

    suspend fun getMealById(id: String): MealsResponse {
        return httpClient.get("$tempApiKey/lookup.php?i=$id").body()
    }
}
