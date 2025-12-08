package one.vitaliy.whatscooking.categories.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import one.vitaliy.whatscooking.categories.CategoriesRepository
import one.vitaliy.whatscooking.networking.Meal

/**
 * ViewModel for the Category Details screen.
 * Manages UI state and data fetching for displaying meals within a specific category.
 */
class CategoryDetailsViewModel(
    private val categoriesRepository: CategoriesRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<CategoryDetailsUiState>(CategoryDetailsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    /**
     * Fetches meals for a specific category.
     * @param category The category name to fetch meals for
     */
    fun getMealsForCategory(category: String) {
        viewModelScope.launch {
            _uiState.value = CategoryDetailsUiState.Loading
            runCatching {
                categoriesRepository.getMealsByCategory(category)
            }.onSuccess { response ->
                val meals = response.meals.orEmpty()
                _uiState.value = CategoryDetailsUiState.Content(meals)
            }.onFailure { throwable ->
                Napier.e(throwable) { "Failed to fetch meals for category: $category" }
                _uiState.value = CategoryDetailsUiState.Error(throwable)
            }
        }
    }

    /**
     * Refreshes the meals list for the current category.
     * @param category The category name to refresh
     */
    fun refresh(category: String) {
        getMealsForCategory(category)
    }
}

/**
 * UI state for the Category Details screen.
 */
sealed interface CategoryDetailsUiState {
    data class Content(val meals: List<Meal>) : CategoryDetailsUiState
    data object Loading : CategoryDetailsUiState
    data class Error(val throwable: Throwable) : CategoryDetailsUiState
}
