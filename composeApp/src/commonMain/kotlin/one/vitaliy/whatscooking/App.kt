package one.vitaliy.whatscooking

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import one.vitaliy.whatscooking.categories.list.CategoriesPage
import one.vitaliy.whatscooking.di.initKoin
import one.vitaliy.whatscooking.homepage.HomepageScreen
import one.vitaliy.whatscooking.mealdetail.MealDetailScreen
import one.vitaliy.whatscooking.mealdetail.MealDetailScreenRoute
import one.vitaliy.whatscooking.randommeal.RandomMealScreen
import one.vitaliy.whatscooking.ui.theme.WhatsCookingTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() {
    KoinApplication(
        application = { initKoin() }
    ) {
        WhatsCookingTheme {
            Scaffold { paddingValues ->
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = HomepageScreen) {
                    composable<RandomMealScreen> {
                        RandomMealScreen(
                            paddingValues = paddingValues,
                            onNavigateToCategories = { navController.navigate(CategoriesPage) },
                        )
                    }
                    composable<CategoriesPage> {
                        CategoriesPage(
                            paddingValues = paddingValues,
//                        onCategoryClick = { navController.navigate(it) }
                        )
                    }
                    composable<HomepageScreen> {
                        HomepageScreen(
                            paddingValues = paddingValues,
                            navigateToMealDetail = { mealId ->
                                navController.navigate(MealDetailScreenRoute(mealId))
                            }
                        )
                    }
                    composable<MealDetailScreenRoute> {
                        val mealId = it.toRoute<MealDetailScreenRoute>().mealId
                        MealDetailScreen(paddingValues = paddingValues, mealId = mealId)
                    }
                }
            }
        }
    }

}
