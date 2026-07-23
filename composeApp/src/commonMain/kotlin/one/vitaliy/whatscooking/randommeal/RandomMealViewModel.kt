package one.vitaliy.whatscooking.randommeal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import one.vitaliy.whatscooking.data.RecipeDomain

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
                repository.getRandomRecipe()
            }.onSuccess { recipe ->
                _uiState.value = RandomMealUiState.Content(recipe)
            }.onFailure { throwable ->
                Napier.e(throwable) { "Failed to fetch random recipe" }
                _uiState.value = RandomMealUiState.Error(throwable)
            }
        }
    }
}

/**
 * UI state for the Random Meal screen.
 */
sealed interface RandomMealUiState {
    data class Content(val recipe: RecipeDomain) : RandomMealUiState
    data object Loading : RandomMealUiState
    data class Error(val throwable: Throwable) : RandomMealUiState
}
