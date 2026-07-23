package one.vitaliy.whatscooking.randommeal

import one.vitaliy.whatscooking.data.RecipeDomain
import one.vitaliy.whatscooking.recipes.RecipeRepository

/**
 * Repository for fetching pseudo-random recipe data.
 * Abstracts the data source from the ViewModel.
 */
class RandomMealRepository(
    private val recipeRepository: RecipeRepository,
) {
    /**
     * Fetches a pseudo-random recipe from the API.
     * @return RecipeDomain containing a recipe selected from a random search query
     */
    suspend fun getRandomRecipe(): RecipeDomain = recipeRepository.getRandomRecipe()
}
