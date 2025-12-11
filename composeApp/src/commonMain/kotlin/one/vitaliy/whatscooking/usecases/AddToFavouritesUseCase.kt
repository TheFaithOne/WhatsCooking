package one.vitaliy.whatscooking.usecases

import io.github.aakira.napier.Napier
import one.vitaliy.whatscooking.data.MealDomain
import one.vitaliy.whatscooking.db.FavouriteMealDao
import one.vitaliy.whatscooking.db.FavouriteMealEntity
import one.vitaliy.whatscooking.networking.MealDto

class AddToFavouritesUseCase(private val mealDao: FavouriteMealDao) {
    suspend operator fun invoke(mealDto: MealDomain): AddToFavouritesResult {
        return runCatching {
            val totalCount = mealDao.getFavouriteMealCount()
            if (totalCount >= 5) {
                AddToFavouritesResult.LimitReached
            } else {
                mealDao.insertFavouriteMeal(
                    FavouriteMealEntity(
                        id = mealDto.id,
                        name = mealDto.name,
                        region = mealDto.origin.orEmpty(),
                    )
                )
                AddToFavouritesResult.Success
            }
        }.getOrElse {
            Napier.e { "Adding to favourite failed: $it" }
            AddToFavouritesResult.Failure(it)
        }
    }
}

sealed interface AddToFavouritesResult {
    data object Success : AddToFavouritesResult
    data object LimitReached : AddToFavouritesResult
    data class Failure(val reason: Throwable) : AddToFavouritesResult
}