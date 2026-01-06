package one.vitaliy.whatscooking

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import one.vitaliy.whatscooking.db.MealDatabase

fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<MealDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath("meal.db")
    return Room.databaseBuilder<MealDatabase>(
        context = appContext,
        name = dbFile.absolutePath,
    )
}
