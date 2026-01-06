package one.vitaliy.whatscooking.homepage.api

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import one.vitaliy.whatscooking.data.MealDomain
import one.vitaliy.whatscooking.db.FavouriteMealDao
import one.vitaliy.whatscooking.db.toDomain
import one.vitaliy.whatscooking.networking.MealDbApiServices
import one.vitaliy.whatscooking.networking.toDomain

interface HomepageRepository {
    suspend fun getLatestMeals(): Flow<List<MealDomain>>
    suspend fun getFavouriteMeals(): Flow<List<MealDomain>>
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
        flowOf(apiServices.getLatestMeals()),
    ) { favourites, mealsResponse ->
        val idsOfFavourites = favourites.map { it.id }
        mealsResponse.meals?.map {
            it.toDomain().copy(isFavourite = it.id in idsOfFavourites)
        }.orEmpty()
    }

    override suspend fun getFavouriteMeals(): Flow<List<MealDomain>> =
        favouriteMealDao.getAllFavouriteMeals().map {
            it.map { entity ->
                entity.toDomain()
            }
        }
}
