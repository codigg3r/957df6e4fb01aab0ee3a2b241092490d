package me.sukru.carpettravel.presentation.station

import androidx.lifecycle.LiveData
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
import me.sukru.carpettravel.common.SingleLiveEvent
import me.sukru.carpettravel.domain.model.Station
import me.sukru.carpettravel.domain.use_case.station.GetSpaceShipUseCase
import me.sukru.carpettravel.domain.use_case.station.GetStationListUseCase
import me.sukru.carpettravel.domain.use_case.station.UpdateFavoriteStation
import javax.inject.Inject

@HiltViewModel
class StationViewModel @Inject constructor(
    private val getStationListUseCase: GetStationListUseCase,
    private val getSpaceShipUseCase: GetSpaceShipUseCase,
    private val updateFavoriteStation: UpdateFavoriteStation
) : ViewModel() {

    private val _state = MutableStateFlow(StationState())
    val state: StateFlow<StationState> = _state

    private val _uiEvent = SingleLiveEvent<StationState>()
    val uiEvent: LiveData<StationState> get() = _uiEvent

    private var stationFetchJob: Job? = null

    init {
        getSpaceShip()
    }

    private fun getSpaceShip() {
        getSpaceShipUseCase().onEach {
            when (it) {
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
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
                        spaceShipName = it.data!!.name,
                        spaceShipHealth = it.data.health,
                        ugs = it.data.ugs,
                        eus = it.data.eus,
                        ds = it.data.ds,
                        dsTimer = it.data.dsTimer,
                    )
                    fetchStations()
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun fetchStations(stationName: String = "") {
        stationFetchJob?.cancel()
        stationFetchJob = getStationListUseCase(stationName = stationName).onEach {
            when (it) {
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
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
                        stationList = it.data ?: emptyList()
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun travelStation(station: Station) {

    }

    fun favoriteStation(station: Station) {
        viewModelScope.launch {
            updateFavoriteStation(station)
        }
    }

    fun search(stationName: String) {
        fetchStations(stationName)
    }
}