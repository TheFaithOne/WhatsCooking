package one.vitaliy.whatscooking.networking

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class MealDbApiServices {
    private val baseUrl = "https://www.themealdb.com/api/json/v1/"
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
        return httpClient.get("1/random.php").body()
    }
}