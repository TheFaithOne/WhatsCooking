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

class ApiNinjasApiServices {
    private val baseUrl = "https://api.api-ninjas.com/v3/"
    private val apiKey = BuildKonfig.API_NINJAS_API_KEY

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

    suspend fun getRecipes(query: String): List<RecipeDto> {
        return httpClient.get("recipe") {
            parameter("title", query)
        }.body()
    }
}

@Serializable
data class RecipeDto(
    @SerialName("title")
    val title: String,
    @SerialName("ingredients")
    val ingredients: List<RecipeIngredientDto> = emptyList(),
    @SerialName("servings")
    val servings: String = "",
    @SerialName("instructions")
    val instructions: List<String> = emptyList(),
)

@Serializable
data class RecipeIngredientDto(
    @SerialName("name")
    val name: String,
    @SerialName("quantity")
    val quantity: Double? = null,
    @SerialName("unit")
    val unit: String? = null,
)
