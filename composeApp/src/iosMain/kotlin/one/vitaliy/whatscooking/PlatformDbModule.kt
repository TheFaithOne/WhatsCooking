package one.vitaliy.whatscooking

import one.vitaliy.whatscooking.db.MealDatabase
import one.vitaliy.whatscooking.db.getMealDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun databasePlatformModule(): Module = module {
    single<MealDatabase> {
        val builder = getDatabaseBuilder()
        getMealDatabase(builder)
    }
}