package one.vitaliy.whatscooking.networking

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import one.vitaliy.whatscooking.BuildKonfig

class CalorieNinjasApiServices {
    private val baseUrl = "https://api.calorieninjas.com/v1/"
    private val apiKey = BuildKonfig.CALORIE_NINJAS_API_KEY

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
            header("X-Api-Key", apiKey)
        }
        install(ContentNegotiation) {
            json(json)
        }
    }

    suspend fun getRecipes(query: String): RecipesResponse {
        return httpClient.get("recipe") {
            parameter("query", query)
        }.body()
    }
}

@Serializable
data class RecipesResponse(
    @SerialName("items")
    val items: List<RecipeDto> = emptyList(),
)

@Serializable
data class RecipeDto(
    @SerialName("title")
    val title: String,
    @SerialName("ingredients")
    val ingredients: String,
    @SerialName("servings")
    val servings: String,
    @SerialName("instructions")
    val instructions: String,
)
