package one.vitaliy.whatscooking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import one.vitaliy.whatscooking.networking.MealDbApiServices
import one.vitaliy.whatscooking.networking.MealsResponse

class RandomMealViewModel(
    private val apiServices: MealDbApiServices,
) : ViewModel() {

    private val _uiState = MutableStateFlow<MealsResponse?>(null)
    val uiState = _uiState.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null,
    )

    init {
        getRandomMeal()
    }

    fun getRandomMeal() {
        viewModelScope.launch {
            _uiState.value = apiServices.loadRandomMeal()
        }
    }
}