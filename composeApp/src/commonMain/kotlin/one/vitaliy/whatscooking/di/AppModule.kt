package one.vitaliy.whatscooking.di

import org.koin.dsl.module

val appModule = module {
    includes(dataModule, viewModelModule)
}
