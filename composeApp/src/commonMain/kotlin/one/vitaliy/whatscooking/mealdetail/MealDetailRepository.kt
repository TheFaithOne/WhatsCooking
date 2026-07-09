package one.vitaliy.whatscooking.mealdetail

import one.vitaliy.whatscooking.data.RecipeDomain
import one.vitaliy.whatscooking.recipes.RecipeRepository

/**
 * Repository for fetching meal detail data.
 * Abstracts the data source from the ViewModel.
 */
class MealDetailRepository(
    private val recipeRepository: RecipeRepository,
) {
    /**
     * Fetches detailed information about a recipe by its ID.
     * @param mealId The unique identifier of the recipe
     */
    suspend fun getMealById(mealId: String): RecipeDomain = recipeRepository.getRecipe(mealId)
}
