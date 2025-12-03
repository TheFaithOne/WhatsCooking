package one.vitaliy.whatscooking.di

import one.vitaliy.whatscooking.randommeal.RandomMealViewModel
import one.vitaliy.whatscooking.categories.CategoriesViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::RandomMealViewModel)
    viewModelOf(::CategoriesViewModel)
}