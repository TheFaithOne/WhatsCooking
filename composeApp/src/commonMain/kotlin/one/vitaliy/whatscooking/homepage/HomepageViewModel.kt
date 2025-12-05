package one.vitaliy.whatscooking.homepage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import one.vitaliy.whatscooking.homepage.api.HomepageRepository
import one.vitaliy.whatscooking.networking.Meal
import org.koin.core.KoinApplication.Companion.init
import kotlin.time.Duration.Companion.seconds

class HomepageViewModel(
    private val homepageRepository: HomepageRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomepageUiState>(HomepageUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        fetchLatestMeals()
    }

    private fun fetchLatestMeals() {
        viewModelScope.launch {
            runCatching {
                homepageRepository.getLatestMeals()
            }.onSuccess {
                _uiState.value = HomepageUiState.Content(it.meals.orEmpty())
            }.onFailure {
                Napier.e { "Failed to fetch latest meals $it" }
                _uiState.value = HomepageUiState.Error(it)
            }
        }
    }

    fun refresh() {
        fetchLatestMeals()
    }
}

sealed interface HomepageUiState {
    data class Content(val latestMeals: List<Meal>) : HomepageUiState
    data object Loading : HomepageUiState
    data class Error(val throwable: Throwable) : HomepageUiState
}
