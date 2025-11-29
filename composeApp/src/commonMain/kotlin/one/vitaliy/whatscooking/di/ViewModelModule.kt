package one.vitaliy.whatscooking.di

import one.vitaliy.whatscooking.RandomMealViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::RandomMealViewModel)
}