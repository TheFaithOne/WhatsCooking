package one.vitaliy.whatscooking.di

import one.vitaliy.whatscooking.categories.CategoriesRepository
import one.vitaliy.whatscooking.homepage.api.HomepageRepository
import one.vitaliy.whatscooking.mealdetail.MealDetailRepository
import one.vitaliy.whatscooking.networking.MealDbApiServices
import one.vitaliy.whatscooking.randommeal.RandomMealRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

/**
 * Data module for dependency injection.
 * Provides API services and repositories.
 */
val dataModule = module {
    singleOf(::MealDbApiServices)
    singleOf(::CategoriesRepository)
    singleOf(::HomepageRepository)
    singleOf(::RandomMealRepository)
    singleOf(::MealDetailRepository)
}
