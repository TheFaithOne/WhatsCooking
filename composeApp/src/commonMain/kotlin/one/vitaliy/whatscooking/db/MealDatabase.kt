package one.vitaliy.whatscooking.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(entities = [FavouriteMealEntity::class], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class MealDatabase : RoomDatabase() {
    abstract fun getFavouriteMealDao(): FavouriteMealDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<MealDatabase> {
    override fun initialize(): MealDatabase
}

internal fun getMealDatabase(
    builder: RoomDatabase.Builder<MealDatabase>,
): MealDatabase {
    return builder
        .fallbackToDestructiveMigrationOnDowngrade(dropAllTables = true)
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}
