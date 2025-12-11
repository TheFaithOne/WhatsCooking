package one.vitaliy.whatscooking.di

import one.vitaliy.whatscooking.categories.CategoriesRepository
import one.vitaliy.whatscooking.homepage.api.HomepageRepository
import one.vitaliy.whatscooking.homepage.api.HomepageRepositoryImpl
import one.vitaliy.whatscooking.mealdetail.MealDetailRepository
import one.vitaliy.whatscooking.networking.MealDbApiServices
import one.vitaliy.whatscooking.randommeal.RandomMealRepository
import one.vitaliy.whatscooking.usecases.AddToFavouritesUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.binds
import org.koin.dsl.module

/**
 * Data module for dependency injection.
 * Provides API services and repositories.
 */
val dataModule = module {
    singleOf(::MealDbApiServices)
    singleOf(::CategoriesRepository)
    singleOf(::HomepageRepositoryImpl) { bind<HomepageRepository>() }
    single { HomepageRepositoryImpl(get(), get()) } binds arrayOf(HomepageRepository::class)
    singleOf(::RandomMealRepository)
    singleOf(::MealDetailRepository)
    factoryOf(::AddToFavouritesUseCase)
}
