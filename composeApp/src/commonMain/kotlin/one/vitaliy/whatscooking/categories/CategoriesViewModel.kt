package one.vitaliy.whatscooking.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import one.vitaliy.whatscooking.categories.api.Category

/**
 * ViewModel for the Categories screen.
 * Manages UI state and data fetching for displaying meal categories.
 */
class CategoriesViewModel(
    private val repository: CategoriesRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<CategoryUiState>(CategoryUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getCategories()
    }

    /**
     * Fetches all meal categories from the repository.
     */
    private fun getCategories() {
        viewModelScope.launch {
            _uiState.value = CategoryUiState.Loading
            runCatching {
                repository.getAllCategories()
            }.onSuccess { response ->
                val categories = response.categories.orEmpty()
                _uiState.value = CategoryUiState.Content(categories)
            }.onFailure { throwable ->
                Napier.e(throwable) { "Failed to fetch categories" }
                _uiState.value = CategoryUiState.Error(throwable)
            }
        }
    }

    /**
     * Refreshes the categories list.
     */
    fun refresh() {
        getCategories()
    }
}

/**
 * UI state for the Categories screen.
 */
sealed interface CategoryUiState {
    data class Content(val categories: List<Category>) : CategoryUiState
    data object Loading : CategoryUiState
    data class Error(val throwable: Throwable) : CategoryUiState
}
