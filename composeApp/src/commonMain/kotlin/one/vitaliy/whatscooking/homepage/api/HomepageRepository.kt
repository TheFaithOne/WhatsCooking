package one.vitaliy.whatscooking.homepage.api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import one.vitaliy.whatscooking.data.MealDomain
import one.vitaliy.whatscooking.data.toMealDomain
import one.vitaliy.whatscooking.db.FavouriteMealDao
import one.vitaliy.whatscooking.recipes.RecipeRepository

interface HomepageRepository {
    suspend fun getLatestMeals(): Flow<List<MealDomain>>
}

/**
 * Repository for homepage data.
 * Provides access to latest meals and featured content.
 */
class HomepageRepositoryImpl(
    private val recipeRepository: RecipeRepository,
    private val favouriteMealDao: FavouriteMealDao,
) : HomepageRepository {
    private companion object {
        const val DEFAULT_RECIPE_QUERY = "chicken"
    }

    override suspend fun getLatestMeals(): Flow<List<MealDomain>> = combine(
        favouriteMealDao.getAllFavouriteMeals(),
        flowOf(recipeRepository.searchRecipes(DEFAULT_RECIPE_QUERY)),
    ) { favourites, recipes ->
        val idsOfFavourites = favourites.map { it.id }
        recipes.map { it.toMealDomain(isFavourite = it.id in idsOfFavourites) }
    }
}
