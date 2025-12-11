package one.vitaliy.whatscooking.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteMealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteMeal(favouriteMeal: FavouriteMealEntity)

    @Query("DELETE FROM favourite_meal WHERE id = :id")
    suspend fun deleteFavouriteMeal(id: String)

    @Query("SELECT * FROM favourite_meal")
    fun getAllFavouriteMeals(): Flow<List<FavouriteMealEntity>>

    @Query("SELECT * FROM favourite_meal WHERE id = :id")
    suspend fun getFavouriteMeal(id: String): FavouriteMealEntity?

    @Query("SELECT COUNT(*) FROM favourite_meal")
    suspend fun getFavouriteMealCount(): Int

    @Delete
    suspend fun clearFavouriteMeals(favouriteMeal: FavouriteMealEntity)
}