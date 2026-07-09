package one.vitaliy.whatscooking.recipes

import one.vitaliy.whatscooking.data.RecipeDomain
import one.vitaliy.whatscooking.networking.CalorieNinjasApiServices
import one.vitaliy.whatscooking.networking.RecipeDto

interface RecipeRepository {
    suspend fun searchRecipes(query: String): List<RecipeDomain>
    suspend fun getRecipe(id: String): RecipeDomain
}

class CalorieNinjasRecipeRepository(
    private val apiServices: CalorieNinjasApiServices,
) : RecipeRepository {

    override suspend fun searchRecipes(query: String): List<RecipeDomain> {
        return apiServices.getRecipes(query).items.map { it.toDomain() }
    }

    override suspend fun getRecipe(id: String): RecipeDomain {
        return searchRecipes(id).firstOrNull { it.id == id || it.title.equals(id, ignoreCase = true) }
            ?: searchRecipes(id).firstOrNull()
            ?: error("No recipe found for: $id")
    }
}

private fun RecipeDto.toDomain(): RecipeDomain {
    return RecipeDomain(
        id = title,
        title = title,
        ingredients = ingredients.toIngredientsList(),
        servings = servings,
        instructions = instructions,
    )
}

private fun String.toIngredientsList(): List<String> {
    return split("\n", "|", ";")
        .map { it.trim().trimStart('-', '•') }
        .filter { it.isNotBlank() }
}
