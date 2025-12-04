package one.vitaliy.whatscooking.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes

fun initKoin(configuration: KoinAppDeclaration? = null): KoinApplication = startKoin {
    includes(configuration)
    modules(appModule)
}
