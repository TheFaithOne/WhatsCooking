package one.vitaliy.whatscooking.mealdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import one.vitaliy.whatscooking.networking.MealDto

/**
 * ViewModel for the Meal Detail screen.
 * Manages UI state and data fetching for displaying detailed information about a meal.
 */
internal class MealDetailViewModel(
    private val repository: MealDetailRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<MealDetailUiState>(MealDetailUiState.Loading)
    val uiState = _uiState.asStateFlow()

    /**
     * Fetches detailed information about a meal by its ID.
     * @param mealId The unique identifier of the meal
     */
    fun getMealDetails(mealId: String) {
        viewModelScope.launch {
            _uiState.value = MealDetailUiState.Loading
            runCatching {
                val response = repository.getMealById(mealId)
                requireNotNull(response.meals?.firstOrNull()) {
                    "No meal found with ID: $mealId"
                }
            }.onFailure { throwable ->
                Napier.e(throwable) { "Failed to fetch meal details for ID: $mealId" }
                _uiState.value = MealDetailUiState.Error(throwable)
            }.onSuccess { meal ->
                _uiState.value = MealDetailUiState.Content(meal)
            }
        }
    }

    /**
     * Refreshes the meal details by re-fetching from the repository.
     * @param mealId The unique identifier of the meal to refresh
     */
    fun refresh(mealId: String) {
        getMealDetails(mealId)
    }
}

/**
 * UI state for the Meal Detail screen.
 */
internal sealed interface MealDetailUiState {
    data class Content(val mealDto: MealDto) : MealDetailUiState
    data object Loading : MealDetailUiState
    data class Error(val throwable: Throwable) : MealDetailUiState
}
