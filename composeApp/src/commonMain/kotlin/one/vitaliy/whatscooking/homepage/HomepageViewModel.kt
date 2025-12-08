package one.vitaliy.whatscooking.homepage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import one.vitaliy.whatscooking.homepage.api.HomepageRepository
import one.vitaliy.whatscooking.networking.Meal

/**
 * ViewModel for the Homepage screen.
 * Manages UI state and data fetching for the main homepage content.
 */
class HomepageViewModel(
    private val homepageRepository: HomepageRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomepageUiState>(HomepageUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        fetchLatestMeals()
    }

    /**
     * Fetches the latest meals from the repository.
     */
    private fun fetchLatestMeals() {
        viewModelScope.launch {
            _uiState.value = HomepageUiState.Loading
            runCatching {
                homepageRepository.getLatestMeals()
            }.onSuccess { response ->
                _uiState.value = HomepageUiState.Content(response.meals.orEmpty())
            }.onFailure { throwable ->
                Napier.e(throwable) { "Failed to fetch latest meals" }
                _uiState.value = HomepageUiState.Error(throwable)
            }
        }
    }

    /**
     * Refreshes the homepage content by re-fetching the latest meals.
     */
    fun refresh() {
        fetchLatestMeals()
    }
}

/**
 * UI state for the Homepage screen.
 */
sealed interface HomepageUiState {
    data class Content(val latestMeals: List<Meal>) : HomepageUiState
    data object Loading : HomepageUiState
    data class Error(val throwable: Throwable) : HomepageUiState
}
