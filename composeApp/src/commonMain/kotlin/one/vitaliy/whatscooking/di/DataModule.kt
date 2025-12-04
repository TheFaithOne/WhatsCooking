package one.vitaliy.whatscooking.di

import one.vitaliy.whatscooking.categories.CategoriesRepository
import one.vitaliy.whatscooking.homepage.api.HomepageRepository
import one.vitaliy.whatscooking.networking.MealDbApiServices
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    singleOf(::MealDbApiServices)
    singleOf(::CategoriesRepository)
    singleOf(::HomepageRepository)
}
