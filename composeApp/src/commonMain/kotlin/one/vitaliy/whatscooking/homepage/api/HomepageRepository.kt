package one.vitaliy.whatscooking.homepage.api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import one.vitaliy.whatscooking.data.MealDomain
import one.vitaliy.whatscooking.db.FavouriteMealDao
import one.vitaliy.whatscooking.networking.MealDbApiServices
import one.vitaliy.whatscooking.networking.MealsResponse
import one.vitaliy.whatscooking.networking.toDomain

interface HomepageRepository {
    suspend fun getLatestMeals(): Flow<List<MealDomain>>
}

/**
 * Repository for homepage data.
 * Provides access to latest meals and featured content.
 */
class HomepageRepositoryImpl(
    private val apiServices: MealDbApiServices,
    private val favouriteMealDao: FavouriteMealDao,
) : HomepageRepository {
    override suspend fun getLatestMeals(): Flow<List<MealDomain>> = combine(
        favouriteMealDao.getAllFavouriteMeals(),
        flowOf(apiServices.getLatestMeals())
    ) { favourites, mealsResponse ->
        val idsOfFavourites = favourites.map { it.id }
        mealsResponse.meals?.map {
            it.toDomain().copy(isFavourite = it.id in idsOfFavourites)
        }.orEmpty()
    }
}
