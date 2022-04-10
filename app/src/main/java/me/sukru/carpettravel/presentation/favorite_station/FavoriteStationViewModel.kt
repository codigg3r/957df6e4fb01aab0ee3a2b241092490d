package me.sukru.carpettravel.presentation.favorite_station

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import me.sukru.carpettravel.common.Resource
import me.sukru.carpettravel.domain.model.Station
import me.sukru.carpettravel.domain.use_case.favorite_station.GetFavoriteStationUseCase
import me.sukru.carpettravel.domain.use_case.station.UpdateFavoriteStation
import javax.inject.Inject

@HiltViewModel
class FavoriteStationViewModel @Inject constructor(
    private val getFavoriteStationUseCase: GetFavoriteStationUseCase,
    private val updateFavoriteStation: UpdateFavoriteStation,
): ViewModel() {
    private val _state = MutableStateFlow(FavoriteStationUiState())
    val state: StateFlow<FavoriteStationUiState> = _state
    private var favoriteStationJob: Job? = null

    init {
        getFavoriteStation()
    }

    private fun getFavoriteStation() {
        favoriteStationJob?.cancel()
        favoriteStationJob = getFavoriteStationUseCase().onEach {
            when(it) {
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading =  false,
                        error = it.message
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        favoriteStationList = it.data!!,
                        favoriteStationCount = it.data.size
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun favoriteStation(it: Station) {
        viewModelScope.launch {
            updateFavoriteStation(it)
        }
    }
}