package one.vitaliy.whatscooking.di

import one.vitaliy.whatscooking.databasePlatformModule
import org.koin.dsl.module

/**
 * Main application module for dependency injection.
 * Combines all feature modules (data and viewModel).
 */
val appModule = module {
    includes(dataModule, viewModelModule, databasePlatformModule(), databaseModule)
}
