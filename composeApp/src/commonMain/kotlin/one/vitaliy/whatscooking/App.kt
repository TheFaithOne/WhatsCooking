package one.vitaliy.whatscooking

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import one.vitaliy.whatscooking.bottommenu.FavouritesPlaceholderScreen
import one.vitaliy.whatscooking.bottommenu.FavouritesScreen
import one.vitaliy.whatscooking.bottommenu.NavigationBar
import one.vitaliy.whatscooking.bottommenu.SearchPlaceholderScreen
import one.vitaliy.whatscooking.bottommenu.SearchScreen
import one.vitaliy.whatscooking.categories.list.CategoriesPage
import one.vitaliy.whatscooking.homepage.HomepageScreen
import one.vitaliy.whatscooking.mealdetail.MealDetailScreen
import one.vitaliy.whatscooking.mealdetail.MealDetailScreenRoute
import one.vitaliy.whatscooking.randommeal.RandomMealScreen
import one.vitaliy.whatscooking.ui.theme.WhatsCookingTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun App() {
    val navController = rememberNavController()
    WhatsCookingTheme {
        Scaffold(
            bottomBar = {
                NavigationBar(navController)
            },
        ) { paddingValues ->
            SharedTransitionLayout {
                NavHost(navController = navController, startDestination = HomepageScreen) {
                    composable<RandomMealScreen> {
                        RandomMealScreen(
                            paddingValues = paddingValues,
                            onNavigateToCategories = { navController.navigate(CategoriesPage) },
                        )
                    }
                    composable<FavouritesScreen> {
                        FavouritesPlaceholderScreen(paddingValues = paddingValues)
                    }
                    composable<SearchScreen> {
                        SearchPlaceholderScreen(paddingValues = paddingValues)
                    }
                    composable<CategoriesPage> {
                        CategoriesPage(
                            paddingValues = paddingValues,
                        )
                    }
                    composable<HomepageScreen> {
                        HomepageScreen(
                            paddingValues = paddingValues,
                            sharedTransitionScope = this@SharedTransitionLayout,
                            animatedVisibilityScope = this,
                            navigateToMealDetail = { mealId ->
                                navController.navigate(MealDetailScreenRoute(mealId))
                            },
                        )
                    }
                    composable<MealDetailScreenRoute> {
                        val mealId = it.toRoute<MealDetailScreenRoute>().mealId
                        MealDetailScreen(
                            paddingValues = paddingValues,
                            mealId = mealId,
                            sharedTransitionScope = this@SharedTransitionLayout,
                            animatedVisibilityScope = this,
                        )
                    }
                }
            }
        }
    }
}
