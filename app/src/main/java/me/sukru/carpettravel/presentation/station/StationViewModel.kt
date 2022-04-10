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
import me.sukru.carpettravel.domain.model.SpaceShip
import me.sukru.carpettravel.domain.model.Station
import me.sukru.carpettravel.domain.use_case.station.*
import javax.inject.Inject

@HiltViewModel
class StationViewModel @Inject constructor(
    private val getStationListUseCase: GetStationListUseCase,
    private val getSpaceShipUseCase: GetSpaceShipUseCase,
    private val updateFavoriteStation: UpdateFavoriteStation,
    private val getCurrentStationUseCase: GetCurrentStationUseCase,
    private val travelToStationUseCase: TravelToStationUseCase,
    private val restartGameUseCase: RestartGameUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(StationState())
    val state: StateFlow<StationState> = _state

    private var spaceShip: SpaceShip? = null
    private var currentStation: Station? = null

    private val _uiEvent = SingleLiveEvent<StationUiEvent>()
    val uiEvent: LiveData<StationUiEvent> get() = _uiEvent

    private var stationFetchJob: Job? = null

    private var spaceShipJob: Job? = null
    private var currentStationJob: Job? = null

    init {
        getSpaceShip()
    }

    private fun getSpaceShip() {
        spaceShipJob?.cancel()
        spaceShipJob = getSpaceShipUseCase().onEach {
            when (it) {
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = it.message
                    )
                    _uiEvent.value = StationUiEvent.ShowError(it.message ?: "Unknown error")
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
                    spaceShip = it.data
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
                        isLoading = false
                    )
                    _uiEvent.value = StationUiEvent.ShowError(it.message ?: "Unknown error")
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
                    getCurrentStation()
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getCurrentStation() {
        currentStationJob?.cancel()
        currentStationJob = getCurrentStationUseCase().onEach {
            when (it) {
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                    _uiEvent.value = StationUiEvent.ShowError(it.message ?: "Unexpected error")
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        currentStation = it.data
                    )
                    currentStation = it.data
                }
            }
        }.launchIn(viewModelScope)
    }

    fun travelStation(station: Station) {
        val _spaceShip = spaceShip
        val _currentStation = currentStation
        _state.value = _state.value.copy(
            isLoading = true,
        )
        if (_spaceShip == null) {
            _state.value = _state.value.copy(
                isLoading = false
            )
            _uiEvent.value = StationUiEvent.ShowError("Space ship is not ready")
            return
        }
        if(_currentStation == null) {
            _state.value = _state.value.copy(
                isLoading = false
            )
            _uiEvent.value = StationUiEvent.ShowError("Current station is not ready")
            return
        }

        viewModelScope.launch {
            val result = travelToStationUseCase(_spaceShip, _currentStation, station)
            when(result) {
                is TravelToStationUseCase.TravelStatus.Failure -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                    )
                    _uiEvent.value = StationUiEvent.ShowError(result.message)
                    resetGame()
                }
                TravelToStationUseCase.TravelStatus.Finished -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                    _uiEvent.value = StationUiEvent.ShowDialog(
                        result.message,
                        "Game finished",
                    )
                    resetGame()
                }
                is TravelToStationUseCase.TravelStatus.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                    _uiEvent.value = StationUiEvent.ShowDialog(result.message, "Travel finished")
                }
            }
        }

    }

    private fun resetGame() {
        viewModelScope.launch {
            restartGameUseCase()
        }
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