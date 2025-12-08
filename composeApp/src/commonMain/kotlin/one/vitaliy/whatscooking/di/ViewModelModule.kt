package one.vitaliy.whatscooking.di

import one.vitaliy.whatscooking.categories.CategoriesViewModel
import one.vitaliy.whatscooking.categories.detail.CategoryDetailsViewModel
import one.vitaliy.whatscooking.homepage.HomepageViewModel
import one.vitaliy.whatscooking.mealdetail.MealDetailViewModel
import one.vitaliy.whatscooking.randommeal.RandomMealViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/**
 * ViewModel module for dependency injection.
 * Provides all ViewModels used throughout the application.
 */
val viewModelModule = module {
    viewModelOf(::RandomMealViewModel)
    viewModelOf(::CategoriesViewModel)
    viewModelOf(::CategoryDetailsViewModel)
    viewModelOf(::HomepageViewModel)
    viewModelOf(::MealDetailViewModel)
}
