package me.sukru.carpettravel.presentation.space_ship_create

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.sukru.carpettravel.R
import me.sukru.carpettravel.common.SingleLiveEvent
import me.sukru.carpettravel.domain.model.SpaceShip
import me.sukru.carpettravel.domain.use_case.space_ship_create.CalculateRemainingPointsUseCase
import me.sukru.carpettravel.domain.use_case.space_ship_create.UpsertSpaceShipUseCase
import me.sukru.carpettravel.domain.use_case.space_ship_create.ValidateSpaceShipUseCase
import javax.inject.Inject

@HiltViewModel
class SpaceShipCreateViewModel @Inject constructor(
    private val upsertSpaceShipUseCase: UpsertSpaceShipUseCase,
    private val validateSpaceShipUseCase: ValidateSpaceShipUseCase,
    private val calculateRemainingPointsUseCase: CalculateRemainingPointsUseCase,
) : ViewModel() {

    private var spaceShip:SpaceShip? = null

    private val _state = MutableStateFlow(SpaceShipState())
    val state: StateFlow<SpaceShipState> = _state

    private val _uiEvent = SingleLiveEvent<SpaceShipUiEvent>()
    val uiEvent: LiveData<SpaceShipUiEvent> get() = _uiEvent

    private suspend fun saveSpaceShip(): Boolean {
        return spaceShip?.let {
            upsertSpaceShipUseCase(it)
            true
        } ?: false
    }

    private fun updateSpaceShip(spaceShip: SpaceShip) {
        val isValid = validateSpaceShipUseCase(spaceShip)
        if (isValid) {
            val remainingPoints = calculateRemainingPointsUseCase(
                speed = spaceShip.speed,
                strength = spaceShip.strength,
                capacity = spaceShip.capacity,
            )
            _state.apply {
                value = value.copy(
                    speed = spaceShip.speed,
                    strength = spaceShip.strength,
                    capacity = spaceShip.capacity,
                    name = spaceShip.name,
                    speedMaxValue = remainingPoints.speedMaxValue,
                    strengthMaxValue = remainingPoints.strengthMaxValue,
                    capacityMaxValue = remainingPoints.capacityMaxValue,
                    remainingPoint = remainingPoints.remainingTotalPoints.toInt().toString(),
                )
            }
        }
    }

    fun onNameChanged(name: String) {
        spaceShip.apply {
            if (this?.name == name) return
            val newSpaceShip = this?.copy(name = name)
                ?: SpaceShip(name = name)
            spaceShip = newSpaceShip
            updateSpaceShip(newSpaceShip)
        }
    }

    fun onSpeedChanged(speed: Float) {
        spaceShip.apply {
            if(this?.speed == speed) return
            val newSpaceShip = this?.copy(speed = speed)
                ?: SpaceShip(speed = speed)
            spaceShip = newSpaceShip
            updateSpaceShip(newSpaceShip)
        }
    }

    fun onStrengthChanged(strength: Float) {
        spaceShip.apply {
            if (this?.strength == strength) return
            val newSpaceShip = this?.copy(strength = strength)
                ?: SpaceShip(strength = strength)
            spaceShip = newSpaceShip
            updateSpaceShip(newSpaceShip)
        }
    }

    fun onCapacityChanged(capacity: Float) {
        spaceShip.apply {
            if (this?.capacity == capacity) return
            val newSpaceShip = this?.copy(capacity = capacity)
                ?: SpaceShip(capacity = capacity)
            spaceShip = newSpaceShip
            updateSpaceShip(newSpaceShip)
        }
    }

    fun onContinueClicked() {
        viewModelScope.launch {
            val isSuccessful = saveSpaceShip()
            if(isSuccessful) {
                _uiEvent.value = SpaceShipUiEvent.Navigate(R.id.homeFragment)
            } else {
                _uiEvent.value = SpaceShipUiEvent.ShowError("Error creating space ship")
            }
        }
    }

}
