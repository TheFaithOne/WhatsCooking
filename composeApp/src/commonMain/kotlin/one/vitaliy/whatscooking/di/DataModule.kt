package one.vitaliy.whatscooking.di

import one.vitaliy.whatscooking.categories.CategoriesRepository
import one.vitaliy.whatscooking.homepage.api.HomepageRepository
import one.vitaliy.whatscooking.homepage.api.HomepageRepositoryImpl
import one.vitaliy.whatscooking.mealdetail.MealDetailRepository
import one.vitaliy.whatscooking.networking.CalorieNinjasApiServices
import one.vitaliy.whatscooking.networking.MealDbApiServices
import one.vitaliy.whatscooking.randommeal.RandomMealRepository
import one.vitaliy.whatscooking.recipes.CalorieNinjasRecipeRepository
import one.vitaliy.whatscooking.recipes.RecipeRepository
import one.vitaliy.whatscooking.usecases.ToggleFavouriteMealUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

/**
 * Data module for dependency injection.
 * Provides API services and repositories.
 */
val dataModule = module {
    singleOf(::MealDbApiServices)
    singleOf(::CalorieNinjasApiServices)
    singleOf(::CalorieNinjasRecipeRepository) { bind<RecipeRepository>() }
    singleOf(::CategoriesRepository)
    singleOf(::HomepageRepositoryImpl) { bind<HomepageRepository>() }
    singleOf(::RandomMealRepository)
    singleOf(::MealDetailRepository)
    factoryOf(::ToggleFavouriteMealUseCase)
}
