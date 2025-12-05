package one.vitaliy.whatscooking.mealdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import one.vitaliy.whatscooking.networking.Meal
import one.vitaliy.whatscooking.networking.MealDbApiServices

internal class MealDetailViewModel(
    private val apiServices: MealDbApiServices,
) : ViewModel() {

    private val _uiState = MutableStateFlow<MealDetailUiState>(MealDetailUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getMealDetails(mealId: String) {
        viewModelScope.launch {
            runCatching {
                requireNotNull(apiServices.getMealById(mealId).meals?.first())
            }.onFailure {
                Napier.e { "Failed to fetch meal details $it" }
                _uiState.value = MealDetailUiState.Error(it)
            }.onSuccess {
                _uiState.value = MealDetailUiState.Content(it)
            }
        }
    }

    fun refresh(mealId: String) {
        getMealDetails(mealId)
    }
}

internal sealed interface MealDetailUiState {
    data class Content(val meal: Meal) : MealDetailUiState
    object Loading : MealDetailUiState
    data class Error(val throwable: Throwable) : MealDetailUiState
}
