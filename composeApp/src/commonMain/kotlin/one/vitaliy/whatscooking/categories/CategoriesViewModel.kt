package one.vitaliy.whatscooking.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import one.vitaliy.whatscooking.categories.api.Category
import one.vitaliy.whatscooking.networking.MealDbApiServices

class CategoriesViewModel(
    private val apiServices: MealDbApiServices,
) : ViewModel() {

    private val _uiState = MutableStateFlow<CategoryUiState?>(null)
    val uiState = _uiState.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null,
    )

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            val response = apiServices.getCategories().categories.orEmpty()
            _uiState.value = CategoryUiState(response)
        }
    }
}

data class CategoryUiState(
    val categories: List<Category>,
)
