package one.vitaliy.whatscooking.categories.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import one.vitaliy.whatscooking.categories.CategoriesRepository

class CategoryDetailsViewModel(
    private val categoriesRepository: CategoriesRepository,
) : ViewModel() {
    init {
//        getMealsForCategory()
    }

    // TODO: Fetch meals for the category and display the in a list
    private fun getMealsForCategory(category: String) {
        viewModelScope.launch {
            categoriesRepository.getMealsByCategory(category)
        }
    }
}