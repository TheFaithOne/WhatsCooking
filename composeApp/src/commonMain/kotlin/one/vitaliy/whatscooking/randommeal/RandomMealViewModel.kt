package one.vitaliy.whatscooking.randommeal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import one.vitaliy.whatscooking.networking.MealDto

/**
 * ViewModel for the Random Meal screen.
 * Manages UI state and data fetching for displaying a random meal.
 */
class RandomMealViewModel(
    private val repository: RandomMealRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<RandomMealUiState>(RandomMealUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getRandomMeal()
    }

    /**
     * Fetches a random meal from the repository.
     */
    fun getRandomMeal() {
        viewModelScope.launch {
            _uiState.value = RandomMealUiState.Loading
            runCatching {
                repository.getRandomMeal()
            }.onSuccess { response ->
                val meal = response.meals?.firstOrNull()
                if (meal != null) {
                    _uiState.value = RandomMealUiState.Content(meal)
                } else {
                    _uiState.value = RandomMealUiState.Error(Exception("No meal found"))
                }
            }.onFailure { throwable ->
                Napier.e(throwable) { "Failed to fetch random meal" }
                _uiState.value = RandomMealUiState.Error(throwable)
            }
        }
    }
}

/**
 * UI state for the Random Meal screen.
 */
sealed interface RandomMealUiState {
    data class Content(val mealDto: MealDto) : RandomMealUiState
    data object Loading : RandomMealUiState
    data class Error(val throwable: Throwable) : RandomMealUiState
}
