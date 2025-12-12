package one.vitaliy.whatscooking.di

import one.vitaliy.whatscooking.db.FavouriteMealDao
import one.vitaliy.whatscooking.db.MealDatabase
import org.koin.dsl.module

val databaseModule = module {
    single<FavouriteMealDao> { get<MealDatabase>().getFavouriteMealDao() }
}