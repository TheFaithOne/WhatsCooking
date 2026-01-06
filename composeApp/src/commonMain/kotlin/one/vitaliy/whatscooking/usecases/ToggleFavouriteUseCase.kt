package one.vitaliy.whatscooking.usecases

import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.first
import one.vitaliy.whatscooking.data.MealDomain
import one.vitaliy.whatscooking.db.FavouriteMealDao
import one.vitaliy.whatscooking.db.FavouriteMealEntity

class ToggleFavouriteMealUseCase(private val dao: FavouriteMealDao) {
    suspend operator fun invoke(meal: MealDomain): AddToFavouritesResult {
        return if (dao.getAllFavouriteMeals().first().any { it.id == meal.id }) {
            dao.deleteFavouriteMeal(meal.id)
            AddToFavouritesResult.RemoveSuccess
        } else {
            runCatching {
                val totalCount = dao.getFavouriteMealCount()
                if (totalCount >= 5) {
                    AddToFavouritesResult.LimitReached
                } else {
                    dao.insertFavouriteMeal(
                        FavouriteMealEntity(
                            id = meal.id,
                            name = meal.name,
                            region = meal.origin.orEmpty(),
                        ),
                    )
                    AddToFavouritesResult.AddSuccess
                }
            }.getOrElse {
                Napier.e { "Adding to favourite failed: $it" }
                AddToFavouritesResult.Failure(it)
            }
        }
    }
}

sealed interface AddToFavouritesResult {
    data object AddSuccess : AddToFavouritesResult
    data object RemoveSuccess : AddToFavouritesResult
    data object LimitReached : AddToFavouritesResult
    data class Failure(val reason: Throwable) : AddToFavouritesResult
}
